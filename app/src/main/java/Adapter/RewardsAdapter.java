package Adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Model.Rewards;
import finapp.publicstatic.com.fintechbankapp.R;
import finapp.publicstatic.com.fintechbankapp.RewardsActivity;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.MyViewHolder> {
    private List<Rewards> rewardList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView cardNum;
        public TextView totalPts;
        public TextView cardId;
        public TextView userId;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardViewReward);
            cardNum = (TextView) itemView.findViewById(R.id.title_card_num);
            totalPts = (TextView) itemView.findViewById(R.id.title_totalPoints);
            cardId = (TextView) itemView.findViewById(R.id.title_card_id);
            userId = (TextView) itemView.findViewById(R.id.title_user_id);
        }
    }

    public RewardsAdapter(List<Rewards> rewardList) {
        this.rewardList = rewardList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View bankView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_reward_cardview, viewGroup, false);
        return new MyViewHolder(bankView);
    }

    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        final Rewards reward = rewardList.get(position);
        viewHolder.cardNum.setText(reward.getCardNum());
        viewHolder.totalPts.setText("Total Points : " + String.valueOf(reward
                .getPoints()));
        viewHolder.cardId.setText(String.valueOf(reward.getCardId()));
        viewHolder.userId.setText(String.valueOf(reward.getUserId()));

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RewardsActivity.class);
                intent.putExtra("userId", String.valueOf(reward.getUserId()));
                intent.putExtra("cardId", String.valueOf(reward.getCardId()));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

}