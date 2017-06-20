package Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Model.Accounts;
import Model.Schedule;
import finapp.publicstatic.com.fintechbankapp.R;

public class BillScheduleAdapter extends RecyclerView.Adapter<BillScheduleAdapter.ViewHolder> {

    private List<Schedule> scheduleList = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvPayeeName;
        public TextView tvScheduleDate;
        public TextView tvAmount;
        public TextView scheduleId;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPayeeName = (TextView)itemView.findViewById(R.id.tv_payee_name);
            tvScheduleDate = (TextView)itemView.findViewById(R.id.tv_schedule_date);
            tvAmount = (TextView)itemView.findViewById(R.id.tv_amount);
            scheduleId = (TextView)itemView.findViewById(R.id.tv_schedule_id);
        }
    }

    public BillScheduleAdapter(List<Schedule> accountList) {
        this.scheduleList = accountList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View accountView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_bill_schedule_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(accountView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Schedule schedule = scheduleList.get(position);
        viewHolder.tvPayeeName.setText(schedule.getIssuerName());

        Format formatter = new SimpleDateFormat("yyyy-mm-dd");
        String schDate = formatter.format(schedule.getDate());

        viewHolder.tvScheduleDate.setText(schDate);
        viewHolder.tvAmount.setText("$ " + String.format("%.2f", schedule.getAmount()));
        viewHolder.scheduleId.setText(String.valueOf(schedule.getScheduleId()));
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }



}