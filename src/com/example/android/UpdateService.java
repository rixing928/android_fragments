package com.example.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;
import com.example.R;
import com.example.android.activities.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class UpdateService extends Service {

    public static final String ACTION_UPDATE = "com.example.android.UPDATE_LIST";

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private int NOTIFICATION = R.string.update_service_started;

    private final IBinder mBinder = new LocalBinder();
    private NotificationManager mNM;
    private Timer timer;

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
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer = new Timer();
        timer.schedule(new UpdateTask(), 0, 5000);

        showNotification();
        return START_STICKY;
    }

    private class UpdateTask extends TimerTask {

        @Override
        public void run() {
            Intent intent = new Intent(ACTION_UPDATE);
            sendBroadcast(intent);
        }
    }

    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.update_service_started);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(android.R.drawable.ic_dialog_info, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.update_service_label),
                text, contentIntent);

        // Send the notification.
        mNM.notify(NOTIFICATION, notification);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        timer.cancel();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        mNM.cancel(NOTIFICATION);

        // Tell the user we stopped.
        Toast.makeText(this, R.string.update_service_stopped, Toast.LENGTH_SHORT).show();

        super.onDestroy();
    }
}
