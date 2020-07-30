package com.baweigame.xmpplibrary.contract;

import java.util.HashMap;
import java.util.List;

public interface IXmppFriend {
    /**
     * 添加好友 无分组
     *
     * @param userName userName
     * @param name     name
     * @return boolean
     */
    boolean addFriend(String userName, String name);

    /**
     * 添加好友 有分组
     *
     * @param userName  userName
     * @param name      name
     * @param groupName groupName
     * @return boolean
     */
    boolean addFriend(String userName, String name, String groupName);

    /**
     * 删除好友
     *
     * @param userName userName
     * @return boolean
     */
    boolean removeFriend(String userName);

    /**
     * 查询用户
     *
     * @param userName userName
     * @return List<HashMap<String, String>>
     */
    List<HashMap<String, String>> searchFriends(String userName);

}
