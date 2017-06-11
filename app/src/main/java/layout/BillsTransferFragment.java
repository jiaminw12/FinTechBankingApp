package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import Adapter.BillTransferSectionPageAdapter;
import finapp.publicstatic.com.fintechbankapp.R;

public class BillsTransferFragment extends Fragment {

    BillTransferSectionPageAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    private static final String ARG_PARAM = "param";
    private static String mUserId;

    public BillsTransferFragment() {
        // Required empty public constructor
    }

    public static BillsTransferFragment newInstance() {
        BillsTransferFragment fragment = new BillsTransferFragment();
        return fragment;
    }

    public static BillsTransferFragment newInstance(String param) {
        BillsTransferFragment fragment = new BillsTransferFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        mUserId = param;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_billtransfer, container, false);

        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setAdapter(new BillTransferSectionPageAdapter(
                        getChildFragmentManager(), mUserId));

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return v;
    }
}
