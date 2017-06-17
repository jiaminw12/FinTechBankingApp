package layout;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.text.TextUtils;
import android.widget.Spinner;

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
import Model.Bills;
import finapp.publicstatic.com.fintechbankapp.JSONParser;
import finapp.publicstatic.com.fintechbankapp.R;
import finapp.publicstatic.com.fintechbankapp.WebServiceAddress;

public class BillsFragment extends Fragment {

    private static final String ARG_PARAM = "param";
    private static String mUserId;

    private SearchView searchBills;
    private Spinner spinnerDate;
    private RecyclerView recyclerView;

    List<Bills> billsList = new ArrayList<>();

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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_bill, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        //searchBills = (SearchView) view.findViewById(R.id.search_bills);
        spinnerDate = (Spinner) view.findViewById(R.id.spinner_bill_date);

        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                // get the position
                // 0 -> get All Unpaid Bills
                // 1 -> get Bill Schedule

                if (position == 0) {
                    BillTask();
                } else if (position == 1) {
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;

    }

    /*
            //For the list, search and date spinner
    private void showView(List<BillScheduleAdapter.BillListItem> billItems) {
        billScheduleAdapter=new BillScheduleAdapter( this.getActivity() billItems);
        billScheduleAdapter.setMode()
        billScheduleAdapter.setLayoutManager(new LinearLayoutManager.(this));
        //"String" to include the class for reference. and to complete the referencing of the list in adapter.
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1, _____);
        listBills.setAdapter(listAdapter);
        listBills.setTextFilterEnabled(true);
        setupSearchView();
    }
    //starts new activity on click for the individual items.
    public void onListItemClick(ListView listBills, View itemView, int position, long id) {
        Intent intent=new Intent(BillsFragment.this, BillsPayment );
        startActivity(intent);
    }

    //search Function
    private void setupSearchView(SearchView mSearchView) {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }
    public boolean onQueryTextChange(ListView listBills, String newText) {
        if (TextUtils.isEmpty(newText)) {
            listBills.clearTextFilter();
        } else {
            listBills.setFilterText(newText.toString());
        }
        return true;
    }
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
*/

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void BillTask() {
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

    private Date formatDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd " +
                "hh:mm:ss");

        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}



