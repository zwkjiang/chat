package com.baweigame.xmpplibrary.callback;

import com.baweigame.xmpplibrary.entity.MsgEntity;

public interface IMsgCallback {
    void Success(MsgEntity msgEntity);
    void Failed(Throwable throwable);
}
