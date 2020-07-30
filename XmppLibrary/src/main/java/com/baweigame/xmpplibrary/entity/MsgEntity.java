package com.baweigame.xmpplibrary.entity;

/**
 * 消息实体
 */
public class MsgEntity {

    private String from;
    private String to;
    private String msg;
    private MsgType msgType=MsgType.Txt;
    public MsgEntity(){}

    public MsgEntity(String from, String to, String msg, MsgType msgType) {
        this.from = from;
        this.to = to;
        this.msg = msg;
        this.msgType = msgType;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MsgEntity{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    /**
     * 消息类型
     */
    public enum MsgType{
        /**
         * 文本信息
         */
        Txt,
        /**
         * 图片信息
         */
        Img,
        /**
         * 音频信息
         */
        Audio,
        /**
         * 视频信息
         */
        Video,
        /**
         * 位置信息
         */
        Location
    }
}
