package layout;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import finapp.publicstatic.com.fintechbankapp.R;

public class AccountsFragment extends Fragment {
    private List<AccountsFragmentGetterSetter> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AccountsFragmentAdapter mAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
       // setContentView(R.layout.fragment_accounts);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_accounts, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        mAdapter = new AccountsFragmentAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareMovieData();

        return v;
    }

    private void prepareMovieData() {
        AccountsFragmentGetterSetter movie = new AccountsFragmentGetterSetter("Mad Max: Fury Road", "Action & Adventure", "2015");
        movieList.add(movie);

        movie = new AccountsFragmentGetterSetter("Inside Out", "Animation, Kids & Family", "2015");
        movieList.add(movie);

        movie = new AccountsFragmentGetterSetter("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        movieList.add(movie);

        movie = new AccountsFragmentGetterSetter("Shaun the Sheep", "Animation", "2015");
        movieList.add(movie);

        movie = new AccountsFragmentGetterSetter("The Martian", "Science Fiction & Fantasy", "2015");
        movieList.add(movie);

        movie = new AccountsFragmentGetterSetter("Mission: Impossible Rogue Nation", "Action", "2015");
        movieList.add(movie);

        movie = new AccountsFragmentGetterSetter("Up", "Animation", "2009");
        movieList.add(movie);

        movie = new AccountsFragmentGetterSetter("Star Trek", "Science Fiction", "2009");
        movieList.add(movie);

        movie = new AccountsFragmentGetterSetter("The LEGO Movie", "Animation", "2014");
        movieList.add(movie);

        movie = new AccountsFragmentGetterSetter("Iron Man", "Action & Adventure", "2008");
        movieList.add(movie);

        movie = new AccountsFragmentGetterSetter("Aliens", "Science Fiction", "1986");
        movieList.add(movie);

        movie = new AccountsFragmentGetterSetter("Chicken Run", "Animation", "2000");
        movieList.add(movie);

        movie = new AccountsFragmentGetterSetter("Back to the Future", "Science Fiction", "1985");
        movieList.add(movie);

        movie = new AccountsFragmentGetterSetter("Raiders of the Lost Ark", "Action & Adventure", "1981");
        movieList.add(movie);

        movie = new AccountsFragmentGetterSetter("Goldfinger", "Action & Adventure", "1965");
        movieList.add(movie);

        movie = new AccountsFragmentGetterSetter("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        movieList.add(movie);

        mAdapter.notifyDataSetChanged();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}