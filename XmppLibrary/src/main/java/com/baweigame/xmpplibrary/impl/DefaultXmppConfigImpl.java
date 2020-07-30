package com.baweigame.xmpplibrary.impl;

import com.baweigame.xmpplibrary.contract.IXmppConfig;

/**
 * 默认XmppConfig实现类
 */
public class DefaultXmppConfigImpl implements IXmppConfig {
    @Override
    public String getDomainName() {
        return "api.city2sky";
    }

    @Override
    public String getHostAddress() {
        return "39.100.125.37";
    }

    @Override
    public int getPort() {
        return 5222;
    }
}
