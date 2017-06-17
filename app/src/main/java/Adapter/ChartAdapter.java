package Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Model.Charts;
import finapp.publicstatic.com.fintechbankapp.R;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.MyViewHolder> {
    private List<Charts> chartList;
    int colorcodes[];

    public class MyViewHolder extends RecyclerView.ViewHolder {

        View textViewColor;
        TextView textViewCategory;
        TextView textViewAmount;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewColor = (View) itemView.findViewById(R.id.item_color);
            textViewCategory = (TextView) itemView.findViewById(R.id.item_category);
            textViewAmount = (TextView) itemView.findViewById(R.id.item_amount);
        }
    }

    public ChartAdapter(List<Charts> chartList, int colorcodes[]) {
        this.chartList = chartList;
        this.colorcodes = colorcodes;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View bankView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_planner_chart, viewGroup, false);
        return new MyViewHolder(bankView);
    }

    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        final Charts charts = chartList.get(position);
        viewHolder.textViewColor.setBackgroundColor(colorcodes[position]);
        viewHolder.textViewCategory.setText(charts.getCategory());
        viewHolder.textViewAmount.setText("$ " + charts.getAmount());
    }

    @Override
    public int getItemCount() {
        return chartList.size();
    }

}