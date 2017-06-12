package finapp.publicstatic.com.fintechbankapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private String muserID;
    private String mBankID;
    private CardsAdapter cardsAdapter;
    private AccountsCardsTask mCardsTask = null;
    private List<Cards> cardList = new ArrayList<>();
    private List<Accounts> accountList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragments_cards_cardview2);

        Bundle bundle = getIntent().getExtras();
        muserID = bundle.getString("userID");
        mBankID = bundle.getString("bankID");

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.card_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class AccountsCardsTask extends AsyncTask<String, String, JSONObject> {
        private JSONParser jsonParser = new JSONParser();
        private WebServiceAddress webServiceAddress = new WebServiceAddress();
        private static final String TAG_SUCCESS = "success";
        private final String mUserid;
        private final String mBankId;

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
                Log.e("userId", mUserid);
                Log.e("bankId", mBankId);
                JSONObject json = jsonParser.makeHttpRequest("POST", webServiceAddress.getBaseUrl("getAllCardsByUserIdAndBankId"), params);

                if (json != null) {
                    return json;
                }
            } catch (InterruptedException e) {
                return null;
            }
            return null;
        }

        protected void onPostExecute(JSONObject json, JSONObject json2) {
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

        @Override
        protected void onCancelled() {
        }
    }
}