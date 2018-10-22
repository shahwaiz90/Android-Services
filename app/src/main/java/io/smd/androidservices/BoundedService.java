package io.smd.androidservices;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

public class BoundedService extends Service{
    String TAG = "BoundedService";
    private int randomNumber;
    private boolean generateNumberOn;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Current Thread: "+Thread.currentThread().getId());
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                generateNumberOn  =true;
                startRandomNumber();
            }
        }).start();
        return Service.START_STICKY;
    }

    private final IBinder mBinder = new MyBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    public boolean onUnbind(Intent intent) {

        Log.i(TAG, "unbinded");
        return super.onUnbind(intent);

    }
    private int startRandomNumber(){
        while(generateNumberOn) {
            try{
                Thread.sleep(1000);
                if(generateNumberOn){
                    randomNumber = new Random().nextInt(100);
                    Log.i(TAG, "Current Thread: "+Thread.currentThread().getId());
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return randomNumber;
    }

    private void stopRandomNumber(){
        generateNumberOn = false;
    }

    public int getRandomNumber(){
        return randomNumber;
    }

    public class MyBinder extends Binder {
        BoundedService getService() {
            return BoundedService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRandomNumber();
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
    }
}
