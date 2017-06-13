package layout;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.text.TextUtils;

import finapp.publicstatic.com.fintechbankapp.R;

public class BillsFragment extends Fragment implements SearchView.OnQueryTextListener {

    private static final String ARG_PARAM = "param";
    private static String mUserId;
    private SearchView mSearchView=(SearchView)findViewById(R.id._bills);

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
        return inflater.inflate(R.layout.fragment_bill, container, false);

        //For the list
        ListView listBills = (ListView)findViewById(R.id.list_bills);
        //"String" to include the class for reference. and to complete the referencing of the list in adapter.
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1, _____);
        listBills.setAdapter(listAdapter);
        listBills.setTextFilterEnabled(true);
        setupSearchView();
    }

    //starts new activity on click for the individual items.
    public void onListItemClick(ListView listview, View itemView, int position, long id) {
        Intent intent=new Intent(BillsFragment.this, BillTransferHistoryFragment );
        startActivity(intent);

    }


    //search Function
    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }

    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            ListBills.clearTextFilter();
        } else {
            ListBills.setFilterText(newText.toString());
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

}



