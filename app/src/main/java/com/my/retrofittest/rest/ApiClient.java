package com.my.retrofittest.rest;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.my.retrofittest.BuildConfig;
import com.my.retrofittest.util.AppConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Tejas Shah on 29/10/18.
 */
public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClientInstance(Context context) {

        if (retrofit == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.readTimeout(30,TimeUnit.SECONDS);
            builder.connectTimeout(30,TimeUnit.SECONDS);

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
            }
            OkHttpClient okHttpClient = builder.build();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(AppConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
