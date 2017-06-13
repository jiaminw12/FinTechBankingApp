package Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Model.Banks;
import finapp.publicstatic.com.fintechbankapp.R;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.MyViewHolder> {
    private List<Banks> bankList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView bankName;
        public TextView bankID;

        public MyViewHolder(View view) {
            super(view);
            bankName = (TextView) view.findViewById(R.id.bankName);
            bankID = (TextView) view.findViewById(R.id.bankID);
        }
    }

    public BankAdapter() {
    }

    public BankAdapter(List<Banks> bankList) {
        this.bankList = bankList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_bank_row, parent, false);
        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        Banks bank = bankList.get(position);
        holder.bankName.setText(bank.getBankname());
        holder.bankID.setText(String.valueOf(bank.getBankId()));
    }

    public int getItemCount() {
        return bankList.size();
    }
}