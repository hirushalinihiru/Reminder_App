package lk.sliit.mad.reminder_app;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText date,task,time;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Calendar currentCal;
    Calendar selectCal=Calendar.getInstance();
    Date d = new Date();

    Button btnOneTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initiate the date picker and a button
        date = (EditText) findViewById(R.id.date);
        time = (EditText) findViewById(R.id.time);
        task = (EditText) findViewById(R.id.task);

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


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCal=Calendar.getInstance();
                int hour = currentCal.get(Calendar.HOUR); // current hour
                int minute = currentCal.get(Calendar.MINUTE); // current minute

                timePickerDialog = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        time.setText(""+ new DecimalFormat("00").format(selectedHour) +" : "+ new DecimalFormat("00").format(selectedMinute));
                        selectCal.set(Calendar.HOUR_OF_DAY,selectedHour);
                        selectCal.set(Calendar.MINUTE,selectedMinute);
                        selectCal.set(Calendar.SECOND,0);
                    }
                }, hour,minute,true);

                timePickerDialog.show();



            }
        });


        btnOneTime = (Button) findViewById(R.id.btnOneTime);


        btnOneTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCal=Calendar.getInstance();
                startAlarm();
            }
        });




    }

    private void startAlarm() {

        AlarmNotificationReceiver.task=task.getText().toString();
        long time =selectCal.getTimeInMillis()-currentCal.getTimeInMillis();
        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this,AlarmNotificationReceiver.class);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,myIntent,PendingIntent.FLAG_ONE_SHOT);
            manager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+time,pendingIntent);



    }


}
