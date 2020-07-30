package com.baweigame.xmpplibrary;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.bawei6.common.LogUtils;
import com.baweigame.xmpplibrary.callback.IAddFriendCallback;
import com.baweigame.xmpplibrary.callback.IMsgCallback;
import com.baweigame.xmpplibrary.contract.IXmppChatRoom;
import com.baweigame.xmpplibrary.contract.IXmppConfig;
import com.baweigame.xmpplibrary.contract.IXmppFriend;
import com.baweigame.xmpplibrary.contract.IXmppGroup;
import com.baweigame.xmpplibrary.contract.IXmppMsg;
import com.baweigame.xmpplibrary.contract.IXmppUser;
import com.baweigame.xmpplibrary.entity.MsgEntity;
import com.baweigame.xmpplibrary.impl.DefaultXmppChatRoomImpl;
import com.baweigame.xmpplibrary.impl.DefaultXmppConfigImpl;
import com.baweigame.xmpplibrary.impl.DefaultXmppFriendImpl;
import com.baweigame.xmpplibrary.impl.DefaultXmppGroupImpl;
import com.baweigame.xmpplibrary.impl.DefaultXmppMsgImpl;
import com.baweigame.xmpplibrary.impl.DefaultXmppUserImpl;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.roster.SubscribeListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.ping.PingFailedListener;
import org.jivesoftware.smackx.ping.PingManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.PropertyResourceBundle;

public class XmppManager {

    private final String TAG=XmppManager.class.getSimpleName();

    /**
     * Xmpp Connection
     */
    private AbstractXMPPConnection mConnection;

    private volatile static XmppManager instance=null;
    private XmppManager(){
        //连接IM服务器
        connect();

    }
    public static XmppManager getInstance(){
        if (instance==null){
            synchronized (XmppManager.class){
                if (instance==null){
                    instance=new XmppManager();
                }
            }
        }
        return instance;
    }

    /**
     * Xmpp 配置信息
     */
    private IXmppConfig xmppConfig;

    /**
     * 用户管理
     */
    private IXmppUser xmppUserManager;

    /**
     * 好友管理
     */
    private IXmppFriend xmppFriendManager;

    /**
     * 分组管理
     */
    private IXmppGroup xmppGroupManager;

    /**
     * 聊天室管理
     */
    private IXmppChatRoom xmppChatRoomManager;

    /**
     * 消息管理
     */
    private IXmppMsg xmppMsgManager;

    /**
     * 获取XmppConfig
     * @return
     */
    public IXmppConfig getXmppConfig(){
        if (null==xmppConfig){
            xmppConfig=new DefaultXmppConfigImpl();
        }
        return xmppConfig;
    }

    /**
     * 设置XmppConfig
     * @param _xmppConfig
     */
    public void setXmppConfig(IXmppConfig _xmppConfig){
        this.xmppConfig=_xmppConfig;
    }

    /**
     * 获取用户管理对象
     * @return
     */
    public IXmppUser getXmppUserManager(){
        if (null==xmppUserManager){
            xmppUserManager=new DefaultXmppUserImpl();
        }
        return xmppUserManager;
    }

    /**
     * 设置User管理类
     *
     * @param _xmppUserManager
     */
    public void setXmppUserManager(IXmppUser _xmppUserManager){
        this.xmppUserManager=_xmppUserManager;
    }

    /**
     * 获取好友管理类
     * @return
     */
    public IXmppFriend getXmppFriendManager() {
        if (null==xmppFriendManager){
            xmppFriendManager=new DefaultXmppFriendImpl();
        }
        return xmppFriendManager;
    }

    /**
     * 设置好友管理类
     * @param xmppFriendManager
     */
    public void setXmppFriendManager(IXmppFriend xmppFriendManager) {
        this.xmppFriendManager = xmppFriendManager;
    }

    /**
     * 获取分组管理类
     * @return
     */
    public IXmppGroup getXmppGroupManager() {
        if (null==xmppGroupManager){
            xmppGroupManager=new DefaultXmppGroupImpl();
        }
        return xmppGroupManager;
    }

    /**
     * 设置分组管理类
     * @param xmppGroupManager
     */
    public void setXmppGroupManager(IXmppGroup xmppGroupManager) {
        this.xmppGroupManager = xmppGroupManager;
    }

    /**
     * 获取聊天室管理类
     * @return
     */
    public IXmppChatRoom getXmppChatRoomManager() {
        if (null==xmppChatRoomManager){
            xmppChatRoomManager=new DefaultXmppChatRoomImpl();
        }
        return xmppChatRoomManager;
    }

    /**
     * 设置聊天室管理类
     * @param xmppChatRoomManager
     */
    public void setXmppChatRoomManager(IXmppChatRoom xmppChatRoomManager) {
        this.xmppChatRoomManager = xmppChatRoomManager;
    }

    /**
     * 获取消息管理类
     * @return
     */
    public IXmppMsg getXmppMsgManager() {
        if (null==xmppMsgManager){
            xmppMsgManager=new DefaultXmppMsgImpl();
        }
        return xmppMsgManager;
    }

    /**
     * 设置消息管理类
     * @param xmppMsgManager
     */
    public void setXmppMsgManager(IXmppMsg xmppMsgManager) {
        this.xmppMsgManager = xmppMsgManager;
    }

    /**
     * 连接Xmpp服务器
     */
    private void connect(){
        try {
            XMPPTCPConnectionConfiguration configuration=XMPPTCPConnectionConfiguration.builder()
                    .setXmppDomain(getXmppConfig().getDomainName())
                    .setHostAddress(InetAddress.getByName(getXmppConfig().getHostAddress()))
                    .setPort(getXmppConfig().getPort())
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .setCompressionEnabled(false)
                    .setDebuggerEnabled(true)
                    //.setSendPresence(false)//设置离线状态获取离线消息
                    .build();

            //设置需要经过同意才可以加为好友
            Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
            AbstractXMPPConnection connection = new XMPPTCPConnection(configuration);

            connection.connect();// 连接, 可设置监听
            connection.addConnectionListener(new XMPPConnectionListener());
            this.mConnection = connection;


        } catch (XmppStringprepException e) {
            LogUtils.e(e.getMessage());
        } catch (UnknownHostException e) {
            LogUtils.e(e.getMessage());
        } catch (InterruptedException e) {
            LogUtils.e(e.getMessage());
        } catch (IOException e) {
            LogUtils.e(e.getMessage());
        } catch (SmackException e) {
            LogUtils.e(e.getMessage());
        } catch (XMPPException e) {
            LogUtils.e(e.getMessage());
        }
    }

    private Handler mHandler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
        }
    };

    /**
     * 跳线处理 使用心跳逻辑
     */
    private void startHeartDump(){
        PingManager.setDefaultPingInterval(10);
        final PingManager pingManager = PingManager.getInstanceFor(this.mConnection);
        pingManager.registerPingFailedListener(new PingFailedListener() {
            @Override
            public void pingFailed() {
                LogUtils.e("Ping IMServer 失败...");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            LogUtils.i("尝试重连中...");
                            mConnection.connect();
                            if (!mConnection.isAuthenticated()){
                                LogUtils.i("连接失败，继续重连...");
                                mHandler.postDelayed(this,3*1000);
                                return;
                            }
                            LogUtils.i("重新连接成功...");
                            //mHandler.removeCallbacksAndMessages(null);
                        } catch (SmackException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XMPPException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                },3*1000);


            }
        });
    }

    /**
     * 添加链接监听
     */
    public XmppManager addConnectionListener(){
        if (getConnection()==null){
            return this;
        }
        getConnection().addConnectionListener(new XMPPConnectionListener());
        return this;
    }

    /**
     * 添加好友变化监听
     */
    public XmppManager addRosterListener(){
        addFriendListener();
        return this;
    }

    /**
     * 获取连接
     * @return
     */
    public AbstractXMPPConnection getConnection(){
        return this.mConnection;
    }


    /**
     * 检查是否连接
     */
    public Boolean checkConnect(){
        return this.mConnection!=null&&this.mConnection.isConnected();
    }

    /**
     * 关闭连接
     */
    public void disConnect(){
        if (mConnection.isConnected()) {
            mConnection.disconnect();
        }
        mConnection=null;
    }

    private IAddFriendCallback iAddFriendCallback;

    /**
     * 添加好友监听
     * @param callback
     */
    public void setAddFriendCallback(IAddFriendCallback callback){
        iAddFriendCallback=callback;
    }

    /**
     * 获取到其他人添加自己的信息监听
     */
    private class AddFriendMessageListener implements SubscribeListener {
        @Override public SubscribeAnswer processSubscribe(Jid from, Presence subscribeRequest) {
            LogUtils.d("有人添加："+from.asBareJid());
            if (null!=iAddFriendCallback){
                iAddFriendCallback.notify(from);
            }
            //getXmppFriendManager().addFriend(from.asBareJid().toString(),from.asBareJid().toString(),null);
            return SubscribeAnswer.Approve;
        }
    }

    /**
     * 可以接收到其他人是否删除了自己,好友在线状态改变
     */
    private class ReceiverFriendStatusListener implements RosterListener {
        @Override public void entriesAdded(Collection<Jid> addresses) {
            LogUtils.d("entriesAdded");
        }

        @Override public void entriesUpdated(Collection<Jid> addresses) {
            LogUtils.d("entriesUpdated");
        }

        @Override public void entriesDeleted(Collection<Jid> addresses) {
            LogUtils.d("entriesDeleted");
        }

        @Override public void presenceChanged(Presence presence) {
            LogUtils.d("presenceChanged");
        }
    }

    /**
     * 服务器连接监听
     */
    private class XMPPConnectionListener implements ConnectionListener {
        @Override
        public void connected(XMPPConnection connection) {

            LogUtils.d("connected: ");
        }

        @Override
        public void authenticated(XMPPConnection connection, boolean resumed) {
            LogUtils.d("authenticated: ");
            addRosterListener();
            //开启心跳处理
            //startHeartDump();
            LogUtils.i("start heartdump...");
        }

        @Override
        public void connectionClosed() {
            Log.d(TAG, "connectionClosed: ");
            //开启心跳处理
            //startHeartDump();
        }

        @Override
        public void connectionClosedOnError(Exception e) {
            LogUtils.d("connectionClosedOnError: ");
            //开启心跳处理
            //startHeartDump();
        }

        @Override
        public void reconnectionSuccessful() {
            LogUtils.d("reconnectionSuccessful: ");
        }

        @Override
        public void reconnectingIn(int seconds) {
            LogUtils.d( "reconnectingIn: ");
        }

        @Override
        public void reconnectionFailed(Exception e) {
            LogUtils.d("reconnectionFailed: ");
        }
    }

    /**
     * 添加关于好友的监听
     */
    private void addFriendListener() {
        // 获取当前角色
        Roster roster = Roster.getInstanceFor(mConnection);
        // 接收到好友信息变化
        ReceiverFriendStatusListener mReceiverFriendStatusListener = new ReceiverFriendStatusListener();
        roster.addRosterListener((RosterListener) mReceiverFriendStatusListener);
        // 接收到添加好友信息
        AddFriendMessageListener mAddFriendMessageListener = new AddFriendMessageListener();
        roster.addSubscribeListener((SubscribeListener) mAddFriendMessageListener);
    }

    /**
     * 添加消息监听
     */
    public void addMessageListener(final IMsgCallback callback){
        ChatManager.getInstanceFor(getConnection()).addIncomingListener(new IncomingChatMessageListener() {
            @Override
            public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
                if (message!=null&&!TextUtils.isEmpty(message.getBody())){
                    LogUtils.d("from:"+message.getFrom()+"  to:"+message.getTo()+" message body -> "+message.getBody());
                    MsgEntity msgEntity=new MsgEntity();
                    String ff=message.getFrom().asEntityBareJidIfPossible().toString();
                    msgEntity.setFrom(ff.substring(0,ff.lastIndexOf("@")));
                    msgEntity.setTo(message.getTo().asBareJid().toString());
                    msgEntity.setMsg(message.getBody());
                    callback.Success(msgEntity);
                }
            }
        });

        ChatManager.getInstanceFor(getConnection()).addOutgoingListener(new OutgoingChatMessageListener() {
            @Override
            public void newOutgoingMessage(EntityBareJid to, Message message, Chat chat) {
                if (message!=null&&!TextUtils.isEmpty(message.getBody())){
                    LogUtils.d("to:"+message.getTo()+"  from:"+message.getFrom()+" message body -> "+message.getBody());

                }
            }
        });
    }


}
