package Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import finapp.publicstatic.com.fintechbankapp.R;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.MyViewHolder> {
    private List<String> bankList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView bankID;

        public MyViewHolder(View view) {
            super(view);
            bankID = (TextView) view.findViewById(R.id.bankID);
        }
    }

    public BankAdapter() {
    }

    public BankAdapter(List<String> bankList) {
        this.bankList = bankList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragments_bank_row, parent, false);
        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bankID.setText(bankList.get(position));
    }

    public int getItemCount() {
        return bankList.size();
    }
}