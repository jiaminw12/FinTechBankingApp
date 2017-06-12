package layout;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Adapter.AccountAdapterTransfer;
import Adapter.PayeeAdapter;
import Model.Accounts;
import Model.Payees;

import finapp.publicstatic.com.fintechbankapp.JSONParser;
import finapp.publicstatic.com.fintechbankapp.R;
import finapp.publicstatic.com.fintechbankapp.WebServiceAddress;

public class TransferFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Payees> payeeList = new ArrayList<Payees>();
    private ArrayList<Accounts> accountList = new ArrayList<Accounts>();

    PayeeAdapter payeeAdapter;
    AccountAdapterTransfer accountAdapterTransfer;

    EditText amtEditText;
    EditText commentEdittext;
    Spinner spinnerPayee;
    Spinner spinnerAccount;
    Spinner spinnerCategory;
    Button submitBtn;

    private static final String ARG_PARAM = "param";
    private static String mUserId;

    private String mAcctId, mAcctNum, mPayeeId, mPayeeAcctNum, mAcctAmount;

    public TransferFragment() {}

    public static TransferFragment newInstance() {
        TransferFragment fragment = new TransferFragment();
        return fragment;
    }

    public static TransferFragment newInstance(String param) {
        TransferFragment fragment = new TransferFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        mUserId = param;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_transfer, container,
                false);

        amtEditText = (EditText) v.findViewById(R.id.amount);
        commentEdittext = (EditText) v.findViewById(R.id.comments);
        spinnerAccount = (Spinner) v.findViewById(R.id.spinner_account);
        spinnerPayee = (Spinner) v.findViewById(R.id.spinner_payee);
        spinnerCategory = (Spinner) v.findViewById(R.id.spinner_category);

        setPayeeList(mUserId);
        getAccountList(mUserId);

        // Listener called when spinner item selected
        spinnerPayee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                TextView payeeId = (TextView) v.findViewById(R.id.payeeId);
                TextView acctNum = (TextView) v.findViewById(R.id.payeeAccNum);

                mPayeeId = payeeId.getText().toString();
                mPayeeAcctNum = acctNum.getText().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        spinnerAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                TextView accountId = (TextView) v.findViewById(R.id.acctId);
                TextView accountNum = (TextView) v.findViewById(R.id
                        .acctNum);
                TextView accountAmt = (TextView) v.findViewById(R.id
                        .acctAmt);

                mAcctId = accountId.getText().toString();
                mAcctNum = accountNum.getText().toString();
                mAcctAmount = accountAmt.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });


        submitBtn = (Button) v.findViewById(R.id.transfer_button);
        submitBtn.setOnClickListener(this);

        return v;
    }

    private void setPayeeList(String userId){
        PayeeTask payeeTask = new TransferFragment.PayeeTask(userId);
        payeeTask.execute((String) null);
    }

    public class PayeeTask extends AsyncTask<String, String, JSONObject> {
        private JSONParser jsonParser = new JSONParser();
        private WebServiceAddress webServiceAddress = new WebServiceAddress();
        private static final String TAG_SUCCESS = "success";
        private final String mUserid;

        PayeeTask(String userId) {
            mUserid = userId;
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            try {
                // Simulate network access.
                Thread.sleep(2000);

                HashMap<String, String> params = new HashMap<>();
                params.put("userId", mUserid);
                JSONObject json = jsonParser.makeHttpRequest(
                        "POST", webServiceAddress.getBaseUrl
                                ("getAllPayeeByUserId"), params);

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
                        JSONArray jsonArray = json.getJSONArray("payees");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Payees payees = new Payees();
                            payees.setPayeeId(obj.getInt("payeeId"));
                            payees.setPayeeName(obj.getString("payeeName"));
                            payees.setPayeeAcctNum(obj.getString("payeeAcctNum"));

                            //Add your values in your `ArrayList` as below:
                            payeeList.add(payees);

                            payeeAdapter = new PayeeAdapter(getActivity(), R
                                    .layout
                                    .fragment_payee_row, payeeList);
                        }
                    }
                    spinnerPayee.setAdapter(payeeAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onCancelled() {

        }
    }

    private void getAccountList(String userId){
        JSONParser jsonParser = new JSONParser();
        WebServiceAddress webServiceAddress = new WebServiceAddress();
        String TAG_SUCCESS = "success";
        int success = 0;

        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JSONObject json = jsonParser.makeHttpRequest(
                "POST", webServiceAddress.getBaseUrl
                        ("getAllAccountsByUserid"), params);

        if (json != null) {
            try {
                success = json.getInt(TAG_SUCCESS);
                if(success == 1){
                    JSONArray jsonArray = json.getJSONArray("accounts");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Accounts account = new Accounts();
                        account.setAccountID(obj.getInt("accountId"));
                        account.setAccountNum(obj.getString("accountNum"));
                        account.setAmount(obj.getDouble("amount"));

                        accountList.add(account);

                        accountAdapterTransfer = new AccountAdapterTransfer(getActivity(), R
                                .layout
                                .fragment_account_transfer_row, accountList);
                    }
                }
                spinnerAccount.setAdapter(accountAdapterTransfer);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onClick(View v) {
        transferSubmit();
    }

    private void transferSubmit(){
        // check amount is 2 decimal places
        // comments can be null
        // get all selected item from all snipper

        String mAmt = amtEditText.getText().toString();
        String mComment = commentEdittext.getText().toString();
        int postionCategory = spinnerCategory.getSelectedItemPosition() + 1;

        if(mAmt.equals("")){
            Toast.makeText(getActivity(), R.string.error_empty_amount, Toast.LENGTH_LONG).show();
        }  else if (Double.parseDouble(mAmt) > Double.parseDouble(mAcctAmount)){
            Toast.makeText(getActivity(), R.string.error_amount, Toast.LENGTH_LONG).show();
        } else {
            JSONParser jsonParser = new JSONParser();
            WebServiceAddress webServiceAddress = new WebServiceAddress();
            String TAG_SUCCESS = "success";
            int success = 0;

            HashMap<String, String> params = new HashMap<>();

            params.put("userId", mUserId);
            params.put("accountId", mAcctId);
            params.put("transactionAmount", mAmt);
            params.put("transactionDetails", mComment);
            params.put("payeeId", mPayeeId);
            params.put("categoryId", String.valueOf(postionCategory));

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            JSONObject json = jsonParser.makeHttpRequest(
                    "POST", webServiceAddress.getBaseUrl
                            ("addTransfer"), params);

            if (json != null) {
                try {
                    success = json.getInt(TAG_SUCCESS);
                    if(success == 1){
                        /*Intent intent = new Intent(getActivity(),
                                TransferSuccessActivity.class);
                        intent.putExtra("userId", json.getString("userId"));
                        startActivity(intent);*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



    }
}
