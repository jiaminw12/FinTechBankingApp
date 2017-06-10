package finapp.publicstatic.com.fintechbankapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import layout.AccountsFragment;
import layout.BillsFragment;
import layout.TransactionFragment;
import layout.PlannerFragment;
import layout.WalletFragment;

public class MainActivity extends AppCompatActivity implements AccountsFragment.OnFragmentInteractionListener, BillsFragment.OnFragmentInteractionListener, TransactionFragment.OnFragmentInteractionListener, PlannerFragment.OnFragmentInteractionListener, WalletFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_transac:
                                selectedFragment = TransactionFragment
                                        .newInstance();
                                break;
                            case R.id.action_bill:
                                selectedFragment = BillsFragment.newInstance();
                                break;
                            case R.id.action_account:
                                selectedFragment = AccountsFragment.newInstance();
                                break;
                            case R.id.action_wallet:
                                selectedFragment = WalletFragment
                                        .newInstance();
                                break;
                            case R.id.action_budget_planner:
                                selectedFragment = PlannerFragment
                                        .newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, AccountsFragment.newInstance());
        transaction.commit();

        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }
}
