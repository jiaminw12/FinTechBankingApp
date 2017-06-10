package layout;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import Adapter.AccountsAdapter;
import Model.Accounts;
import finapp.publicstatic.com.fintechbankapp.AccountsDividerItemDecoration;
import finapp.publicstatic.com.fintechbankapp.AccountsRecyclerTouchListener;
import finapp.publicstatic.com.fintechbankapp.R;

public class AccountsFragment extends Fragment {
    private List<Accounts> accountList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AccountsAdapter accountsAdapter;
    private AccountsFragment.OnFragmentInteractionListener mListener;

    private static final String ARG_PARAM1 = "param1";

    public static AccountsFragment newInstance() {
        AccountsFragment fragment = new AccountsFragment();
        return fragment;
    }

    public static AccountsFragment newInstance(String param1) {
        AccountsFragment fragment = new AccountsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        recyclerView.addOnItemTouchListener(new AccountsRecyclerTouchListener(this.getActivity().getApplicationContext(), recyclerView, new AccountsRecyclerTouchListener.ClickListener() {
            public void onClick(View view, int position) {
                Accounts account = accountList.get(position);
                Toast.makeText(getActivity(), account.getBankID() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            public void onLongClick(View view, int position) {

            }
        }));

        prepareAccountList();
        recyclerView.setAdapter(accountsAdapter);

        return view;
    }

    private void prepareAccountList() {
        String jsonString = null;

        try {
            InputStream inputStream = getActivity().getAssets().open("accountsSample.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObj.getJSONArray("accounts");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Accounts account = new Accounts();
                account.setAccountID(obj.getInt("accountID"));
                account.setAccountNum(obj.getString("accountNum"));
                account.setAmount(obj.getInt("amount"));
                account.setBankID(obj.getInt("bankID"));
                account.setTypeID(obj.getInt("typeID"));

                //Add your values in your `ArrayList` as below:
                accountList.add(account);
            }
        } catch (JSONException e) {
            e.printStackTrace();
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