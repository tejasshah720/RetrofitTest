package com.my.retrofittest.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.my.retrofittest.R;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Tejas Shah on 29/10/18.
 */

public class Util {

    public static int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

   /* public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return true;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }
        return false;
    }*/

    public static void showPopup(Context context, String title, final String msg) {
        if (!((Activity) context).isFinishing()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    context);
            if (!title.isEmpty()) {
                alertDialog.setTitle(title);
            }
            alertDialog.setMessage(msg);

            alertDialog.setPositiveButton(context.getResources().getString(R.string.lbl_btn_cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });

            alertDialog.show();
        }

    }
}
