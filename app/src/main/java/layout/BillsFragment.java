package layout;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Adapter.BillAdapter;
import Adapter.BillScheduleAdapter;
import Model.Bills;
import Model.Schedule;
import finapp.publicstatic.com.fintechbankapp.BillRecyclerTouchListener;
import finapp.publicstatic.com.fintechbankapp.BillScheduleActivity;
import finapp.publicstatic.com.fintechbankapp.JSONParser;
import finapp.publicstatic.com.fintechbankapp.R;
import finapp.publicstatic.com.fintechbankapp.WebServiceAddress;

public class BillsFragment extends Fragment {

    private static final String ARG_PARAM = "param";
    private static String mUserId;

    private Spinner spinnerBillType;
    private RecyclerView recyclerView;

    List<Bills> billsList = new ArrayList<>();
    List<Schedule> schedulesList = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public BillsFragment() {
        // Required empty public constructor
    }

    public static BillsFragment newInstance() {
        BillsFragment fragment = new BillsFragment();
        return fragment;
    }

    public static BillsFragment newInstance(String param) {
        BillsFragment fragment = new BillsFragment();
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
        View view = inflater.inflate(R.layout.fragment_bill, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        spinnerBillType = (Spinner) view.findViewById(R.id.spinner_bill_type);

        recyclerView.addOnItemTouchListener(new BillRecyclerTouchListener
                (getActivity().getApplicationContext(), recyclerView, new BillRecyclerTouchListener.ClickListener() {
            public void onClick(View view, int position) {

                if(spinnerBillType.getSelectedItemPosition() == 0){
                    TextView tvBillId = (TextView) view.findViewById(R.id.bill_id) ;
                    Intent intent = new Intent(view.getContext(), BillScheduleActivity.class);
                    intent.putExtra("userId", mUserId);
                    intent.putExtra("billId", tvBillId.getText().toString());
                    startActivityForResult(intent, 10001);
                }
            }

            public void onLongClick(View view, int position) {
            }
        }));

        spinnerBillType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                // get the position
                // 0 -> get All Unpaid Bills
                // 1 -> get Bill Schedule

                if (position == 0) {
                    BillTask();
                } else if (position == 1) {
                    BillScheduleTask();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;

    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        if ((reqCode == 10001) && (resultCode == BillScheduleActivity.RESULT_OK)){
            billsList = new ArrayList<>();
            BillTask();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void BillTask() {
        billsList = new ArrayList<>();
        schedulesList = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        WebServiceAddress webServiceAddress = new WebServiceAddress();
        final String TAG_SUCCESS = "success";
        int success = 1;

        HashMap<String, String> params = new HashMap<>();
        params.put("userId", mUserId);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JSONObject json = jsonParser.makeHttpRequest(
                "POST", webServiceAddress.getBaseUrl
                        ("getAllBillsByUserId"), params);
        if (json != null) {
            try {
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    JSONArray jsonArray = json.getJSONArray("bills");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Bills bills = new Bills();
                        bills.setBillId(obj.getInt("billsId"));
                        bills.setBillAmount(obj.getDouble("billAmount"));
                        bills.setDueDate(formatDate(obj.getString("dueDate")));
                        bills.setIssuerName(obj.getString("issuerName"));
                        bills.setComment(obj.getString("comment"));
                        bills.setCreatedAt(formatDate(obj.getString
                                ("createdAt")));
                        bills.setUserId(Integer.parseInt(mUserId));

                        //Add your values in your `ArrayList` as below:
                        billsList.add(bills);
                    }
                }
                BillAdapter billAdapter = new BillAdapter(billsList);
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
                recyclerView.setAdapter(billAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void BillScheduleTask() {
        billsList = new ArrayList<>();
        schedulesList = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        WebServiceAddress webServiceAddress = new WebServiceAddress();
        final String TAG_SUCCESS = "success";
        int success = 1;

        HashMap<String, String> params = new HashMap<>();
        params.put("userId", mUserId);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JSONObject json = jsonParser.makeHttpRequest(
                "POST", webServiceAddress.getBaseUrl
                        ("getAllBillScheduleByUserId"), params);
        if (json != null) {
            try {
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    JSONArray jsonArray = json.getJSONArray("schedule");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Schedule schedule = new Schedule();
                        schedule.setScheduleId(obj.getInt("scheduleId"));
                        schedule.setAmount(obj.getDouble("billAmount"));
                        schedule.setDate(formatDate(obj.getString("date")));
                        schedule.setIssuerName(obj.getString("issuerName"));

                        //Add your values in your `ArrayList` as below:
                        schedulesList.add(schedule);
                    }
                }
                BillScheduleAdapter billScheduleAdapter = new BillScheduleAdapter(schedulesList);
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
                recyclerView.setAdapter(billScheduleAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private Date formatDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}



