package layout;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import Adapter.BillTransHistoryAdapter;
import finapp.publicstatic.com.fintechbankapp.R;
import finapp.publicstatic.com.fintechbankapp.RetrieveHistoryOfBillTransac;

public class BillTransferHistoryFragment extends Fragment {

    private static final String ARG_PARAM = "param";
    private static String mUserId;

    private Spinner spinnerDate;
    private Spinner spinnerView;
    private RecyclerView recyclerView;

    List<BillTransHistoryAdapter.HistoryListItem> items = new ArrayList<>();

    BillTransHistoryAdapter billTransHistoryAdapter;

    RetrieveHistoryOfBillTransac retrieveHistoryOfBillTransac ;

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

        spinnerDate = (Spinner) historyFragment.findViewById(R.id.spinner_choice_day);
        spinnerView = (Spinner) historyFragment.findViewById(R.id.spinner_view);

        recyclerView = (RecyclerView) historyFragment.findViewById(R.id
                .recycleView_history);

        retrieveHistoryOfBillTransac = new RetrieveHistoryOfBillTransac();

        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                showView(retrieveHistoryOfBillTransac.retrieveHistoryView
                        (position,
                        spinnerView.getSelectedItemPosition(), mUserId));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                showView(retrieveHistoryOfBillTransac.retrieveHistoryView(
                        spinnerDate.getSelectedItemPosition(), position,
                        mUserId));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        return historyFragment;
    }

    private void showView(List<BillTransHistoryAdapter.HistoryListItem> items){
        billTransHistoryAdapter = new BillTransHistoryAdapter
                (this.getActivity(), items);
        billTransHistoryAdapter.setMode(ExpandableRecyclerAdapter.MODE_ACCORDION);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(billTransHistoryAdapter);
    }

}
