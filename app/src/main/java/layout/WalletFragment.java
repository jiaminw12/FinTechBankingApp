package layout;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.cooltechworks.creditcarddesign.CreditCardUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapter.CardsAdapter;
import Model.Cards;
import finapp.publicstatic.com.fintechbankapp.JSONParser;
import finapp.publicstatic.com.fintechbankapp.R;
import finapp.publicstatic.com.fintechbankapp.WebServiceAddress;
import it.michelelacorte.swipeablecard.OptionView;
import it.michelelacorte.swipeablecard.OptionViewAdditional;
import it.michelelacorte.swipeablecard.SwipeableCard;
import it.michelelacorte.swipeablecard.SwipeableCardAdapter;

import static android.app.Activity.RESULT_OK;

public class WalletFragment extends Fragment {

    private static final String ARG_PARAM = "param";
    private static String mUserId;
    private CardsAdapter cardsAdapter;
    private CardsTask mCardsTask = null;
    private List<Cards> cardList = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    RecyclerView rv;
    LinearLayoutManager llm;

    final int GET_NEW_CARD = 2;

    public WalletFragment() {
        // Required empty public constructor
    }

    public static WalletFragment newInstance() {
        WalletFragment fragment = new WalletFragment();
        return fragment;
    }

    public static WalletFragment newInstance(String param) {
        WalletFragment fragment = new WalletFragment();
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
        View v = inflater.inflate(R.layout.fragment_wallet, container, false);

        Button btnAddCard = (Button) v.findViewById(R.id.add_card_button);
        btnAddCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(),
                        com.cooltechworks.creditcarddesign.CardEditActivity.class);
                startActivityForResult(intent,GET_NEW_CARD);
            }
        });

        rv = (RecyclerView) v.findViewById(R.id.recycler_view_cards);
        cardsAdapter = new CardsAdapter(cardList);
        mCardsTask = new CardsTask(mUserId);
        mCardsTask.execute((String) null);
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setAdapter(cardsAdapter);

        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {

            String cardHolderName = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            String cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
            String expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
            String cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);

            // Your processing goes here.
            // addCard
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

    public class CardsTask extends AsyncTask<String, String, JSONObject> {
        private JSONParser jsonParser = new JSONParser();
        private WebServiceAddress webServiceAddress = new WebServiceAddress();
        private static final String TAG_SUCCESS = "success";
        private final String mUserid;

        CardsTask(String userId) {
            mUserid = userId;
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            try {
                // Simulate network access.
                Thread.sleep(2000);

                HashMap<String, String> params = new HashMap<>();
                params.put("userId", mUserid);

                JSONObject json = jsonParser.makeHttpRequest("POST", webServiceAddress.getBaseUrl("getAllCardsByUserId"), params);

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
                        JSONArray jsonArray = json.getJSONArray("cards");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Cards cards = new Cards();
                            cards.setActivate(obj.getInt("activate"));
                            cards.setAccountID(obj.getInt("accountId"));
                            cards.setCardsID(obj.getInt("cardId"));
                            cards.setCardTypeID(obj.getInt("cardTypeId"));
                            cards.setCardNum(obj.getString("cardNum"));
                            cards.setCardName(obj.getString("cardName"));
                            cards.setCcv(obj.getString("ccv"));
                            cards.setCardExpiryDate(obj.getString("expiryDate"));

                            //Add your values in your `ArrayList` as below:
                            cardList.add(cards);
                        }
                    }
                    rv.setAdapter(cardsAdapter);
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
