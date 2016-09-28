package br.com.meuscontatos.principal.util;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;


public class AlarmManager extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public static void setAlarme(Context context,int id, int hora, int minuto,int intervalo){
        Calendar calendar = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE,      minuto);

        long milisegundos_alarme = 0;

        if(calendar.getTimeInMillis() <= now.getTimeInMillis())
            milisegundos_alarme = calendar.getTimeInMillis() + (android.app.AlarmManager.INTERVAL_DAY+1);
        else
            milisegundos_alarme = calendar.getTimeInMillis();

        android.app.AlarmManager alarmeMenager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,AlarmManager.class);
        PendingIntent pending = PendingIntent.getBroadcast(context,id,intent,0);
        alarmeMenager.setRepeating(android.app.AlarmManager.RTC_WAKEUP, milisegundos_alarme, 1000 * 60 * 60 * intervalo,pending);
    }

    public static void cancelAlarme(Context context, int id){
        Intent intent = new Intent(context,AlarmManager.class);
        PendingIntent pending = PendingIntent.getBroadcast(context,id,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        android.app.AlarmManager alarmeMenager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmeMenager.cancel(pending);
        pending.cancel();
    }
}
