package Adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Model.Rewards;
import finapp.publicstatic.com.fintechbankapp.R;
import finapp.publicstatic.com.fintechbankapp.RewardsActivity;

public class RewardsAdapterForDetails extends RecyclerView.Adapter<RewardsAdapterForDetails.MyViewHolder> {
    private List<Rewards> rewardList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView totalPts;
        public TextView expiryDate;
        public TextView cardId;
        public TextView userId;

        public MyViewHolder(View itemView) {
            super(itemView);
            totalPts = (TextView) itemView.findViewById(R.id.title_totalPoints);
            expiryDate = (TextView) itemView.findViewById(R.id.title_expiryDate);
            cardId = (TextView) itemView.findViewById(R.id.title_card_id);
            userId = (TextView) itemView.findViewById(R.id.title_user_id);
        }
    }

    public RewardsAdapterForDetails(List<Rewards> rewardList) {
        this.rewardList = rewardList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View bankView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_reward_cardview_detail, viewGroup, false);
        return new MyViewHolder(bankView);
    }

    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        final Rewards reward = rewardList.get(position);
        viewHolder.totalPts.setText("Points :  " + String.valueOf(reward
                .getPoints()));

        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String expiryDate = formatter.format(reward.getExpiryAt());

        viewHolder.expiryDate.setText("Expiry at " + expiryDate);
        viewHolder.cardId.setText(String.valueOf(reward.getCardId()));
        viewHolder.userId.setText(String.valueOf(reward.getUserId()));
    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

}