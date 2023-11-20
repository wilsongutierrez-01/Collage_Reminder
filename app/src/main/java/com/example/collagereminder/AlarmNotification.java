package com.example.collagereminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AlarmNotification extends BroadcastReceiver {
    public static final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        createSimpleNotification(context);
    }

    private void createSimpleNotification(Context context) {
        Intent intent = new Intent(context, Calendario.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        int flag = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) ? PendingIntent.FLAG_IMMUTABLE : 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, flag);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, AgregarTareaActivity.MY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Revisa tus tareas")
                .setContentText("Es momento de revisar tu tareas pendientes")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(""))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(AgregarTareaActivity.MY_CHANNEL_ID, "MySuperChannel", NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("SUSCRIBETE");
                manager.createNotificationChannel(channel);
            }

            manager.notify(NOTIFICATION_ID, notificationBuilder.build());
        }
    }
}