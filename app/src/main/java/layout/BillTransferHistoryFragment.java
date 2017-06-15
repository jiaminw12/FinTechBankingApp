package layout;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import finapp.publicstatic.com.fintechbankapp.JSONParser;
import finapp.publicstatic.com.fintechbankapp.R;
import finapp.publicstatic.com.fintechbankapp.TransSuccessfulActivity;
import finapp.publicstatic.com.fintechbankapp.WebServiceAddress;

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


    private void showYearlyMonthlyView(){

        // https://github.com/thoughtbot/expandable-recycler-view
        // https://gist.github.com/devrath/1e7af99e468b429a6799
        // https://gist.github.com/yuviii/accc3d817005940e366f
        // http://codegists.com/code/expandable-recyclerview-android-example-androidhive/

        //{"history":[{"date":"2017-7","total":"15.00","transRow":[{"transactionId":"7","transactionAmount":"15.00","createdAt":"2017-07-03 07:37:13","payeeName":"Rubi Olaughlin"}]},{"date":"2017-6","total":"3414.90","transRow":[{"transactionId":"6","transactionAmount":"10.00","createdAt":"2017-06-14 15:56:08","payeeName":"Peggy Criss"},

        /*JSONParser jsonParser = new JSONParser();
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
        params.put("amountLeft", String.valueOf(diffAmt));

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
                    Intent intent = new Intent(getActivity(),
                            TransSuccessfulActivity.class);
                    intent.putExtra("amount", mAmt);
                    intent.putExtra("payeeName", mPayeeName);

                    if (mComment.equals("")) {
                        intent.putExtra("comments", "");
                    } else {
                        intent.putExtra("comments", mComment);
                    }
                    startActivityForResult(intent, 10001);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/

    }

}
