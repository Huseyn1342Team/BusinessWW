package com.updownteam.businessww;

public class SocialMediaSearch_model {
    String name;
    String surname;
    String gender;
    String profil;
    String uid;
    public SocialMediaSearch_model() {
    }
    public SocialMediaSearch_model(String name,String surname,String gender,String profil,String uid){
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.profil = profil;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
}
