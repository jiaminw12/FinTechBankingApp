package finapp.publicstatic.com.fintechbankapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

import Adapter.PayeeAdapter;
import Model.Payees;

public class AddAccountActivity extends AppCompatActivity {

    Spinner spinnerBank;
    Spinner spinnerAccountType;
    TextView accountNum;
    Button btnAddAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        Bundle bundle = getIntent().getExtras();
        final String userId = bundle.getString("userId");

        spinnerBank = (Spinner) findViewById(R.id.spinner_bank);
        spinnerAccountType = (Spinner) findViewById(R.id.spinner_account);
        accountNum = (TextView) findViewById(R.id.et_accountNum);

        btnAddAccount = (Button) findViewById(R.id.button_add_account);
        btnAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (accountNum.getText().toString().equals("")){
                    // do something
                } else {
                    new AddAccountTask(userId, String.valueOf(spinnerBank
                            .getSelectedItemPosition() + 1), accountNum.getText().toString()
                            , String.valueOf(spinnerAccountType.getSelectedItemPosition() + 1), "20000.00");

                }
            }
        });
    }


    public class AddAccountTask extends AsyncTask<String, String, JSONObject> {
        private JSONParser jsonParser = new JSONParser();
        private WebServiceAddress webServiceAddress = new WebServiceAddress();
        private static final String TAG_SUCCESS = "success";
        private final String mUserid, mBankId, mAccountNum, mTypeId, mAmount;

        AddAccountTask(String userId, String bankId, String accountNum,
                       String typeId, String amount) {
            mUserid = userId;
            mBankId = bankId;
            mAccountNum = accountNum;
            mTypeId = typeId;
            mAmount = amount;
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            try {
                // Simulate network access.
                Thread.sleep(2000);

                HashMap<String, String> params = new HashMap<>();
                params.put("userId", mUserid);
                params.put("bankId", mBankId);
                params.put("accountNum", mAccountNum);
                params.put("typeId", mTypeId);
                params.put("amount", mAmount);

                JSONObject json = jsonParser.makeHttpRequest(
                        "POST", webServiceAddress.getBaseUrl
                                ("addAccount"), params);

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
                        setResult(TransSuccessfulActivity.RESULT_OK);
                        finish();
                    }
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
