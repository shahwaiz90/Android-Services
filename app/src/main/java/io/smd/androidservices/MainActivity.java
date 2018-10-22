package io.smd.androidservices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

//Implementing ServiceConnect only for BoundedService!!!!
//If you are going to start a StartedService then no need to implement ServiceConnection
public class MainActivity extends AppCompatActivity implements ServiceConnection {

    BoundedService boundedService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startStartedService(View view) {
        Intent serviceIntent = new Intent(getApplicationContext(), StartedService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(getApplicationContext(), serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }

    public void stopStartedService(View view) {
        stopService(new Intent(getApplicationContext(), StartedService.class));
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        BoundedService.MyBinder b = (BoundedService.MyBinder) service;
        boundedService = b.getService();
        Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

        Toast.makeText(getApplicationContext(), "Disconnected", Toast.LENGTH_SHORT).show();
    }

    public void startBoundedService(View view) {

        Intent intent= new Intent(this, BoundedService.class);
        startService(intent);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    public void stopBoundedService(View view) {
        if(boundedService!=null) {
            unbindService(this);
            boundedService = null;
        }

    }

    public void getValueFromService(View view) {
        if(boundedService!=null) {
            Toast.makeText(MainActivity.this, String.valueOf(boundedService.getRandomNumber()), Toast.LENGTH_SHORT).show();
        }
    }

    public void startJobIntentService(View view) {
        Intent i = new Intent(getApplicationContext(), ExampleJobIntentService.class);
        ExampleJobIntentService.enqueueWork(getApplicationContext(),i);
    }
}
