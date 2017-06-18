package finapp.publicstatic.com.fintechbankapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import Adapter.AccountAdapterTransfer;
import Model.Accounts;

public class BillScheduleActivity extends AppCompatActivity {

    TextView tvDueDate;
    TextView tvPayeeName;
    TextView tvAmount;
    Spinner spinnerAcct;
    CheckBox checkSchedule;
    Button btnPay;
    DatePickerDialog datePickerDialog;

    AccountAdapterTransfer accountAdapterTransfer;

    private String mUserId, mBillId, mAcctId, mAcctNum, mAcctAmount, mDate = "",
            mTime = "";
    private int mYear, mMonth, mDay, mHour, mMinute;
    boolean scheduleChecker = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_schedule);

        Bundle bundle = getIntent().getExtras();
        mUserId = bundle.getString("userId");
        mBillId = bundle.getString("billId");

        tvDueDate = (TextView) findViewById(R.id.tv_dueDate);
        tvPayeeName = (TextView) findViewById(R.id.tv_payee_name);
        tvAmount = (TextView) findViewById(R.id.tv_amount);
        spinnerAcct = (Spinner) findViewById(R.id.spinner_account);
        checkSchedule = (CheckBox) findViewById(R.id.checkBox_scheduler);
        btnPay = (Button) findViewById(R.id.button_pay);

        BillDetailsTask();

        getAccountList(mUserId);
        spinnerAcct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        //checkSchedule.setChecked(true);

        checkSchedule.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showCalendarDialog();
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

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


    private void showCalendarDialog(){
        final Calendar c = Calendar.getInstance();

        if(checkSchedule.isChecked()) {
            // Get Current Date
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            String month, day;
                            if(!checkDoubleDigit(monthOfYear)){
                                month = "0" + String
                                        .valueOf(monthOfYear);
                            } else {
                                month = String.valueOf(monthOfYear);
                            }

                            if(!checkDoubleDigit(dayOfMonth)){
                                day = "0" + String
                                        .valueOf(dayOfMonth);
                            } else {
                                day = String.valueOf(dayOfMonth);
                            }

                            mDate = String.valueOf(year) + "-" + month
                                    + "-" + day;
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if(checkSchedule.isChecked()) {
            // Get Current Time
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            mTime = String.valueOf(hourOfDay) + ":" + String
                                    .valueOf(minute) + ":00";
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        scheduleChecker = true;
    }

    private void submitForm(){
        if (Double.parseDouble(tvAmount.getText().toString()) > Double.parseDouble
                (mAcctAmount)){
            Toast.makeText(this, R.string.error_amount, Toast.LENGTH_LONG)
                    .show();
        } else {

            double diffAmt = Double.parseDouble(mAcctAmount) - Double
                    .parseDouble(tvAmount.getText().toString());

            JSONParser jsonParser = new JSONParser();
            WebServiceAddress webServiceAddress = new WebServiceAddress();
            String TAG_SUCCESS = "success";
            int success = 0;
            JSONObject json = null;

            HashMap<String, String> params = new HashMap<>();

            params.put("userId", mUserId);
            params.put("accountId", mAcctId);
            params.put("billId", mBillId);
            params.put("amountLeft", String.valueOf(diffAmt));

            if (mDate.equals("") && mTime.equals("")){
                params.put("date", "0000-00-00 00:00:00");
            } else {
                params.put("date", mDate + " " + mTime);
            }

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            if (scheduleChecker == false) {
                json = jsonParser.makeHttpRequest(
                        "POST", webServiceAddress.getBaseUrl
                                ("updateBillWithoutSchedule"), params);
            } else {
                json = jsonParser.makeHttpRequest("POST", webServiceAddress.getBaseUrl
                                ("addBillSchedule"), params);
            }

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
    }

    private void BillDetailsTask() {
        JSONParser jsonParser = new JSONParser();
        WebServiceAddress webServiceAddress = new WebServiceAddress();
        final String TAG_SUCCESS = "success";
        int success = 1;

        HashMap<String, String> params = new HashMap<>();
        params.put("userId", mUserId);
        params.put("billId", mBillId);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JSONObject json = jsonParser.makeHttpRequest(
                "POST", webServiceAddress.getBaseUrl
                        ("getAllBillsByUserIdAndBillId"), params);
        if (json != null) {
            try {
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    JSONArray jsonArray = json.getJSONArray("bills");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        tvDueDate.setText(obj.getString("dueDate"));
                        tvPayeeName.setText(obj.getString("issuerName"));
                        tvAmount.setText(String.format("%.2f",obj
                                .getDouble("billAmount")));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getAccountList(String userId){
        ArrayList<Accounts> accountList = new ArrayList<Accounts>();

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
                        ("getAllSavingAccountsByUserId"), params);

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

                        accountAdapterTransfer = new AccountAdapterTransfer(getApplicationContext(), R.layout.fragment_account_transfer_row, accountList);
                    }
                }
                spinnerAcct.setAdapter(accountAdapterTransfer);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean checkDoubleDigit(int x){
        boolean isDoubleDigit = (x > 9 && x < 100) || (x < -9 && x > -100);
        return isDoubleDigit;
    }
}
