package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import finapp.publicstatic.com.fintechbankapp.R;

public class BillTransHistoryAdapter extends ExpandableRecyclerAdapter<BillTransHistoryAdapter
        .HistoryListItem> {

    public static final int TYPE_PERSON = 1001;

    private List<HistoryListItem> items;

    public BillTransHistoryAdapter(Context context, List<HistoryListItem> cloneItems) {
        super(context);
        items = new ArrayList<>(cloneItems);
        setItems(items);
    }

    public static class HistoryListItem extends ExpandableRecyclerAdapter.ListItem {
        public String Text, mTotalAmt, mAmount, mPayee;
        public Date mDate;

        public HistoryListItem(String dateString, String mTotalAmt) {
            super(TYPE_HEADER);
            Text = dateString;
            this.mTotalAmt = mTotalAmt;
        }

        public HistoryListItem(Date itemDate, String itemAmount, String
                itemPayee) {
            super(TYPE_PERSON);
            mDate = itemDate;
            mAmount = itemAmount;
            mPayee = itemPayee;
        }
    }

    public class HeaderViewHolder extends ExpandableRecyclerAdapter.HeaderViewHolder {
        TextView name;
        TextView totalAmount;

        public HeaderViewHolder(View view) {
            super(view, (ImageView) view.findViewById(R.id.item_arrow));

            name = (TextView) view.findViewById(R.id.item_header_name);
            totalAmount = (TextView) view.findViewById(R.id.item_header_amount);
        }

        public void bind(int position) {
            super.bind(position);

            name.setText(visibleItems.get(position).Text);
            totalAmount.setText("$ " + visibleItems.get(position).mTotalAmt);
        }
    }

    public class PersonViewHolder extends ExpandableRecyclerAdapter.ViewHolder {
        TextView textViewDate;
        TextView textViewAmount;
        TextView textViewPayee;

        public PersonViewHolder(View view) {
            super(view);

            textViewDate = (TextView) view.findViewById(R.id.item_date);
            textViewAmount = (TextView) view.findViewById(R.id.item_amount);
            textViewPayee = (TextView) view.findViewById(R.id.item_payeeName);
        }

        public void bind(int position) {

            Format formatter = new SimpleDateFormat("yyyy-MM-dd");

            textViewDate.setText(formatter.format(visibleItems.get
                    (position).mDate));
            textViewPayee.setText(visibleItems.get(position).mPayee);
            textViewAmount.setText("$ " + visibleItems.get(position).mAmount);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(inflate(R.layout.list_head_history,
                        parent));
            case TYPE_PERSON:
            default:
                return new PersonViewHolder(inflate(R.layout.list_item_history, parent));
        }
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerAdapter.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((HeaderViewHolder) holder).bind(position);
                break;
            case TYPE_PERSON:
            default:
                ((PersonViewHolder) holder).bind(position);
                break;
        }
    }

}
