package com.baweigame.xmpplibrary.impl;

import android.util.Log;

import com.baweigame.xmpplibrary.XmppManager;
import com.baweigame.xmpplibrary.contract.IXmppGroup;

import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultXmppGroupImpl implements IXmppGroup {
    /**
     * 获取所有组
     *
     * @return 所有组集合
     */
    @Override
    public List<RosterGroup> getGroups() {
        if (XmppManager.getInstance().getConnection() == null)
            return null;
        List<RosterGroup> groupList = new ArrayList<>();
        Collection<RosterGroup> rosterGroup = Roster.getInstanceFor(XmppManager.getInstance().getConnection()).getGroups();
        for (RosterGroup aRosterGroup : rosterGroup) {
            groupList.add(aRosterGroup);
        }
        return groupList;
    }

    /**
     * 添加一个分组
     *
     * @param groupName groupName
     * @return boolean
     */
    @Override
    public boolean addGroup(String groupName) {
        if (XmppManager.getInstance().getConnection() == null)
            return false;
        try {
            RosterGroup group = Roster.getInstanceFor(XmppManager.getInstance().getConnection()).createGroup(groupName);

            Log.v("addGroup", groupName + "创建成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取某个组里面的所有好友
     *
     * @param groupName 组名
     * @return List<RosterEntry>
     */
    @Override
    public List<RosterEntry> getEntriesByGroup(String groupName) {
        if (XmppManager.getInstance().getConnection() == null)
            return null;
        List<RosterEntry> EntriesList = new ArrayList<>();
        RosterGroup rosterGroup = Roster.getInstanceFor(XmppManager.getInstance().getConnection()).getGroup(groupName);
        Collection<RosterEntry> rosterEntry = rosterGroup.getEntries();
        for (RosterEntry aRosterEntry : rosterEntry) {
            EntriesList.add(aRosterEntry);
        }
        return EntriesList;
    }
}
