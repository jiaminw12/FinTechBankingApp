package finapp.publicstatic.com.fintechbankapp;

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

import Adapter.AccountsAdapter;
import Model.Accounts;

public class AccountsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private String muserID;
    private String mBankID;
    private AccountsAdapter accountsAdapter;
    private List<Accounts> accountList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_accounts_cardview2);

        Bundle bundle = getIntent().getExtras();
        muserID = bundle.getString("userId");
        mBankID = bundle.getString("bankId");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        accountsAdapter = new AccountsAdapter(accountList);

        new AccountsCardsTask(muserID, mBankID).execute();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(accountsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

                JSONObject json = jsonParser.makeHttpRequest("POST", webServiceAddress.getBaseUrl("getAllAccountsByUserIdAndBankId"), params);

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
                        JSONArray jsonArray = json.getJSONArray("accounts");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Accounts account = new Accounts();
                            account.setAccountID(jsonObject.getInt("accountId"));
                            account.setAccountNum(jsonObject.getString("accountNum"));
                            account.setAmount(jsonObject.getDouble("amount"));
                            account.setBankName(jsonObject.getString("bankname"));
                            account.setTypeName(jsonObject.getString("type_name"));
                            account.setBankID(jsonObject.getInt("bankId"));
                            account.setTypeID(jsonObject.getInt("typeId"));

                            //Add your values in your `ArrayList` as below:
                            accountList.add(account);
                        }
                    }
                    recyclerView.setAdapter(accountsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}