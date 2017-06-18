package Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.Bills;
import finapp.publicstatic.com.fintechbankapp.R;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    private List<Bills> accountList = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvBillId;
        public TextView tvPayeeName;
        public TextView tvBillAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            tvBillId = (TextView)itemView.findViewById(R.id.bill_id);
            tvPayeeName = (TextView)itemView.findViewById(R.id.bill_payee);
            tvBillAmount = (TextView)itemView.findViewById(R.id.bill_amount);
        }
    }

    public BillAdapter(List<Bills> accountList) {
        this.accountList = accountList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View accountView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_bill_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(accountView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Bills bill = accountList.get(position);
        viewHolder.tvBillId.setText(String.valueOf(bill.getBillId()));
        viewHolder.tvPayeeName.setText(bill.getIssuerName());
        viewHolder.tvBillAmount.setText("$ " + String.format("%.2f", bill
                .getBillAmount()));
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }
}