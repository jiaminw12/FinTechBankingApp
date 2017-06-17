package finapp.publicstatic.com.fintechbankapp;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Adapter.BillTransHistoryAdapter;

public class RetrieveHistoryOfBillTransac {

    JSONParser jsonParser ;
    WebServiceAddress webServiceAddress;
    HashMap<String, String> params;
    List<BillTransHistoryAdapter.HistoryListItem> items;

    int success = 0;
    String TAG_SUCCESS = "success";

    public RetrieveHistoryOfBillTransac(){}

    public List<BillTransHistoryAdapter.HistoryListItem> retrieveHistoryView
            (int positionDate, int positionView, String mUserId ){

        jsonParser = new JSONParser();
        webServiceAddress = new WebServiceAddress();
        items = new ArrayList<>();

        if(positionDate == 0){
            // Daily
            if(positionView == 0){
                // Daily + Payment

            } else if(positionView == 1){
                // Daily + Transaction

            } else if(positionView == 2){
                // Daily + Both

            }

        } else if(positionDate == 1){
            // Weekly
            if(positionView == 0){
                // Weekly + Payment
                showPaymentsView("getPaymentsYearlyWeekly", mUserId);

            } else if(positionView == 1){
                // Weekly + Transaction
                showTransactionView("getTransactionsYearlyWeekly", mUserId);

            } else if(positionView == 2){
                // Weekly + Both
                showTransactionView("getBTYearlyWeekly", mUserId);
            }

        } else if(positionDate == 2){
            // Monthly
            if(positionView == 0){
                // Monthly + Payment
                showPaymentsView("getPaymentsYearlyMonthly", mUserId);

            } else if(positionView == 1){
                // Monthly + Transaction
                showTransactionView("getTransactionsYearlyMonthly", mUserId);

            } else if(positionView == 2){
                // Monthly + Both
                showTransactionView("getBTYearlyMonthly", mUserId);

            }
        }

        return items;

    }

    private void showTransactionView(String url, String mUserId){

        params = new HashMap<>();
        params.put("userId", mUserId);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JSONObject json = jsonParser.makeHttpRequest(
                "POST", webServiceAddress.getBaseUrl
                        (url), params);

        if (json != null) {
            try {
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    JSONArray mHistory = json.getJSONArray("history");
                    for (int i = 0; i < mHistory.length(); i++) {
                        JSONObject jsonObject = mHistory.getJSONObject(i);

                        items.add(new BillTransHistoryAdapter.HistoryListItem
                                (jsonObject.getString
                                        ("date"),jsonObject.getString
                                        ("total")));

                        JSONArray transArray = jsonObject.getJSONArray
                                ("transRow");
                        for (int j = 0; j < transArray.length(); j++) {
                            JSONObject jsonObj = transArray.getJSONObject(j);

                            items.add(new BillTransHistoryAdapter
                                    .HistoryListItem(
                                    (formatDate(jsonObj.getString
                                            ("createdAt"))), jsonObj.getString
                                    ("transactionAmount"),
                                    jsonObj.getString("payeeName")));
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void showPaymentsView(String url, String mUserId){

        params = new HashMap<>();
        params.put("userId", mUserId);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JSONObject json = jsonParser.makeHttpRequest(
                "POST", webServiceAddress.getBaseUrl
                        (url), params);

        if (json != null) {
            try {
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    JSONArray mHistory = json.getJSONArray("history");
                    for (int i = 0; i < mHistory.length(); i++) {
                        JSONObject jsonObject = mHistory.getJSONObject(i);

                        items.add(new BillTransHistoryAdapter.HistoryListItem
                                (jsonObject.getString
                                        ("date"), jsonObject.getString
                                        ("total")));

                        JSONArray transArray = jsonObject.getJSONArray
                                ("paymentRow");
                        for (int j = 0; j < transArray.length(); j++) {
                            JSONObject jsonObj = transArray.getJSONObject(j);

                            items.add(new BillTransHistoryAdapter
                                    .HistoryListItem(
                                    (formatDate(jsonObj.getString
                                            ("createdAt"))), jsonObj.getString
                                    ("billAmount"),
                                    jsonObj.getString("issuerName")));
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private Date formatDate(String dateString){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd " +
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
