package Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Model.Accounts;
import finapp.publicstatic.com.fintechbankapp.R;

public class AccountAdapterTransfer extends ArrayAdapter<Accounts> {

    private Context context;
    private ArrayList<Accounts> accountList;
    int resource;
    int count;

    public AccountAdapterTransfer(Context context, int textViewResourceId,
                                  ArrayList<Accounts> accountList) {
        super(context, textViewResourceId, accountList);
        this.context = context;
        this.resource = textViewResourceId;
        this.accountList = accountList;
        this.count = accountList.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTextSize(18);
        label.setPadding(2,2,2,2);
        label.setText(accountList.get(position).getAccountNum());
        return label;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView,
                              ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_account_transfer_row, parent, false);
        }

        TextView accountId = (TextView) convertView.findViewById(R.id.acctId);
        TextView accountNum = (TextView) convertView.findViewById(R.id
                .acctNum);
        TextView accountAmt = (TextView) convertView.findViewById(R.id
                .acctAmt);

        accountId.setText(String.valueOf(accountList.get(position)
                .getAccountID()));
        accountAmt.setText(String.format("%.2f", accountList.get
                (position).getAmount()));
        accountNum.setText(accountList.get(position).getAccountNum());
        return convertView;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
