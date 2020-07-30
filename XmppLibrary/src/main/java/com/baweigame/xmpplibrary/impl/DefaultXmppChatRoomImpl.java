package com.baweigame.xmpplibrary.impl;

import android.util.Log;

import com.baweigame.xmpplibrary.XmppManager;
import com.baweigame.xmpplibrary.contract.IXmppChatRoom;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;
import org.jxmpp.jid.EntityFullJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultXmppChatRoomImpl implements IXmppChatRoom {
    /**
     * 初始化会议室列表
     */
    @Override
    public List<HostedRoom> getHostRooms() {
        if (XmppManager.getInstance().getConnection() == null)
            return null;
        Collection<HostedRoom> hostrooms;
        List<HostedRoom> roominfos = new ArrayList<>();
        try {
            hostrooms = MultiUserChatManager.getInstanceFor(XmppManager.getInstance().getConnection()).getHostedRooms(
                    JidCreate.domainBareFrom(XmppManager.getInstance().getConnection().getServiceName()));
            for (HostedRoom entry : hostrooms) {
                roominfos.add(entry);
                Log.i("room", "名字：" + entry.getName() + " - ID:" + entry.getJid());
            }
            Log.i("room", "服务会议数量:" + roominfos.size());
        } catch (XMPPException | XmppStringprepException | InterruptedException | SmackException e) {
            e.printStackTrace();
            return null;
        }
        return roominfos;
    }

    /**
     * 创建房间
     *
     * @param roomName 房间名称
     */
    @Override
    public MultiUserChat createRoom(String roomName, String password) {
        if (XmppManager.getInstance().getConnection() == null)
            return null;

        MultiUserChat muc = null;
        try {
            // 创建一个MultiUserChat
            muc = MultiUserChatManager.getInstanceFor(XmppManager.getInstance().getConnection()).getMultiUserChat(
                    JidCreate.entityBareFrom(roomName + "@conference." + XmppManager.getInstance().getConnection().getServiceName()));
            // 创建聊天室
            muc.create(Resourcepart.from(roomName));
            // 获得聊天室的配置表单
            Form form = muc.getConfigurationForm();
            // 根据原始表单创建一个要提交的新表单。
            Form submitForm = form.createAnswerForm();
            // 向要提交的表单添加默认答复
            for (FormField formField : form.getFields()) {
                if (FormField.Type.hidden == formField.getType()
                        && formField.getVariable() != null) {
                    // 设置默认值作为答复
                    submitForm.setDefaultAnswer(formField.getVariable());
                }
            }
            // 设置聊天室的新拥有者
            List<String> owners = new ArrayList<>();
            owners.add(XmppManager.getInstance().getConnection().getUser().asEntityBareJidString());// 用户JID
            submitForm.setAnswer("muc#roomconfig_roomowners", owners);
            // 设置聊天室是持久聊天室，即将要被保存下来
            submitForm.setAnswer("muc#roomconfig_persistentroom", true);
            // 房间仅对成员开放
            submitForm.setAnswer("muc#roomconfig_membersonly", false);
            // 允许占有者邀请其他人
            submitForm.setAnswer("muc#roomconfig_allowinvites", true);
            if (!password.equals("")) {
                // 进入是否需要密码
                submitForm.setAnswer("muc#roomconfig_passwordprotectedroom",
                        true);
                // 设置进入密码
                submitForm.setAnswer("muc#roomconfig_roomsecret", password);
            }
            // 能够发现占有者真实 JID 的角色
            // submitForm.setAnswer("muc#roomconfig_whois", "anyone");
            // 登录房间对话
            submitForm.setAnswer("muc#roomconfig_enablelogging", true);
            // 仅允许注册的昵称登录
            submitForm.setAnswer("x-muc#roomconfig_reservednick", true);
            // 允许使用者修改昵称
            submitForm.setAnswer("x-muc#roomconfig_canchangenick", false);
            // 允许用户注册房间
            submitForm.setAnswer("x-muc#roomconfig_registration", false);
            // 发送已完成的表单（有默认值）到服务器来配置聊天室
            muc.sendConfigurationForm(submitForm);
        } catch (XMPPException | XmppStringprepException | SmackException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return muc;
    }

    /**
     * 加入会议室
     *
     * @param user      昵称
     * @param roomsName 会议室名
     */
    @Override
    public MultiUserChat joinMultiUserChat(String user, String roomsName) {
        if (XmppManager.getInstance().getConnection() == null)
            return null;
        try {
            // 使用XMPPConnection创建一个MultiUserChat窗口
            MultiUserChat muc = MultiUserChatManager.getInstanceFor(XmppManager.getInstance().getConnection()).getMultiUserChat(
                    JidCreate.entityBareFrom(roomsName + "@conference." + XmppManager.getInstance().getConnection().getServiceName()));

            // 用户加入聊天室
            muc.join(Resourcepart.from(user));

            Log.i("MultiUserChat", "会议室【" + roomsName + "】加入成功........");
            return muc;
        } catch (XMPPException | XmppStringprepException | InterruptedException | SmackException e) {
            e.printStackTrace();
            Log.i("MultiUserChat", "会议室【" + roomsName + "】加入失败........");
            return null;
        }
    }


    /**
     * 查询会议室成员名字
     *
     * @param muc
     */
    @Override
    public List<String> findMulitUser(MultiUserChat muc) {

        if (XmppManager.getInstance().getConnection() == null)
            return null;
        List<String> listUser = new ArrayList<>();
        List<EntityFullJid> it = muc.getOccupants();
        // 遍历出聊天室人员名称
        for (EntityFullJid entityFullJid : it) {
            // 聊天室成员名字
            String name = entityFullJid.toString();
            listUser.add(name);
        }
        return listUser;
    }
}
