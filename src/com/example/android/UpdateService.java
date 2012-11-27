package com.example.android;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class UpdateService extends Service {

    public static final String ACTION_UPDATE = "com.example.android.UPDATE_LIST";

    private final IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public UpdateService getService() {
            return UpdateService.this;
        }
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        Timer timer = new Timer();
        timer.schedule(new UpdateTask(), 0, 5000);
        return START_STICKY;
    }

    private class UpdateTask extends TimerTask {

        @Override
        public void run() {
            Intent intent = new Intent(ACTION_UPDATE);
            sendBroadcast(intent);
        }
    }
}
