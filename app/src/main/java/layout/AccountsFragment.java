package layout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import java.util.List;

import Adapter.AccountsAdapter;
import Model.Accounts;
import finapp.publicstatic.com.fintechbankapp.AccountsCardActivity;
import finapp.publicstatic.com.fintechbankapp.AccountsDividerItemDecoration;
import finapp.publicstatic.com.fintechbankapp.AccountsRecyclerTouchListener;
import finapp.publicstatic.com.fintechbankapp.JSONParser;
import finapp.publicstatic.com.fintechbankapp.R;
import finapp.publicstatic.com.fintechbankapp.WebServiceAddress;

public class AccountsFragment extends Fragment {
    private List<Accounts> accountList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AccountsAdapter accountsAdapter;
    private AccountsFragment.OnFragmentInteractionListener mListener;

    private AccountsTask mAuthAcc = null;

    private static final String ARG_PARAM = "param";
    private static String mUserId;

    public static AccountsFragment newInstance() {
        AccountsFragment fragment = new AccountsFragment();
        return fragment;
    }

    public static AccountsFragment newInstance(String param) {
        AccountsFragment fragment = new AccountsFragment();
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
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        accountsAdapter = new AccountsAdapter(accountList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new AccountsDividerItemDecoration(this.getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(new AccountsRecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new AccountsRecyclerTouchListener.ClickListener() {
            public void onClick(View view, int position) {
                TextView v = (TextView) view.findViewById(R.id.accountID);
                Toast.makeText(getActivity(), v.getText().toString() + " is selected!",
                        Toast
                        .LENGTH_SHORT).show();

                Intent i = new Intent(getActivity(), AccountsCardActivity.class);
                startActivity(i);
            }

            public void onLongClick(View view, int position) {

            }
        }));

        prepareAccountList(mUserId);
        recyclerView.setAdapter(accountsAdapter);

        return view;
    }

    private void prepareAccountList(String userId){
        mAuthAcc = new AccountsFragment.AccountsTask(userId);
        mAuthAcc.execute((String) null);
    }

    public class AccountsTask extends AsyncTask<String, String, JSONObject> {
        private JSONParser jsonParser = new JSONParser();
        private WebServiceAddress webServiceAddress = new WebServiceAddress();
        private static final String TAG_SUCCESS = "success";
        private final String mUserid;

        AccountsTask(String userId) {
            mUserid = userId;
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            try {
                // Simulate network access.
                Thread.sleep(2000);

                HashMap<String, String> params = new HashMap<>();
                params.put("userId", mUserid);
                JSONObject json = jsonParser.makeHttpRequest(
                        "POST", webServiceAddress.getBaseUrl
                                ("getAllAccountsByUserid"), params);

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
                            Accounts account = new Accounts();
                            account.setAccountId(obj.getInt("accountId"));
                            account.setAccountNum(obj.getString("accountNum"));
                            account.setAmount(obj.getDouble("amount"));
                            account.setBankname(obj.getString("bankname"));
                            account.setType_name(obj.getString("type_name"));

                            //Add your values in your `ArrayList` as below:
                            accountList.add(account);
                        }
                    }
                    recyclerView.setAdapter(accountsAdapter);
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
        if (context instanceof AccountsFragment.OnFragmentInteractionListener) {
            mListener = (AccountsFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}