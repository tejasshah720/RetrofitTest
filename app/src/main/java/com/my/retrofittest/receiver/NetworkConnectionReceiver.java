package com.my.retrofittest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.my.retrofittest.application.AppController;

/**
 * Created by Tejas Shah on 29/10/18.
 *
 * Receiver class to receive network state change events.
 */

public class NetworkConnectionReceiver extends BroadcastReceiver {

    public static boolean previousConnectionStatus;

    public static ConnectionReceiverListener connectionReceiverListener;

    public NetworkConnectionReceiver(ConnectionReceiverListener listener) {
        connectionReceiverListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent arg1) {
        /*ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();*/

        if (connectionReceiverListener != null) {
            connectionReceiverListener.onNetworkConnectionChanged(isConnected(context));
        }
    }

    /**
     * Check connection status with CONNECTIVITY_SERVICE.
     * return true if connected, false otherwise.
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager
                cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }


    public interface ConnectionReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }

}
