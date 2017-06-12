package finapp.publicstatic.com.fintechbankapp;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AccountsCardsTabActivity extends TabActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accounts_cards_tab);

        Bundle bundle = getIntent().getExtras();
        String userID = bundle.getString("userId");
        String bankID = bundle.getString("userId");

        TabHost tabHost = getTabHost();
        Intent intentAccounts = new Intent().setClass(this, AccountsActivity.class);
        intentAccounts.putExtra("bankId", bankID);
        intentAccounts.putExtra("userId", userID);
        TabSpec tabSpecAccounts = tabHost
                .newTabSpec("Accounts")
                .setContent(intentAccounts)
                .setIndicator("Accounts");

        Intent intentCards = new Intent().setClass(this, CardsActivity.class);
        intentCards.putExtra("bankId", bankID);
        intentCards.putExtra("userId", userID);
        TabSpec tabSpecCards = tabHost
                .newTabSpec("Cards")
                .setContent(intentCards)
                .setIndicator("Cards");

        // Add all tabs
        tabHost.addTab(tabSpecAccounts);
        tabHost.addTab(tabSpecCards);

        // Set Windows tab as default (zero based)
        tabHost.setCurrentTab(2);
    }
}