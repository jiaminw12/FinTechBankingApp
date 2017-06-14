package Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
            cardName = (TextView)itemView.findViewById(R.id.title_card_name);
            cardNum = (TextView)itemView.findViewById(R.id.title_card_num);
            cardExpiryDate = (TextView)itemView.findViewById(R.id.title_expiryDate);
            activate = (TextView)itemView.findViewById(R.id.title_activated);
        }
    }

    public CardsAdapter(List<Cards> cardList) {
        this.cardList = cardList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View cardsView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_wallet_cardview, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(cardsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Cards cards = cardList.get(position);
        viewHolder.cardName.setText(String.valueOf(cards.getCardName()));
        viewHolder.cardNum.setText(String.valueOf(cards.getCardNum()));
        viewHolder.cardExpiryDate.setText(String.valueOf("Expiry Date: " + cards.getCardExpiryDate()));
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