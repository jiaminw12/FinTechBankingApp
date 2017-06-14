package layout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import finapp.publicstatic.com.fintechbankapp.R;

public class BillTransferHistoryFragment extends Fragment {

    private static final String ARG_PARAM = "param";
    private static String mUserId;

    private Spinner spinnerDate;
    private Spinner spinnerView;

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
        View historyFragment =  inflater.inflate(R.layout.fragment_transfer,
                container, false);

        spinnerDate = (Spinner) historyFragment.findViewById(R.id.spinner_payee);
        spinnerView = (Spinner) historyFragment.findViewById(R.id.spinner_category);

        // Listener called when spinner item selected
        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });



        return historyFragment;
    }
}
