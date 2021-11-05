package com.updownteam.businessww;


public class AskAndAnswer_3_model {
    String time;
    String comment;
    String uid;
    public AskAndAnswer_3_model() {

    }
    public AskAndAnswer_3_model(String time,String comment,String uid) {
        this.time = time;
        this.comment = comment;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
