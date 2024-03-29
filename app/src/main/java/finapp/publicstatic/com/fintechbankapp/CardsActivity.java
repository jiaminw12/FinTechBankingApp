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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapter.CardsAdapter;
import Model.Accounts;
import Model.Cards;

public class CardsActivity extends AppCompatActivity {
    private String muserID;
    private String mBankID;
    private CardsAdapter cardsAdapter;
    private AccountsCardsTask mCardsTask = null;
    private List<Cards> cardList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cards_cardview2);

        Bundle bundle = getIntent().getExtras();
        muserID = bundle.getString("userId");
        mBankID = bundle.getString("bankId");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cardsAdapter = new CardsAdapter(cardList);
        mCardsTask = new AccountsCardsTask(muserID, mBankID);
        mCardsTask.execute((String) null);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cardsAdapter);
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

    public class AccountsCardsTask extends AsyncTask<String, String, JSONObject> {
        private final String mUserid;
        private final String mBankId;
        private static final String TAG_SUCCESS = "success";
        private JSONParser jsonParser = new JSONParser();
        private WebServiceAddress webServiceAddress = new WebServiceAddress();

        AccountsCardsTask(String userId, String bankId) {
            mUserid = userId;
            mBankId = bankId;
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            try {
                // Simulate network access.
                Thread.sleep(2000);

                HashMap<String, String> params = new HashMap<>();
                params.put("userId", mUserid);
                params.put("bankId", mBankId);

                JSONObject json = jsonParser.makeHttpRequest("POST", webServiceAddress.getBaseUrl("getAllCardsByUserIdAndBankId"), params);

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
                    if(success == 1){
                        JSONArray jsonArray = json.getJSONArray("cards");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Cards cards = new Cards();
                            cards.setActivate(obj.getInt("activate"));
                            cards.setAccountID(obj.getInt("accountId"));
                            cards.setCardsID(obj.getInt("cardId"));
                            cards.setCardTypeID(obj.getInt("cardTypeId"));
                            cards.setCardNum(obj.getString("cardNum"));
                            cards.setCardName(obj.getString("cardName"));
                            cards.setCcv(obj.getString("ccv"));
                            cards.setCardExpiryDate(obj.getString("expiryDate"));

                            //Add your values in your `ArrayList` as below:
                            cardList.add(cards);
                        }
                    }
                    recyclerView.setAdapter(cardsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}