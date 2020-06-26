package com.punith.chatapp.model;

public class UserDetailsDo {
    String id;
    String username;
    String imageURL;
    String mobileNo;

    public UserDetailsDo(String id, String username, String imageURL, String mobileNo) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.mobileNo = mobileNo;
    }

    public UserDetailsDo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
