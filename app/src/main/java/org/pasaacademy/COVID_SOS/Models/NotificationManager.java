package org.pasaacademy.COVID_SOS.Models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.pasaacademy.COVID_SOS.R;

/**
 * Handles the notification for the reminder to update the data
 */
public class NotificationManager extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notifyVolunteer")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Update Data")
                .setContentText("Its been 2 hours since you last updated the data.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200,builder.build());
    }
}
