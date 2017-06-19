package layout;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.google.zxing.client.android.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapter.CardsAdapter;
import Model.Cards;
import finapp.publicstatic.com.fintechbankapp.BillScheduleActivity;
import finapp.publicstatic.com.fintechbankapp.CardRecyclerTouchListener;
import finapp.publicstatic.com.fintechbankapp.JSONParser;
import finapp.publicstatic.com.fintechbankapp.R;
import finapp.publicstatic.com.fintechbankapp.TransSuccessfulActivity;
import finapp.publicstatic.com.fintechbankapp.WebServiceAddress;
import it.michelelacorte.swipeablecard.OptionView;
import it.michelelacorte.swipeablecard.OptionViewAdditional;
import it.michelelacorte.swipeablecard.SwipeableCard;
import it.michelelacorte.swipeablecard.SwipeableCardAdapter;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.CAMERA_SERVICE;

public class WalletFragment extends Fragment {

    private static final String ARG_PARAM = "param";
    private static String mUserId;
    private CardsAdapter cardsAdapter;
    private CardsTask mCardsTask = null;
    private List<Cards> cardList = new ArrayList<>();
    private static final int REQUEST_USE_CAMERA = 0;

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

        mayRequestCamera();

        Button btnAddCard = (Button) v.findViewById(R.id.add_card_button);
        btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),
                        com.cooltechworks.creditcarddesign.CardEditActivity.class);
                startActivityForResult(intent, GET_NEW_CARD);
            }
        });

        rv = (RecyclerView) v.findViewById(R.id.recycler_view_cards);
        rv.addOnItemTouchListener(new CardRecyclerTouchListener
                (getActivity().getApplicationContext(), rv, new
                        CardRecyclerTouchListener.ClickListener() {
                            public void onClick(View view, int position) {
                                TextView cardId = (TextView) view.findViewById(R
                                        .id.card_Id);
                                TextView accountId = (TextView) view
                                        .findViewById(R
                                        .id.card_account_id);

                                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                                intent.setAction("com.google.zxing.client.android.SCAN");
                                intent.putExtra("SAVE_HISTORY", true);
                                startActivityForResult(intent, 10001);
                            }

                            public void onLongClick(View view, int position) {
                            }
                        }));


        cardsAdapter = new CardsAdapter(cardList);
        mCardsTask = new CardsTask(mUserId);
        mCardsTask.execute((String) null);
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setAdapter(cardsAdapter);

        return v;
    }

    private boolean mayRequestCamera() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(getActivity(), CAMERA) ==
                PackageManager
                        .PERMISSION_GRANTED) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new
                            String[]{CAMERA},
                    REQUEST_USE_CAMERA);
        }
        return false;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent
            intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        Fragment fragment = getFragmentManager().findFragmentById(R.layout
                .fragment_wallet);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, intent);
        }

        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == GET_NEW_CARD) {
                    String cardHolderName = intent.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
                    String cardNumber = intent.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
                    String expiry = intent.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
                    String cvv = intent.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);

                    addCard(cardHolderName, cardNumber, expiry, cvv);

                } else if (requestCode == 10001) {
                    //get the extras that are returned from the intent
                    String contents = intent.getStringExtra("SCAN_RESULT");

                    MaterialDialog materialDialog = new MaterialDialog.Builder
                            (getActivity())
                            .title(R.string.string_dialog_title)
                            .positiveText(R.string.input_positiveText)
                            .negativeText(R.string.input_negativeText)
                            .inputRangeRes(6, 6, R.color.colorPrimary)
                            .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                            .input(null, null, new
                                    MaterialDialog.InputCallback() {
                                        @Override
                                        public void onInput(MaterialDialog dialog, CharSequence input) {
                                            // Do something
                                        }
                                    })
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                }
                            }).show();

                }
                break;
            case RESULT_CANCELED:
                break;
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
                    if (success == 1) {
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

    private void addCard(String cardHolderName, String  cardNumber, String
            expiry, String  ccv){
        JSONParser jsonParser = new JSONParser();
        WebServiceAddress webServiceAddress = new WebServiceAddress();
        String TAG_SUCCESS = "success";
        int success = 0;

        HashMap<String, String> params = new HashMap<>();

        params.put("userId", mUserId);
        params.put("accountId", mAcctId);
        params.put("cardTypeId", mAmt);
        params.put("cardNum", cardNumber);
        params.put("ccv", ccv);
        params.put("expiryDate", expiry);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JSONObject json = jsonParser.makeHttpRequest(
                "POST", webServiceAddress.getBaseUrl
                        ("addTransaction"), params);

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
        }
    }

    private void transferSubmit(){
        String mAmt = amtEditText.getText().toString();
        String mComment = commentEdittext.getText().toString();
        int postionCategory = spinnerCategory.getSelectedItemPosition() + 1;

        if(mAmt.equals("")){
            Toast.makeText(getActivity(), R.string.error_empty_amount, Toast.LENGTH_LONG).show();
        }  else if (Double.parseDouble(mAmt) > Double.parseDouble(mAcctAmount)){
            Toast.makeText(getActivity(), R.string.error_amount, Toast.LENGTH_LONG).show();
        } else {

            double diffAmt = Double.parseDouble(mAcctAmount) - Double
                    .parseDouble(mAmt);

            JSONParser jsonParser = new JSONParser();
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
                            ("addTransaction"), params);

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
            }
        }
    }


}
