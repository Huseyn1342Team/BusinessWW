package com.updownteam.businessww;

public class Activity_1_model {
    String post;
    String date;
    String image;
    String name;
    String surname;
    String profilimage;
    String uid;
    public Activity_1_model() {

    }
    public Activity_1_model(String post,String date,String image,String name,String surname,String profilimage,String uid) {
        this.post = post;
        this.date = date;
        this.image = image;
        this.name = name;
        this.surname =surname;
        this.profilimage = profilimage;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfilimage() {
        return profilimage;
    }

    public void setProfilimage(String profilimage) {
        this.profilimage = profilimage;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
