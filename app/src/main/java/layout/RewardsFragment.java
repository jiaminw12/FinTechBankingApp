package layout;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Adapter.RewardsAdapter;
import Model.Rewards;
import finapp.publicstatic.com.fintechbankapp.JSONParser;
import finapp.publicstatic.com.fintechbankapp.R;
import finapp.publicstatic.com.fintechbankapp.WebServiceAddress;

public class RewardsFragment extends Fragment {

    private ArrayList<Rewards> rewardList = new ArrayList<>();
    RewardsAdapter rewardsAdapter;
    private RecyclerView recyclerView;
    LinearLayoutManager llm;
    RewardsTask rewardsTask;

    private static final String ARG_PARAM = "param";
    private static String mUserId;

    private OnFragmentInteractionListener mListener;

    public RewardsFragment() {
        // Required empty public constructor
    }

    public static RewardsFragment newInstance() {
        RewardsFragment fragment = new RewardsFragment();
        return fragment;
    }

    public static RewardsFragment newInstance(String param) {
        RewardsFragment fragment = new RewardsFragment();
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
        View rewardFrag =  inflater.inflate(R.layout.fragment_reward, container,
                false);

        recyclerView = (RecyclerView) rewardFrag.findViewById(R.id
                .recycler_view_reward);
        rewardsAdapter = new RewardsAdapter(rewardList);

        rewardsTask = new RewardsTask(mUserId);
        rewardsTask.execute((String) null);

        llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(rewardsAdapter);



        return rewardFrag;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class RewardsTask extends AsyncTask<String, String, JSONObject> {
        private JSONParser jsonParser = new JSONParser();
        private WebServiceAddress webServiceAddress = new WebServiceAddress();
        private static final String TAG_SUCCESS = "success";
        private final String mUserid;

        RewardsTask(String userId) {
            mUserid = userId;
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            try {
                // Simulate network access.
                Thread.sleep(2000);

                HashMap<String, String> params = new HashMap<>();
                params.put("userId", mUserid);

                JSONObject json = jsonParser.makeHttpRequest("POST", webServiceAddress.getBaseUrl("getAllSumRewardPtsByUserId"), params);

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
                        JSONArray jsonArray = json.getJSONArray("rewards");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Rewards rewards = new Rewards();
                            rewards.setPoints(obj.getInt("totalPoints"));
                            rewards.setCardId(obj.getInt("cardId"));
                            rewards.setCardNum(obj.getString("cardNum"));
                            rewards.setUserId(Integer.parseInt(mUserid));

                            //Add your values in your `ArrayList` as below:
                            rewardList.add(rewards);
                        }
                    }
                    recyclerView.setAdapter(rewardsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onCancelled() {
        }
    }

}
