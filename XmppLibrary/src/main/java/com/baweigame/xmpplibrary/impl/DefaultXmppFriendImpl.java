package com.baweigame.xmpplibrary.impl;

import android.text.TextUtils;

import com.baweigame.xmpplibrary.XmppManager;
import com.baweigame.xmpplibrary.contract.IXmppFriend;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smackx.search.ReportedData;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.jivesoftware.smackx.xdata.Form;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefaultXmppFriendImpl implements IXmppFriend {
    /**
     * 添加好友 无分组
     *
     * @param userName userName
     * @param name     name
     * @return boolean
     */
    @Override
    public boolean addFriend(String userName, String name) {
        if (XmppManager.getInstance().getConnection() == null)
            return false;
        if (TextUtils.isEmpty(name)){
            return false;
        }

        try {

            Roster.getInstanceFor(XmppManager.getInstance().getConnection()).createEntry(JidCreate.entityBareFrom(userName), name, null);
            //Roster.getInstanceFor(XmppManager.getInstance().getConnection()).preApprove(JidCreate.entityBareFrom(userName));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加好友 有分组
     *
     * @param userName  userName
     * @param name      name
     * @param groupName groupName
     * @return boolean
     */
    @Override
    public boolean addFriend(String userName, String name, String groupName) {
        if (XmppManager.getInstance().getConnection() == null)
            return false;
        try {
            Presence subscription = new Presence(Presence.Type.subscribed);
            subscription.setTo(JidCreate.entityBareFrom(userName));
            userName += "@" + XmppManager.getInstance().getConnection().getServiceName();
            XmppManager.getInstance().getConnection().sendStanza(subscription);
            Roster.getInstanceFor(XmppManager.getInstance().getConnection()).createEntry(JidCreate.entityBareFrom(userName), name,
                    new String[]{groupName});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除好友
     *
     * @param userName userName
     * @return boolean
     */
    @Override
    public boolean removeFriend(String userName) {
        if (XmppManager.getInstance().getConnection() == null)
            return false;
        try {
            RosterEntry entry = null;
            if (userName.contains("@"))
                entry = Roster.getInstanceFor(XmppManager.getInstance().getConnection()).getEntry(JidCreate.entityBareFrom(userName));
            else
                entry = Roster.getInstanceFor(XmppManager.getInstance().getConnection()).getEntry(JidCreate.entityBareFrom(
                        userName + "@" + XmppManager.getInstance().getConnection().getServiceName()));
            if (entry == null)
                entry = Roster.getInstanceFor(XmppManager.getInstance().getConnection()).getEntry(JidCreate.entityBareFrom(userName));
            Roster.getInstanceFor(XmppManager.getInstance().getConnection()).removeEntry(entry);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询用户
     *
     * @param userName userName
     * @return List<HashMap<String, String>>
     */
    @Override
    public List<HashMap<String, String>> searchFriends(String userName) {
        if (XmppManager.getInstance().getConnection() == null)
            return null;
        HashMap<String, String> user;
        List<HashMap<String, String>> results = new ArrayList<>();
        try {
            UserSearchManager usm = new UserSearchManager(XmppManager.getInstance().getConnection());

            Form searchForm = usm.getSearchForm(XmppManager.getInstance().getConnection().getServiceName());
            if (searchForm == null)
                return null;

            Form answerForm = searchForm.createAnswerForm();
            answerForm.setAnswer("userAccount", true);
            answerForm.setAnswer("userPhote", userName);
            ReportedData data = usm.getSearchResults(answerForm, JidCreate.domainBareFrom("search" + XmppManager.getInstance().getConnection().getServiceName()));

            List<ReportedData.Row> rowList = data.getRows();
            for (ReportedData.Row row : rowList) {
                user = new HashMap<>();
                user.put("userAccount", row.getValues("userAccount").toString());
                user.put("userPhote", row.getValues("userPhote").toString());
                results.add(user);
                // 若存在，则有返回,UserName一定非空，其他两个若是有设，一定非空
            }
        } catch (SmackException | InterruptedException | XmppStringprepException | XMPPException e) {
            e.printStackTrace();
        }
        return results;
    }

}
