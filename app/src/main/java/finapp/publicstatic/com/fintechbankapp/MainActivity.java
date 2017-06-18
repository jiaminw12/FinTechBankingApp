package finapp.publicstatic.com.fintechbankapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import layout.BankFragment;
import layout.BillsTransferFragment;
import layout.RewardsFragment;
import layout.PlannerFragment;
import layout.WalletFragment;

public class MainActivity extends AppCompatActivity implements BankFragment.OnFragmentInteractionListener, RewardsFragment.OnFragmentInteractionListener, PlannerFragment.OnFragmentInteractionListener, WalletFragment.OnFragmentInteractionListener {

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
                            case R.id.action_bill:
                                selectedFragment = BillsTransferFragment
                                        .newInstance(userId);
                                break;
                            case R.id.action_wallet:
                                selectedFragment = WalletFragment
                                        .newInstance(userId);
                                break;
                            case R.id.action_account:
                                selectedFragment = BankFragment
                                        .newInstance(userId);
                                break;
                            case R.id.action_rewards:
                                selectedFragment = RewardsFragment
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

        bottomNavigationView.setSelectedItemId(R.id.action_account);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }
}
