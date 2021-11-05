package com.updownteam.businessww;

public class Comment_model {
    String name;
    String comment;
    String profil;
    public Comment_model() {

    }
    public Comment_model(String name,String comment,String profil) {
        this.name = name;
        this.comment = comment;
        this.profil = profil;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
