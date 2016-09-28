package br.com.meuscontatos.principal.util;


import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

public class AlarmeReceiver extends WakefulBroadcastReceiver {

    private static PowerManager.WakeLock wakeLock;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent==null){
           return;
        }

        Toast toast = Toast.makeText(context, "ALARME LIGADO!", Toast.LENGTH_LONG);
        toast.show();
    }
}
