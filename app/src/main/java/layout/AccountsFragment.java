package layout;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Adapter.AccountsAdapter;
import Model.Accounts;
import finapp.publicstatic.com.fintechbankapp.R;

public class AccountsFragment extends Fragment {
    private List<Accounts> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AccountsAdapter mAdapter;

    private AccountsFragment.OnFragmentInteractionListener mListener;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static AccountsFragment newInstance() {
        AccountsFragment fragment = new AccountsFragment();
        return fragment;
    }

    public static AccountsFragment newInstance(String param1) {
        AccountsFragment fragment = new AccountsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_accounts, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        mAdapter = new AccountsAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        prepareMovieData();
        recyclerView.setAdapter(mAdapter);

        return v;
    }

    private void prepareMovieData() {
        Accounts movie = new Accounts("Mad Max: Fury Road", "Action & Adventure", "2015");
        movieList.add(movie);

        movie = new Accounts("Inside Out", "Animation, Kids & Family", "2015");
        movieList.add(movie);

        movie = new Accounts("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        movieList.add(movie);

        movie = new Accounts("Shaun the Sheep", "Animation", "2015");
        movieList.add(movie);

        movie = new Accounts("The Martian", "Science Fiction & Fantasy", "2015");
        movieList.add(movie);

        movie = new Accounts("Mission: Impossible Rogue Nation", "Action", "2015");
        movieList.add(movie);

        movie = new Accounts("Up", "Animation", "2009");
        movieList.add(movie);

        movie = new Accounts("Star Trek", "Science Fiction", "2009");
        movieList.add(movie);

        movie = new Accounts("The LEGO Movie", "Animation", "2014");
        movieList.add(movie);

        movie = new Accounts("Iron Man", "Action & Adventure", "2008");
        movieList.add(movie);

        movie = new Accounts("Aliens", "Science Fiction", "1986");
        movieList.add(movie);

        movie = new Accounts("Chicken Run", "Animation", "2000");
        movieList.add(movie);

        movie = new Accounts("Back to the Future", "Science Fiction", "1985");
        movieList.add(movie);

        movie = new Accounts("Raiders of the Lost Ark", "Action & Adventure", "1981");
        movieList.add(movie);

        movie = new Accounts("Goldfinger", "Action & Adventure", "1965");
        movieList.add(movie);

        movie = new Accounts("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        movieList.add(movie);

        //mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AccountsFragment.OnFragmentInteractionListener) {
            mListener = (AccountsFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}