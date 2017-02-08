package com.elh2ny.services;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elh2ny.model.NetworkEvent;
import com.elh2ny.utility.NetworkUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by mostafa_anter on 11/4/16.
 */

public class ReconnectNetWork extends IntentService {
    // default constructor
    public ReconnectNetWork() {
        super("BaitkBiedakService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("service", "service started");
        EventBus.getDefault().post(new NetworkEvent("message"));
    }


    static public class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String status = NetworkUtil.getConnectivityStatusString(context);

            // this broadcast will receive signal when mobile connect with internet or call it manually from activity
            if (status.equalsIgnoreCase("Wifi enabled") ||
                    status.equalsIgnoreCase("Mobile data enabled")) {
                Intent sendIntent = new Intent(context, ReconnectNetWork.class);
                context.startService(sendIntent);
            }

        }
    }
}
