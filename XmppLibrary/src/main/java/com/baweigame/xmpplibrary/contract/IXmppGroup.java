package com.baweigame.xmpplibrary.contract;

import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;

import java.util.List;

public interface IXmppGroup {
    /**
     * 获取所有组
     *
     * @return 所有组集合
     */
    <T> List<T> getGroups();

    /**
     * 添加一个分组
     *
     * @param groupName groupName
     * @return boolean
     */
    boolean addGroup(String groupName);

    /**
     * 获取某个组里面的所有好友
     *
     * @param groupName 组名
     * @return List<RosterEntry>
     */
    <T> List<T> getEntriesByGroup(String groupName);
}
