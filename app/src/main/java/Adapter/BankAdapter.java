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

        public MyViewHolder(View itemView) {
            super(itemView);
            bankName = (TextView) itemView.findViewById(R.id.bankName);
            bankID = (TextView) itemView.findViewById(R.id.bankID);
        }
    }

    public BankAdapter(List<Banks> bankList) {
        this.bankList = bankList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View bankView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_bank_row, viewGroup, false);
        return new MyViewHolder(bankView);
    }

    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        Banks bank = bankList.get(position);
        viewHolder.bankName.setText(bank.getBankname());
        viewHolder.bankID.setText(String.valueOf(bank.getBankId()));
    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }
}