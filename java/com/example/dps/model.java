package com.example.dps;

import java.util.ArrayList;

public class model {

    String eamil,father,mother,user,phone,purl;
    public model() {

    }

    public model(String eamil, String father, String mother, String user, String phone, String purl) {
        this.eamil = eamil;
        this.father = father;
        this.mother = mother;
        this.user = user;
        this.phone = phone;
        this.purl = purl;
    }

    public model(String eamil, String user) {
        this.eamil = eamil;
        this.user = user;
    }

    public String getEamil() {
        return eamil;
    }

    public void setEamil(String eamil) {
        this.eamil = eamil;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

}
