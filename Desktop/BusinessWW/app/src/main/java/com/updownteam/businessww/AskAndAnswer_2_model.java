package com.updownteam.businessww;

public class AskAndAnswer_2_model {
    String question;
    String time;
    String category;
    String name;
    String uid;
    String profil;
    public AskAndAnswer_2_model() {

    }
    public AskAndAnswer_2_model(String question, String time, String category,String name,String uid,String profil) {
        this.question = question;
        this.time = time;
        this.category = category;
        this.name = name;
        this.uid = uid;
        this.profil = profil;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
