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

        TabHost tabHost = getTabHost();
        Intent intentAccounts = new Intent().setClass(this, AccountsActivity.class);
        TabSpec tabSpecAccounts = tabHost
                .newTabSpec("Accounts")
                .setContent(intentAccounts);

        Intent intentCards = new Intent().setClass(this, CardsActivity.class);
        TabSpec tabSpecCards = tabHost
                .newTabSpec("Cards")
                .setContent(intentCards);

        // Add all tabs
        tabHost.addTab(tabSpecAccounts);
        tabHost.addTab(tabSpecCards);

        // Set Windows tab as default (zero based)
        tabHost.setCurrentTab(2);
    }
}