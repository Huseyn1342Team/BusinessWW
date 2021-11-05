package com.updownteam.businessww;

public class FriendRequestModel {
    String uid;
    public FriendRequestModel() {

    }
    public FriendRequestModel(String uid) {
     this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}