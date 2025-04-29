package com.brink.model.app;

import com.brink.model.PluginFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Device {
    private static final Logger logger = LoggerFactory.getLogger(Device.class);

    private Plugin plugin;
    private boolean isNative;

    public Plugin getPlugin() {
        return plugin;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean isNative() {
        return isNative;
    }

    public void setNative(boolean aNative) {
        isNative = aNative;
    }
}
