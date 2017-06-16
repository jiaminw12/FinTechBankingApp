package finapp.publicstatic.com.fintechbankapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import java.util.Calendar;

public class BillScheduleActivity extends AppCompatActivity {

    /* String date_time = "";
    int mYear;
    int mMonth;
    int mDay;

    int mHour;
    int mMinute;

    EditText showDateTime = (EditText) findViewById(R.id.dateTimeDisplay);
    CheckBox setDateTime = (CheckBox) findViewById(R.id.billCheckBox);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_schedule);





        //Calendar popup. only know how to show. Don't know how to get into the database.
        setDateTime.OnClickListener(new View.OnClickListener()  {

            @Override
            public void onClick(View v) {
                datePicker();

            }
        }); private void datePicker(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        //*************Call Time Picker Here ********************
                        timePicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    private void timePicker() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;

                        showDateTime.setText(date_time + " " + hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }
    }

}
