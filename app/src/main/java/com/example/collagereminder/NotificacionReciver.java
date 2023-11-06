package com.example.collagereminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificacionReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Configura el contenido de la notificación
        String title = "Tarea Guardada";
        String message = "Tu tarea programada se ha completado.";

        // Aquí puedes configurar la notificación, como abrir una actividad específica al hacer clic en ella
        Intent notificationIntent = new Intent(context, Calendario.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        // Crea y muestra la notificación
        NotificationHelper.showNotification(context, title, message, contentIntent);
    }

    public void setNotification(Context context, long timestamp) {
        // Crea una alarma para mostrar la notificación en el momento específico
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
    }


}