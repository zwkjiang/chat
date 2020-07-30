package com.baweigame.xmpplibrary.contract;

import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.util.List;

public interface IXmppChatRoom {
    /**
     * 初始化会议室列表
     */
    <T> List<T> getHostRooms();

    /**
     * 创建房间
     *
     * @param roomName 房间名称
     */
    <T> T createRoom(String roomName, String password);

    /**
     * 加入会议室
     *
     * @param user      昵称
     * @param roomsName 会议室名
     */
    <T> T joinMultiUserChat(String user, String roomsName);

    /**
     * 查询会议室成员名字
     *
     * @param muc
     */
    List<String> findMulitUser(MultiUserChat muc);
}
