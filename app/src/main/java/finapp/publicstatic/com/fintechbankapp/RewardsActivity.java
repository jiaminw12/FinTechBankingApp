package finapp.publicstatic.com.fintechbankapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Adapter.RewardsAdapterForDetails;
import Model.Rewards;
import Model.Rewards;

public class RewardsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private String mUserID;
    private String mCardID;
    private RewardsAdapterForDetails RewardsAdapterForDetails;
    private List<Rewards> rewardList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_reward);

        Bundle bundle = getIntent().getExtras();
        mUserID = bundle.getString("userId");
        mCardID = bundle.getString("cardId");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_reward);
        RewardsAdapterForDetails = new RewardsAdapterForDetails(rewardList);

        new RewardsTask(mUserID, mCardID).execute();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(RewardsAdapterForDetails);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class RewardsTask extends AsyncTask<String, String, JSONObject> {
        private final String mUserid;
        private final String mCardId;
        private static final String TAG_SUCCESS = "success";
        private JSONParser jsonParser = new JSONParser();
        private WebServiceAddress webServiceAddress = new WebServiceAddress();

        RewardsTask(String userId, String cardId) {
            mUserid = userId;
            mCardId = cardId;
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            try {
                // Simulate network access.
                Thread.sleep(2000);

                HashMap<String, String> params = new HashMap<>();
                params.put("userId", mUserid);
                params.put("cardId", mCardId);

                JSONObject json = jsonParser.makeHttpRequest("POST", webServiceAddress.getBaseUrl("getAllRewardPtsByUserIdAndCardId"), params);

                if (json != null) {
                    return json;
                }
            } catch (InterruptedException e) {
                return null;
            }
            return null;
        }

        protected void onPostExecute(JSONObject json) {
            int success = 0;

            if (json != null) {
                try {
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        JSONArray jsonArray = json.getJSONArray("rewards");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Rewards reward = new Rewards();
                            reward.setRewardsId(jsonObject.getInt("rewardsId"));
                            reward.setUserId(jsonObject.getInt("userId"));
                            reward.setPoints(jsonObject.getInt("points"));
                            reward.setCreatedAt(formatDate(jsonObject.getString
                                    ("createdAt")));
                            reward.setExpiryAt(formatDate(jsonObject.getString
                                    ("expiryAt")));
                            reward.setCardId(jsonObject.getInt("cardId"));

                            //Add your values in your `ArrayList` as below:
                            rewardList.add(reward);
                        }
                    }
                    recyclerView.setAdapter(RewardsAdapterForDetails);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Date formatDate(String dateString){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd " +
                "hh:mm:ss");

        Date date=null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}