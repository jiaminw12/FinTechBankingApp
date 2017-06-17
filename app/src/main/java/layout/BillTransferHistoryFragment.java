package layout;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;

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
import finapp.publicstatic.com.fintechbankapp.JSONParser;
import finapp.publicstatic.com.fintechbankapp.R;
import finapp.publicstatic.com.fintechbankapp.TransSuccessfulActivity;
import finapp.publicstatic.com.fintechbankapp.WebServiceAddress;

public class BillTransferHistoryFragment extends Fragment {

    private static final String ARG_PARAM = "param";
    private static String mUserId;

    private Spinner spinnerDate;
    private Spinner spinnerView;
    private RecyclerView recyclerView;

    HashMap<String, String> dateName = new HashMap<>();
    List<BillTransHistoryAdapter.HistoryListItem> items = new ArrayList<>();


    BillTransHistoryAdapter billTransHistoryAdapter;

    public BillTransferHistoryFragment() {
        // Required empty public constructor
    }

    public static BillTransferHistoryFragment newInstance() {
        BillTransferHistoryFragment fragment = new BillTransferHistoryFragment();
        return fragment;
    }

    public static BillTransferHistoryFragment newInstance(String param) {
        BillTransferHistoryFragment fragment = new BillTransferHistoryFragment();
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
        View historyFragment =  inflater.inflate(R.layout.fragment_bill_transfer_history,
                container, false);

        spinnerDate = (Spinner) historyFragment.findViewById(R.id.spinner_payee);
        spinnerView = (Spinner) historyFragment.findViewById(R.id.spinner_view);

        recyclerView = (RecyclerView) historyFragment.findViewById(R.id
                .recycleView_history);

        showYearlyMonthlyView();

        return historyFragment;
    }


    private void showYearlyMonthlyView(){

        JSONParser jsonParser = new JSONParser();
        WebServiceAddress webServiceAddress = new WebServiceAddress();
        String TAG_SUCCESS = "success";
        int success = 0;

        HashMap<String, String> params = new HashMap<>();
        params.put("userId", mUserId);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JSONObject json = jsonParser.makeHttpRequest(
                "POST", webServiceAddress.getBaseUrl
                        ("getTransactionsYearlyMonthly"), params);

        if (json != null) {
            try {
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    //{"history":[{"date":"2017-7","total":"15.00","transRow":[{"transactionId":"7","transactionAmount":"15.00","createdAt":"2017-07-03 07:37:13","payeeName":"Rubi Olaughlin"}]},{"date":"2017-6","total":"3414.90","transRow":[{"transactionId":"6","transactionAmount":"10.00","createdAt":"2017-06-14 15:56:08","payeeName":"Peggy Criss"},

                    JSONArray mHistory = json.getJSONArray("history");
                    for (int i = 0; i < mHistory.length(); i++) {
                        JSONObject jsonObject = mHistory.getJSONObject(i);

                        items.add(new BillTransHistoryAdapter.HistoryListItem
                                (jsonObject.getString
                                ("date")));

                        JSONArray transArray = jsonObject.getJSONArray
                                ("transRow");
                        for(int j = 0; j < transArray.length(); j++){
                            JSONObject jsonObj = transArray.getJSONObject(j);

                            items.add( new BillTransHistoryAdapter
                                    .HistoryListItem(
                                    (formatDate(jsonObj.getString
                                            ("createdAt"))), jsonObj.getString
                                    ("transactionAmount"),
                                    jsonObj.getString("payeeName")));
                        }
                    }
                }

                billTransHistoryAdapter = new BillTransHistoryAdapter
                        (this.getActivity(), items);
                billTransHistoryAdapter.setMode(ExpandableRecyclerAdapter.MODE_ACCORDION);
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
                recyclerView.setAdapter(billTransHistoryAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
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
