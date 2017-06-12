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

import Model.Payees;
import finapp.publicstatic.com.fintechbankapp.R;

public class PayeeAdapter extends ArrayAdapter<Payees> {

    private Context context;
    private ArrayList<Payees> payeeList;
    int resource;
    int count;

    public PayeeAdapter(Context context, int textViewResourceId,
                        ArrayList<Payees> payeeList) {
        super(context, textViewResourceId, payeeList);
        this.context = context;
        this.resource = textViewResourceId;
        this.payeeList = payeeList;
        this.count = payeeList.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTextSize(18);
        label.setPadding(2,2,2,2);
        label.setText(payeeList.get(position).getPayeeName());
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
            convertView = inflater.inflate(R.layout.fragment_payee_row, parent, false);
        }

        TextView payeeId = (TextView) convertView.findViewById(R.id.payeeId);
        TextView name = (TextView) convertView.findViewById(R.id.payeeName);
        TextView acctNum = (TextView) convertView.findViewById(R.id.payeeAccNum);

        payeeId.setText(String.valueOf(payeeList.get(position).getPayeeId()));
        name.setText(payeeList.get(position).getPayeeName());
        acctNum.setText(payeeList.get(position).getPayeeAcctNum());
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
