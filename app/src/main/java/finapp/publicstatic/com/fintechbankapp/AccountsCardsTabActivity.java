package finapp.publicstatic.com.fintechbankapp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AccountsCardsTabActivity extends TabActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accounts_cards_tab);

        Bundle bundle = getIntent().getExtras();
        String userID = bundle.getString("userId");
        String bankID = bundle.getString("bankId");

        TabHost tabHost = getTabHost();
        Intent intentAccounts = new Intent().setClass(this, AccountsActivity.class);
        intentAccounts.putExtra("userId", userID);
        intentAccounts.putExtra("bankId", bankID);
        TabSpec tabSpecAccounts = tabHost
                .newTabSpec("Accounts")
                .setContent(intentAccounts)
                .setIndicator("Accounts");

        Intent intentCards = new Intent().setClass(this, CardsActivity.class);
        intentCards.putExtra("userId", userID);
        intentCards.putExtra("bankId", bankID);
        TabSpec tabSpecCards = tabHost
                .newTabSpec("Cards")
                .setContent(intentCards)
                .setIndicator("Cards");

        // Add all tabs
        tabHost.addTab(tabSpecAccounts);
        tabHost.addTab(tabSpecCards);

        // Set default tab
        tabHost.setCurrentTab(2);
    }
}