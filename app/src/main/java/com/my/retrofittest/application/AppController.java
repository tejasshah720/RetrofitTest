package com.my.retrofittest.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import com.my.retrofittest.MainActivity;
import com.my.retrofittest.receiver.NetworkConnectionReceiver;

/**
 * Created by Tejas Shah on 29/10/18.
 */
public class AppController extends Application implements Application.ActivityLifecycleCallbacks{

    public static final String TAG = AppController.class.getSimpleName();

    public static AppController mAppInstance;
    private static boolean isMainActivityVisible;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppInstance = this;

        // Register to be notified of activity state changes
        registerActivityLifecycleCallbacks(this);

        //initApp();
    }

    public static boolean isMainActivityVisible() {
        return isMainActivityVisible;
    }

    private void initApp() {
        //initialise app
    }

    public static synchronized AppController getInstance(){
        Log.d(TAG,"getInstance call..");
        /*if (mAppInstance == null) {
            mAppInstance = new AppController();
        }*/
        return mAppInstance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity instanceof MainActivity) {
            isMainActivityVisible = true;
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity instanceof MainActivity) {
            isMainActivityVisible = false;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public void setConnectionListener(NetworkConnectionReceiver.ConnectionReceiverListener listener) {
        NetworkConnectionReceiver.connectionReceiverListener = listener;
    }
}
