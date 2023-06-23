package com.easy.myapplication.Helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.os.Vibrator;

public class Alarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      /*  MediaPlayer mp = MediaPlayer.create(context, R.raw.ferry_sound);
        mp.start();*/
        /*
        PowerManager pm = (PowerManager)     context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "NOTE:awaklock");
        wl.acquire(10*60*1000L */
        /*10 minutes*//*);
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] s = { 0, 100, 10, 500, 10, 100, 0, 500, 10, 100, 10, 500 };
        vibrator.vibrate(s, -1);*/
        String id = intent.getExtras().getString("id","0");
        String title = intent.getExtras().getString("title","Reminder");
        String desc = intent.getExtras().getString("desc","You have a reminder");
        ShowNotification showNotification = new ShowNotification();
        showNotification.showNotification(context, id,desc, title, null);
    }
}