package com.example.message.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MsgEntity implements MultiItemEntity {
    private String formName;
    private String time;
    private String message;
    private String toName;
    private int type;
    private int megType;

    public MsgEntity(String formName, String time, String message, String toName, int type, int megType) {
        this.formName = formName;
        this.time = time;
        this.message = message;
        this.toName = toName;
        this.type = type;
        this.megType = megType;
    }

    public MsgEntity() {
    }

    public int getMegType() {
        return megType;
    }

    public void setMegType(int megType) {
        this.megType = megType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    @Override
    public String toString() {
        return "MsgEntity{" +
                "formName='" + formName + '\'' +
                ", time='" + time + '\'' +
                ", message='" + message + '\'' +
                ", toName='" + toName + '\'' +
                '}';
    }

    @Override
    public int getItemType() {
        return type%2;
    }
}


