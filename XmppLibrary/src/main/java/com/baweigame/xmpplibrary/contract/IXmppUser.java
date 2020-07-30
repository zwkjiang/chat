package com.baweigame.xmpplibrary.contract;

import android.graphics.drawable.Drawable;

import org.jivesoftware.smack.packet.Presence;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface IXmppUser {
    /**
     * 是否登陆
     * @return
     */
    boolean isLogin();

    /**
     * 登录
     * @param userName 用户名
     * @param passWord 密码
     * @return
     */
    boolean login(String userName, String passWord);

    /**
     * 注销登录
     * @return
     */
    boolean logout();

    /**
     * 创建用户
     * @param userName 用户名
     * @param passWord 密码
     */
    void createAccount(String userName, String passWord);

    /**
     * 获取当前用户
     * @return
     */
    <T> T getCurrentUser();

    /**
     * 获取用户头像信息
     *
     * @param user user
     * @return Drawable
     */
    Drawable getUserImage(String user);

    /**
     * 获取好友列表
     */
    <T> List<T> getFriendList();

    /**
     * 修改用户头像
     *
     * @param file file
     */
    boolean changeImage(File file);

    /**
     * 删除当前用户
     *
     * @return true成功
     */
    boolean deleteAccount();

    /**
     * 修改密码
     *
     * @return true成功
     */
    boolean changePassword(String pwd);

    /**
     * 判断OpenFire用户的状态 strUrl :
     * url格式 - http://my.openfire.com:9090/plugins/presence
     * /status?jid=user1@SERVER_NAME&type=xml
     * 返回值 : 0 - 用户不存在; 1 - 用户在线; 2 - 用户离线
     * 说明 ：必须要求 OpenFire加载 presence 插件，同时设置任何人都可以访问
     */
    int IsUserOnLine(String user);

    /**
     * 设置在线、离线等状态
     * @param type
     */
    void setOnLine(Presence.Type type);

    /**
     * 更改用户状态
     */
    void setPresence(int code);


    /**
     * 获取用户VCard信息
     *
     * @param user user
     * @return VCard
     */
    <T> T getUserVCard(String user);
}
