package com.my.retrofittest.rest;

import com.my.retrofittest.model.AlbumInfo;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

/**
 * Created by Tejas Shah on 29/10/18.
 */
public interface ApiInterface {

    //Implement all api call methods here.

    /*
    * To get albums information.
    * */
    @GET("/albums")
    Call<List<AlbumInfo>> getAllAlbumsTitle();
}
