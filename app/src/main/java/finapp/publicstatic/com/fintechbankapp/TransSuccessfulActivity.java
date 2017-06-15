package finapp.publicstatic.com.fintechbankapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
}
