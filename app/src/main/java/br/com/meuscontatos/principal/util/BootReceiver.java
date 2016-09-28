package br.com.meuscontatos.principal.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent_) {
        if (intent_.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            Toast toast = Toast.makeText(context,"Contatos IESB está sendo ligado",Toast.LENGTH_LONG);
            toast.show();
            }
         }
      }
