package com.baweigame.xmpplibrary.contract;

import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IXmppMsg {
    /**
     * 创建聊天窗口
     *
     * @param JID JID
     * @return Chat
     */
    <T> T getFriendChat(String JID);

    /**
     * 发送单人聊天消息
     *
     * @param chat    chat
     * @param message 消息文本
     */
    void sendSingleMessage(Chat chat, String message);

    /**
     * 发送单人聊天消息
     * @param friendjid
     * @param message
     */
    void sendSingleMessage(String friendjid,String message);

    /**
     * 发消息
     *
     * @param chat    chat
     * @param muc     muc
     * @param message message
     */
    void sendMessage(Chat chat, MultiUserChat muc, String message);

    /**
     * 发送群组聊天消息
     *
     * @param muc     muc
     * @param message 消息文本
     */
    void sendGroupMessage(MultiUserChat muc, String message);

    /**
     * 发送文件
     *
     * @param user
     * @param filePath
     */
    void sendFile(String user, String filePath);

    /**
     * 获取离线消息
     *
     * @return
     */
    Map<String, List<HashMap<String, String>>> getHisMessage();
}
