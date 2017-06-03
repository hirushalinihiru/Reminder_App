package lk.sliit.mad.reminder_app;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.NotificationCompat;
import android.widget.EditText;
import android.view.View;

/**
 * Created by mtit on 5/24/2017.
 */

public class AlarmNotificationReceiver extends BroadcastReceiver{




    @Override
    public void onReceive(Context context, Intent intent) {

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_main);

        EditText task = (EditText) dialog.findViewById(R.id.task);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Reminder!!!!!!!")
                .setContentText(task.getText().toString())
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND)
                .setContentInfo("Info");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());
    }
}
