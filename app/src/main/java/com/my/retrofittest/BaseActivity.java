package com.my.retrofittest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.my.retrofittest.application.AppController;
import com.my.retrofittest.receiver.NetworkConnectionReceiver;

import static com.my.retrofittest.util.Util.convertDpToPx;

/**
 * Created by Tejas Shah on 29/10/18.
 *
 * Base class to abstract out common feature through out the application.
 */

public abstract class BaseActivity extends AppCompatActivity implements NetworkConnectionReceiver.ConnectionReceiverListener {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // register connection status listener

      AppController.getInstance().setConnectionListener(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        configureToolbar(view);
        super.setContentView(view);
    }

    private void configureToolbar(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        if(mToolbar == null){
//            mToolbar = view.findViewById(R.id.anim_toolbar);
        }
    }

    public void updateToolbar(boolean isConnected){
        if(mToolbar != null){
            ImageView offlineIcon = mToolbar.findViewWithTag("offlineIcon");
            if(isConnected){
                if(offlineIcon != null) {
                    offlineIcon.setVisibility(View.GONE);
                }
            }else{
                if(offlineIcon != null){
                    offlineIcon.setVisibility(View.VISIBLE);
                }else{
                    ImageView imageView = new ImageView(mToolbar.getContext());
                    imageView.setTag("offlineIcon");
                    imageView.setColorFilter(getResources().getColor(R.color.red));
                    imageView.setImageResource(R.drawable.ic_cloud_off);
                    LinearLayout.LayoutParams  layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.gravity= Gravity.CENTER;
                    layoutParams.setMargins(convertDpToPx(32, getResources().getDisplayMetrics()),0,0,0);
                    mToolbar.addView(imageView, layoutParams);
                }
            }
        }
    }

   /**
     * Handler for connection change event.
     * @param isConnected
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        NetworkConnectionReceiver.previousConnectionStatus = isConnected;
        Log.d("Bingo", ""+isConnected);
        if (isConnected) {
            //Toast.makeText(this, getResources().getString(R.string.online_message), Toast.LENGTH_LONG).show();
            updateToolbar(isConnected);
        } else {
            //Toast.makeText(this, getResources().getString(R.string.offline_message), Toast.LENGTH_LONG).show();
            updateToolbar(isConnected);
        }

    }

    /**
     * Check the network connection status and notify the user upon change.
     * Update the connection status with previousConnectionStatus.
     */
    public void checkConnection(Toolbar toolbar) {
        //Notify user only if there is a change in the connection status.
//        if (NetworkConnectionReceiver.previousConnectionStatus != isConnected) {
//            onNetworkConnectionChanged(isConnected);
//        }
        mToolbar = toolbar;
        updateToolbar(NetworkConnectionReceiver.isConnected(this));
    }
}
