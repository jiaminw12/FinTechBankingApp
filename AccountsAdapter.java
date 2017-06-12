package Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.Accounts;
import finapp.publicstatic.com.fintechbankapp.R;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.ViewHolder> {
    private List<Accounts> accountList = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView typeName;
        public TextView accountNum;
        public TextView amount;

        public ViewHolder(View itemView) {
            super(itemView);
            typeName = (TextView)itemView.findViewById(R.id.typeName);
            accountNum = (TextView)itemView.findViewById(R.id.accountNum);
            amount = (TextView)itemView.findViewById(R.id.amount);
        }
    }

    public AccountsAdapter() {
    }

    public AccountsAdapter(List<Accounts> accountList) {
        this.accountList = accountList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragments_accounts_cardview, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Accounts account = accountList.get(position);
        viewHolder.typeName.setText(account.getTypeName());
        viewHolder.accountNum.setText(account.getAccountNum());
        viewHolder.amount.setText(String.valueOf(account.getAmount()));
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }
}