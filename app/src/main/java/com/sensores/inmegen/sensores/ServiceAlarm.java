package com.sensores.inmegen.sensores;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Ruben on 22/03/2016.
 */
public class ServiceAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, Servicio.class));
    }
}
