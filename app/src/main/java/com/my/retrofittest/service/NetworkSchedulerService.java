package com.my.retrofittest.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;
import com.my.retrofittest.application.AppController;
import com.my.retrofittest.receiver.NetworkConnectionReceiver;
import com.my.retrofittest.util.Constants;

import static com.my.retrofittest.receiver.NetworkConnectionReceiver.connectionReceiverListener;

/**
 * Created by Tejas Shah on 03/11/18.
 */
public class NetworkSchedulerService extends JobService implements NetworkConnectionReceiver.ConnectionReceiverListener {

    private static final String TAG = NetworkSchedulerService.class.getSimpleName();
    private NetworkConnectionReceiver mNetworkConnectionReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service created");
        mNetworkConnectionReceiver = new NetworkConnectionReceiver(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.i(TAG, "onStartJob" + mNetworkConnectionReceiver);
        registerReceiver(mNetworkConnectionReceiver, new IntentFilter(Constants.CONNECTIVITY_ACTION));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i(TAG, "onStopJob");
        unregisterReceiver(mNetworkConnectionReceiver);
        return true;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!AppController.isMainActivityVisible()) {
            //String message = isConnected ? "Good! Connected to Internet" : "Sorry! Not connected to internet";
            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
