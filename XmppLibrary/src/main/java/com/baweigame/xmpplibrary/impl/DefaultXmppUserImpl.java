package com.baweigame.xmpplibrary.impl;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import com.bawei6.common.BitmapUtils;
import com.bawei6.common.FileUtils;
import com.baweigame.xmpplibrary.XmppManager;
import com.baweigame.xmpplibrary.contract.IXmppUser;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.StanzaIdFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.iqregister.packet.Registration;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jxmpp.jid.EntityFullJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultXmppUserImpl implements IXmppUser {
    /**
     * 用户是否在线
     */
    private Presence.Type isOnLine;

    /**
     * 是否登陆
     * @return
     */
    @Override
    public boolean isLogin(){
        return XmppManager.getInstance().checkConnect()&&XmppManager.getInstance().getConnection().isAuthenticated();
    }

    /**
     * 登录
     * @param userName 用户名
     * @param passWord 密码
     * @return
     */
    @Override
    public boolean login(String userName, String passWord) {
        try {
            if (!XmppManager.getInstance().getConnection().isAuthenticated()) { // 判断是否登录
                XmppManager.getInstance().getConnection().login(userName, passWord);

                return true;
            }
            return false;
        } catch (XMPPException | SmackException | InterruptedException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  注销登录
     * @return
     */
    @Override
    public boolean logout() {
        XmppManager.getInstance().getConnection().disconnect();
        return false;
    }

    /**
     * 创建用户
     * @param userName 用户名
     * @param passWord 密码
     */
    @Override
    public void createAccount(String userName, String passWord){
        if (TextUtils.isEmpty(userName)) {
            throw new IllegalArgumentException("Username must not be null");
        }
        if (TextUtils.isEmpty(passWord)) {
            throw new IllegalArgumentException("Password must not be null");
        }

        Map<String, String> attributes = new HashMap<>();
        attributes.put("username", userName); // 设置用户名
        attributes.put("password", passWord); // 设置密码
        Registration reg = new Registration(attributes);
        reg.setType(IQ.Type.set); // 设置类型
        reg.setTo(XmppManager.getInstance().getConnection().getXMPPServiceDomain());// 设置发送地址
        try {
            createStanzaCollectorAndSend(reg).nextResultOrThrow();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前用户
     * @return
     */
    @Override
    public EntityFullJid getCurrentUser() {
        return XmppManager.getInstance().getConnection().getUser();
    }

    /**
     * 获取用户头像信息
     *
     * @param user user
     * @return Drawable
     */
    @Override
    public Drawable getUserImage(String user) {
        if (XmppManager.getInstance().getConnection() == null)
            return null;
        ByteArrayInputStream bais = null;
        try {
            VCard vcard = new VCard();
            // 加入这句代码，解决No VCard for
            ProviderManager.addIQProvider("vCard", "vcard-temp",
                    new org.jivesoftware.smackx.vcardtemp.provider.VCardProvider());
            if (user == null || user.equals("") || user.trim().length() <= 0) {
                return null;
            }
            try {
                VCardManager.getInstanceFor(XmppManager.getInstance().getConnection()).loadVCard(
                        JidCreate.entityBareFrom(user + "@" + XmppManager.getInstance().getConnection().getServiceName()));
            } catch (XmppStringprepException | SmackException | InterruptedException | XMPPException.XMPPErrorException e) {
                e.printStackTrace();
            }

            if (vcard.getAvatar() == null)
                return null;
            bais = new ByteArrayInputStream(vcard.getAvatar());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return BitmapUtils.getInstance().InputStream2Drawable(bais);
    }

    /**
     * 获取好友列表
     */
    @Override
    public List<RosterEntry> getFriendList() {
        Roster roster = Roster.getInstanceFor(XmppManager.getInstance().getConnection());
        Set<RosterEntry> entries = roster.getEntries();
        List<RosterEntry> list=new ArrayList<>();
        for (RosterEntry entry : entries) {
            list.add(entry);
        }
        return list;
    }


    private StanzaCollector createStanzaCollectorAndSend(IQ req) throws
            SmackException.NotConnectedException, InterruptedException {
        StanzaCollector collector = XmppManager.getInstance().getConnection().createStanzaCollectorAndSend(new StanzaIdFilter(req.getStanzaId()), req);
        return collector;
    }

    /**
     * 修改用户头像
     *
     * @param file file
     */
    @Override
    public boolean changeImage(File file) {
        if (XmppManager.getInstance().getConnection() == null)
            return false;
        try {
            VCard vcard = new VCard();
            vcard.load(XmppManager.getInstance().getConnection());

            byte[] bytes;

            bytes = FileUtils.getFileBytes(file);
            String encodedImage = StringUtils.encodeHex(bytes);
            vcard.setAvatar(bytes, encodedImage);
            vcard.setEncodedImage(encodedImage);
            vcard.setField("PHOTO", "<TYPE>image/jpg</TYPE><BINVAL>"
                    + encodedImage + "</BINVAL>", true);

            ByteArrayInputStream bais = new ByteArrayInputStream(
                    vcard.getAvatar());
            BitmapUtils.getInstance().InputStream2Bitmap(bais);

            vcard.save(XmppManager.getInstance().getConnection());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 注销当前用户
     *
     * @return true成功
     */
    @Override
    public boolean deleteAccount() {
        if (XmppManager.getInstance().getConnection() == null)
            return false;
        try {
            AccountManager.getInstance(XmppManager.getInstance().getConnection()).deleteAccount();
            return true;
        } catch (XMPPException | SmackException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改密码
     *
     * @return true成功
     */
    @Override
    public boolean changePassword(String pwd) {
        if (XmppManager.getInstance().getConnection() == null)
            return false;
        try {
            AccountManager.getInstance(XmppManager.getInstance().getConnection()).changePassword(pwd);
            return true;
        } catch (SmackException | InterruptedException | XMPPException.XMPPErrorException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断OpenFire用户的状态 strUrl :
     * url格式 - http://my.openfire.com:9090/plugins/presence
     * /status?jid=user1@SERVER_NAME&type=xml
     * 返回值 : 0 - 用户不存在; 1 - 用户在线; 2 - 用户离线
     * 说明 ：必须要求 OpenFire加载 presence 插件，同时设置任何人都可以访问
     */
    @Override
    public int IsUserOnLine(String user) {
        String url = "http://" + XmppManager.getInstance().getXmppConfig().getHostAddress() + ":9090/plugins/presence/status?" +
                "jid=" + user + "@" + XmppManager.getInstance().getXmppConfig().getDomainName() + "&type=xml";
        int shOnLineState = 0; // 不存在
        try {
            URL oUrl = new URL(url);
            URLConnection oConn = oUrl.openConnection();
            if (oConn != null) {
                BufferedReader oIn = new BufferedReader(new InputStreamReader(
                        oConn.getInputStream()));
                String strFlag = oIn.readLine();
                oIn.close();
                System.out.println("strFlag" + strFlag);
                if (strFlag.contains("type=\"unavailable\"")) {
                    shOnLineState = 2;
                }
                if (strFlag.contains("type=\"error\"")) {
                    shOnLineState = 0;
                } else if (strFlag.contains("priority") || strFlag.contains("id=\"")) {
                    shOnLineState = 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return shOnLineState;
    }

    /**
     * 设置在线、离线等状态
     * @param type
     */
    @Override
    public void setOnLine(Presence.Type type) {
        try {
            isOnLine = null;
            if (isOnLine != type) {
                Presence presence = new Presence(type);
                //presence.setStatus("Gone fishing");// 设置状态消息
                XmppManager.getInstance().getConnection().sendStanza(presence);
                isOnLine = type;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更改用户状态
     */
    @Override
    public void setPresence(int code) {
        XMPPConnection con = XmppManager.getInstance().getConnection();
        if (con == null)
            return;
        Presence presence;
        try {
            switch (code) {
                case 0:
                    presence = new Presence(Presence.Type.available);
                    con.sendStanza(presence);
                    Log.v("state", "设置在线");
                    break;
                case 1:
                    presence = new Presence(Presence.Type.available);
                    presence.setMode(Presence.Mode.chat);
                    con.sendStanza(presence);
                    Log.v("state", "设置Q我吧");
                    break;
                case 2:
                    presence = new Presence(Presence.Type.available);
                    presence.setMode(Presence.Mode.dnd);
                    con.sendStanza(presence);
                    Log.v("state", "设置忙碌");
                    break;
                case 3:
                    presence = new Presence(Presence.Type.available);
                    presence.setMode(Presence.Mode.away);
                    con.sendStanza(presence);
                    Log.v("state", "设置离开");
                    break;
                case 4:
//                    Roster roster = con.getRoster();
//                    Collection<RosterEntry> entries = roster.getEntries();
//                    for (RosterEntry entry : entries) {
//                        presence = new Presence(Presence.Type.unavailable);
//                        presence.setPacketID(Packet.ID_NOT_AVAILABLE);
//                        presence.setFrom(con.getUser());
//                        presence.setTo(entry.getUser());
//                        con.sendPacket(presence);
//                        Log.v("state", presence.toXML());
//                    }
//                    // 向同一用户的其他客户端发送隐身状态
//                    presence = new Presence(Presence.Type.unavailable);
//                    presence.setPacketID(Packet.ID_NOT_AVAILABLE);
//                    presence.setFrom(con.getUser());
//                    presence.setTo(StringUtils.parseBareAddress(con.getUser()));
//                    con.sendStanza(presence);
//                    Log.v("state", "设置隐身");
//                    break;
                case 5:
                    presence = new Presence(Presence.Type.unavailable);
                    con.sendStanza(presence);
                    Log.v("state", "设置离线");
                    break;
                default:
                    break;
            }
        } catch (SmackException.NotConnectedException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户VCard信息
     *
     * @param user user
     * @return VCard
     */
    @Override
    public VCard getUserVCard(String user) {
        if (XmppManager.getInstance().getConnection() == null)
            return null;
        VCard vcard = new VCard();
        try {
            vcard = VCardManager.getInstanceFor(XmppManager.getInstance().getConnection()).loadVCard(JidCreate.entityBareFrom(user));
        } catch (XmppStringprepException | SmackException | InterruptedException | XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        }

        return vcard;
    }
}
