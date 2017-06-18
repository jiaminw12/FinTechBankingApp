package finapp.publicstatic.com.fintechbankapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TransSuccessfulActivity extends AppCompatActivity {

    Button btnOk;

    TextView tvAmt;
    TextView tvPayeeName;
    TextView tvNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_transac);

        Bundle bundle = getIntent().getExtras();
        String amt = bundle.getString("amount");
        String payeeName = bundle.getString("payeeName");
        String comments = bundle.getString("comments");

        tvAmt = (TextView) findViewById(R.id.tv_amount);
        tvPayeeName = (TextView) findViewById(R.id.tv_payee_name);
        tvNote = (TextView) findViewById(R.id.tv_note);

        tvAmt.setText("$ " + amt);
        tvPayeeName.setText(payeeName);
        tvNote.setText(comments);

        btnOk = (Button) findViewById(R.id.button_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(TransSuccessfulActivity.RESULT_OK);
                finish();
            }
        });
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
}
