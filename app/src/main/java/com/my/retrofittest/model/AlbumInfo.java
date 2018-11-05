package com.my.retrofittest.model;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Tejas Shah on 29/10/18.
 */
public class AlbumInfo {

    @SerializedName("userId")
    private int UserId;
    @SerializedName("id")
    private int Id;
    @SerializedName("title")
    private String Title;

    public AlbumInfo(int userId, int id, String title) {
        UserId = userId;
        Id = id;
        Title = title;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
