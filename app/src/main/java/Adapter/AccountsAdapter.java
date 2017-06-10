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

        private List<Accounts> moviesList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title, year, genre;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.title);
                genre = (TextView) view.findViewById(R.id.genre);
                year = (TextView) view.findViewById(R.id.year);
            }
        }

     public AccountsAdapter() {
        }

        public AccountsAdapter(List<Accounts> moviesList) {
            this.moviesList = moviesList;
        }

        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragments_accounts_row, parent, false);

            return new MyViewHolder(itemView);
        }

        public void onBindViewHolder(MyViewHolder holder, int position) {
            Accounts movie = moviesList.get(position);
            holder.title.setText(movie.getTitle());
            holder.genre.setText(movie.getGenre());
            holder.year.setText(movie.getYear());
        }

        public int getItemCount() {
            return moviesList.size();
        }
    }

