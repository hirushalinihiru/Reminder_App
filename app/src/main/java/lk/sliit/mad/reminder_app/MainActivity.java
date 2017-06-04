package lk.sliit.mad.reminder_app;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText date,task,task1,task2;
    DatePickerDialog datePickerDialog;
    Calendar currentCal;
    Calendar selectCal=Calendar.getInstance();
    Date d = new Date();

    Button btnOneTime,btnRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initiate the date picker and a button
        date = (EditText) findViewById(R.id.date);
        task = (EditText) findViewById(R.id.task);
        task1 = (EditText) findViewById(R.id.task1);
        task2 = (EditText) findViewById(R.id.task2);
        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                currentCal=Calendar.getInstance();
                int mYear = currentCal.get(Calendar.YEAR); // current year
                int mMonth = currentCal.get(Calendar.MONTH); // current month
                int mDay = currentCal.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                selectCal.set(Calendar.YEAR, year);
                                selectCal.set(Calendar.MONTH, monthOfYear);
                                selectCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                selectCal.set(Calendar.HOUR_OF_DAY,0);
                                selectCal.set(Calendar.MINUTE,0);
                                selectCal.set(Calendar.SECOND,0);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



        btnOneTime = (Button) findViewById(R.id.btnOneTime);
        btnRepeat = (Button) findViewById(R.id.btnRepeating);

        btnOneTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCal=Calendar.getInstance();
                startAlarm(false);
            }
        });

        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlarm(true);
            }
        });


    }

    private void startAlarm(boolean isRepeat) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this,AlarmNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,myIntent,0);

        if(isRepeat)
        {
            //manager.setRepeating(AlarmManager.RTC_WAKEUP,SystemClock.elapsedRealtime()+3000,3000,pendingIntent);
        }
        else
        {
            task.setText(""+(currentCal.getTimeInMillis()));
            task1.setText(""+(selectCal.getTimeInMillis()));
            task2.setText(""+(selectCal.getTimeInMillis()-currentCal.getTimeInMillis()));

            manager.set(AlarmManager.RTC_WAKEUP,selectCal.getTimeInMillis()-currentCal.getTimeInMillis(),pendingIntent);

        }


    }


}
