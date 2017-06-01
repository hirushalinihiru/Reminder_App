package lk.sliit.mad.reminder_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnOneTime,btnRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOneTime = (Button) findViewById(R.id.btnOneTime);
        btnRepeat = (Button) findViewById(R.id.btnRepeating);

        btnOneTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        Intent myIntent = new Intent(MainActivity.this,AlarmNotificationReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,myIntent,0);

        if(!isRepeat)
        {
            manager.set(AlarmManager.RTC_WAKEUP,SystemClock.elapsedRealtime()+3000,pendingIntent);
        }
        else
        {
            manager.setRepeating(AlarmManager.RTC_WAKEUP,SystemClock.elapsedRealtime()+3000,3000,pendingIntent);
        }


    }


}
