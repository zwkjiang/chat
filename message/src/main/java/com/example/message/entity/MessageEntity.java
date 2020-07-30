package com.example.message.entity;

public class MessageEntity {

    /**
     * id : 1
     * fromuser : sample string 2
     * touser : sample string 3
     * msg : sample string 4
     * msgtime : sample string 5
     * msgtype : sample string 6
     * isvalid : 7
     */

    private int id;
    private String fromuser;
    private String touser;
    private String msg;
    private String msgtime;
    private String msgtype;
    private int isvalid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromuser() {
        return fromuser;
    }

    public void setFromuser(String fromuser) {
        this.fromuser = fromuser;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgtime() {
        return msgtime;
    }

    public void setMsgtime(String msgtime) {
        this.msgtime = msgtime;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public int getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(int isvalid) {
        this.isvalid = isvalid;
    }
}
