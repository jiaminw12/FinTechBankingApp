package Adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import layout.BillTransferHistoryFragment;
import layout.BillsFragment;
import layout.TransferFragment;

public class BillTransferSectionPageAdapter extends FragmentStatePagerAdapter {

    private String tabTitles[] = new String[] { "Biils", "Transfer", "History"};
    private String mUserId;

    public BillTransferSectionPageAdapter(FragmentManager fm, String userId) {
        super(fm);
        mUserId = userId;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position){
            case 0 :
                fragment = BillsFragment.newInstance(mUserId);
                break;
            case 1 :
                fragment = TransferFragment.newInstance(mUserId);
                break;
            case 2 :
                fragment = BillTransferHistoryFragment.newInstance(mUserId);
                break;
        }
        return fragment;

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
