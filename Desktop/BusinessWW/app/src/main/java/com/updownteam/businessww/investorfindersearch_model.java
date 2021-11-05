package com.updownteam.businessww;


public class investorfindersearch_model {
    String name;
    String gender;
    String uid;
    public investorfindersearch_model() {

    }
    public investorfindersearch_model(String name,String gender,String uid) {
       this.name = name;
       this.gender = gender;
       this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}