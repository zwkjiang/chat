package com.baweigame.xmpplibrary.impl;

import com.baweigame.xmpplibrary.XmppManager;
import com.baweigame.xmpplibrary.contract.IXmppMsg;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.offline.OfflineMessageManager;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultXmppMsgImpl implements IXmppMsg {
    /**
     * 创建聊天窗口
     *
     * @param JID JID
     * @return Chat
     */
    @Override
    public Chat getFriendChat(String JID) {
        try {
            return ChatManager.getInstanceFor(XmppManager.getInstance().getConnection())
                    .chatWith(JidCreate.entityBareFrom(JID));
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送单人聊天消息
     *
     * @param chat    chat
     * @param message 消息文本
     */
    @Override
    public void sendSingleMessage(Chat chat, String message) {
        try {
            chat.send(message);
        } catch (SmackException.NotConnectedException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据对方jid发送单人聊天消息
     * @param friendjid 对方jid
     * @param message   消息
     */
    @Override
    public void sendSingleMessage(String friendjid, String message) {
        try {
            Chat chat=getFriendChat(friendjid);
            chat.send(message);
        } catch (SmackException.NotConnectedException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送群组聊天消息
     *
     * @param muc     muc
     * @param message 消息文本
     */
    @Override
    public void sendGroupMessage(MultiUserChat muc, String message) {
        try {
            muc.sendMessage(message);
        } catch (SmackException.NotConnectedException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发消息
     *
     * @param chat    chat
     * @param muc     muc
     * @param message message
     */
    @Override
    public void sendMessage(Chat chat, MultiUserChat muc, String message) {
        if (chat != null) {
            sendSingleMessage(chat, message);
        } else if (muc != null) {
            sendGroupMessage(muc, message);
        }
    }

    /**
     * 发送文件
     *
     * @param user
     * @param filePath
     */
    @Override
    public void sendFile(String user, String filePath) {
        if (XmppManager.getInstance().getConnection() == null)
            return;
        // 创建文件传输管理器
        FileTransferManager manager = FileTransferManager.getInstanceFor(XmppManager.getInstance().getConnection());

        // 创建输出的文件传输
        OutgoingFileTransfer transfer = null;
        try {
            transfer = manager.createOutgoingFileTransfer(JidCreate.entityFullFrom(user));
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }

        // 发送文件
        try {
            if (transfer != null)
                transfer.sendFile(new File(filePath), "You won't believe this!");
        } catch (SmackException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取离线消息
     *
     * @return
     */
    @Override
    public Map<String, List<HashMap<String, String>>> getHisMessage() {
        if (XmppManager.getInstance().getConnection() == null)
            return null;
        Map<String, List<HashMap<String, String>>> offlineMsgs = null;

        try {
            OfflineMessageManager offlineManager = new OfflineMessageManager(XmppManager.getInstance().getConnection());
            List<Message> messageList = offlineManager.getMessages();

            int count = offlineManager.getMessageCount();
            if (count <= 0)
                return null;
            offlineMsgs = new HashMap<>();

            for (Message message : messageList) {
                String fromUser = message.getFrom().toString();
                HashMap<String, String> history = new HashMap<>();
                history.put("useraccount", XmppManager.getInstance().getConnection().getUser().asEntityBareJidString());
                history.put("friendaccount", fromUser);
                history.put("info", message.getBody());
                history.put("type", "left");
                if (offlineMsgs.containsKey(fromUser)) {
                    offlineMsgs.get(fromUser).add(history);
                } else {
                    List<HashMap<String, String>> temp = new ArrayList<HashMap<String, String>>();
                    temp.add(history);
                    offlineMsgs.put(fromUser, temp);
                }
            }
            offlineManager.deleteMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return offlineMsgs;
    }
}
