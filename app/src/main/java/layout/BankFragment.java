package layout;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Adapter.BankAdapter;
import Model.Banks;
import finapp.publicstatic.com.fintechbankapp.AccountsActivity;
import finapp.publicstatic.com.fintechbankapp.AccountsCardsTabActivity;
import finapp.publicstatic.com.fintechbankapp.BankDividerItemDecoration;
import finapp.publicstatic.com.fintechbankapp.BankRecyclerTouchListener;
import finapp.publicstatic.com.fintechbankapp.JSONParser;
import finapp.publicstatic.com.fintechbankapp.R;
import finapp.publicstatic.com.fintechbankapp.WebServiceAddress;

public class BankFragment extends Fragment {
    private static String userID;
    private static final String ARG_PARAM = "param";
    private BankAdapter bankAdapter;
    private BankTask mAuthAcc = null;
    private RecyclerView recyclerView;

    private ArrayList<Banks> bankList = new ArrayList<>();
    private BankFragment.OnFragmentInteractionListener mListener;

    public static BankFragment newInstance() {
        BankFragment bankFragment = new BankFragment();
        return bankFragment;
    }

    public static BankFragment newInstance(String parameter) {
        BankFragment bankFragment = new BankFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ARG_PARAM, parameter);
        bankFragment.setArguments(arguments);
        userID = parameter;
        return bankFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userID = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bank, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        bankAdapter = new BankAdapter(bankList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new BankDividerItemDecoration(this.getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(new BankRecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new BankRecyclerTouchListener.ClickListener() {
            public void onClick(View view, int position) {
                TextView textView = (TextView) view.findViewById(R.id.bankID);
                Intent intent = new Intent(getActivity(), AccountsCardsTabActivity.class);
                intent.putExtra("userId", userID);
                intent.putExtra("bankId", textView.getText().toString());
                startActivity(intent);
            }

            public void onLongClick(View view, int position) {
            }
        }));

        prepareBankList(userID);
        recyclerView.setAdapter(bankAdapter);
        return view;
    }

    private void prepareBankList(String userID) {
        mAuthAcc = new BankTask(userID);
        mAuthAcc.execute((String) null);
    }

    public class BankTask extends AsyncTask<String, String, JSONObject> {
        private JSONParser jsonParser = new JSONParser();
        private WebServiceAddress webServiceAddress = new WebServiceAddress();
        private static final String TAG_SUCCESS = "success";
        private final String mUserid;

        BankTask(String userID) {
            mUserid = userID;
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            try {
                // Simulate network access.
                Thread.sleep(2000);

                HashMap<String, String> params = new HashMap<>();
                params.put("userId", mUserid);
                JSONObject json = jsonParser.makeHttpRequest("POST", webServiceAddress.getBaseUrl("getAllAccountsDistinctByUserId"), params);

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
                        JSONArray jsonArray = json.getJSONArray("accounts");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Banks bank = new Banks();
                            bank.setBankId(obj.getInt("bankId"));
                            bank.setBankname(obj.getString("bankname"));

                            bankList.add(bank);
                        }
                    }
                    recyclerView.setAdapter(bankAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onCancelled() {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BankFragment.OnFragmentInteractionListener) {
            mListener = (BankFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}