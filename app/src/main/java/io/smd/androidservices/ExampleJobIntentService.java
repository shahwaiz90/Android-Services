package io.smd.androidservices;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class ExampleJobIntentService extends JobIntentService {
    String TAG = "ExampleJobIntentService";
    static final int JOB_ID = 1000; //Unique job ID.

    //Convenience method for enqueuing work in to this service.

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, ExampleJobIntentService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        Log.i(TAG, "Current Thread: "+Thread.currentThread().getId());
        // This describes what will happen when service is triggered
    }
}
