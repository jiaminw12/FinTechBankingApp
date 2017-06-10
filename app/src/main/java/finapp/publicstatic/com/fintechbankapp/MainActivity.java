package finapp.publicstatic.com.fintechbankapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.MenuItem;

import layout.AccountsFragment;
import layout.BillsFragment;
import layout.TransactionFragment;
import layout.PlannerFragment;
import layout.WalletFragment;

public class MainActivity extends AppCompatActivity implements AccountsFragment.OnFragmentInteractionListener, BillsFragment.OnFragmentInteractionListener, TransactionFragment.OnFragmentInteractionListener, PlannerFragment.OnFragmentInteractionListener, WalletFragment.OnFragmentInteractionListener
{

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("userId");

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
                                        .newInstance(userId);
                                break;
                            case R.id.action_bill:
                                selectedFragment = BillsFragment.newInstance(userId);
                                break;
                            case R.id.action_account:
                                selectedFragment = AccountsFragment
                                        .newInstance(userId);
                                break;
                            case R.id.action_wallet:
                                selectedFragment = WalletFragment
                                        .newInstance(userId);
                                break;
                            case R.id.action_budget_planner:
                                selectedFragment = PlannerFragment
                                        .newInstance(userId);
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
        transaction.replace(R.id.frame_layout, AccountsFragment.newInstance
                (userId));
        transaction.commit();

        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }
}
