package Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Model.Accounts;
import finapp.publicstatic.com.fintechbankapp.R;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.MyViewHolder> {
    /*
       Account ID:
       User ID: ??
       Bank ID: 0 - DBS, 1 - Maybank, 2 - OCBC, 3 - Standard Chartered, 4 - HSBC, 5 - UOB
       Account Num: Get from JSON
       Type ID: 0 - Credit Card, 1- Debit Card, 2 - Savings Account, 3 - Current Account, 4 - POSB Everyday Card, 5 - OCBC 365 Card
       Amount: Get from JSON
    */
    private List<Accounts> accountList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //public TextView title, year, genre;
        public TextView accountID, bankID, accountNum, typeID, amount;

        public MyViewHolder(View view) {
            super(view);
            accountID = (TextView) view.findViewById(R.id.accountID);
            bankID = (TextView) view.findViewById(R.id.bankID);
            accountNum = (TextView) view.findViewById(R.id.accountNum);
            typeID = (TextView) view.findViewById(R.id.typeID);
            amount = (TextView) view.findViewById(R.id.amount);
        }
    }

    public AccountsAdapter() {
    }

    public AccountsAdapter(List<Accounts> accountList) {
        this.accountList = accountList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragments_accounts_row, parent, false);

        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        Accounts account = accountList.get(position);
        holder.accountID.setText(String.valueOf(account.getAccountID()));
        holder.bankID.setText(account.getBankID());
        holder.accountNum.setText(account.getAccountNum());
        holder.typeID.setText(account.getTypeID());
        holder.amount.setText(String.valueOf(account.getAmount()));
    }

    public int getItemCount() {
        return accountList.size();
    }
}

