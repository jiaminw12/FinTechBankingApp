package Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.Accounts;
import Model.Cards;
import finapp.publicstatic.com.fintechbankapp.R;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {
    private List<Cards> cardList = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView cardName;
        public TextView cardNum;
        public TextView cardExpiryDate;
        public TextView activate;

        public ViewHolder(View itemView) {
            super(itemView);
            cardName = (TextView)itemView.findViewById(R.id.cardName);
            cardNum = (TextView)itemView.findViewById(R.id.cardNum);
            cardExpiryDate = (TextView)itemView.findViewById(R.id.cardExpiryDate);
            activate = (TextView)itemView.findViewById(R.id.activate);
        }
    }

    public CardsAdapter() {
    }

    public CardsAdapter(List<Cards> cardList) {
        this.cardList = cardList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragments_cards_cardview, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Cards cards = cardList.get(position);
        viewHolder.cardName.setText(String.valueOf(cards.getCardName()));
        viewHolder.cardNum.setText(String.valueOf(cards.getCardNum()));
        viewHolder.cardExpiryDate.setText(String.valueOf(cards.getCardExpiryDate()));
        if(cards.getActivate() == 0) {
            viewHolder.activate.setText("Not activated");
        } else {
            viewHolder.activate.setText("Activated");
        }
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}