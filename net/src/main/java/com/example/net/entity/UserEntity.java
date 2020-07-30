package com.example.net.entity;

import java.io.Serializable;

public class UserEntity implements Serializable {

    /**
     * id : 19
     * usercode : f7c040da0f9b4f21bd3ba9045b2b36de
     * username : 232323
     * pwd : null
     * sex : 1
     * birthday :
     * headerimg :
     * nick :
     * utype : 0
     * imuseraccount :
     * signdesc :
     * openlocation : 0
     * openmsgalert : 3
     */

    private int id;
    private String usercode;
    private String username;
    private Object pwd;
    private String sex;
    private String birthday;
    private String headerimg;
    private String nick;
    private int utype;
    private String imuseraccount;
    private String signdesc;
    private int openlocation;
    private int openmsgalert;

    public UserEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getPwd() {
        return pwd;
    }

    public void setPwd(Object pwd) {
        this.pwd = pwd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeaderimg() {
        return headerimg;
    }

    public void setHeaderimg(String headerimg) {
        this.headerimg = headerimg;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getUtype() {
        return utype;
    }

    public void setUtype(int utype) {
        this.utype = utype;
    }

    public String getImuseraccount() {
        return imuseraccount;
    }

    public void setImuseraccount(String imuseraccount) {
        this.imuseraccount = imuseraccount;
    }

    public String getSigndesc() {
        return signdesc;
    }

    public void setSigndesc(String signdesc) {
        this.signdesc = signdesc;
    }

    public int getOpenlocation() {
        return openlocation;
    }

    public void setOpenlocation(int openlocation) {
        this.openlocation = openlocation;
    }

    public int getOpenmsgalert() {
        return openmsgalert;
    }

    public void setOpenmsgalert(int openmsgalert) {
        this.openmsgalert = openmsgalert;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", usercode='" + usercode + '\'' +
                ", username='" + username + '\'' +
                ", pwd=" + pwd +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", headerimg='" + headerimg + '\'' +
                ", nick='" + nick + '\'' +
                ", utype=" + utype +
                ", imuseraccount='" + imuseraccount + '\'' +
                ", signdesc='" + signdesc + '\'' +
                ", openlocation=" + openlocation +
                ", openmsgalert=" + openmsgalert +
                '}';
    }
}
