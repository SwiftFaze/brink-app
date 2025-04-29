package com.brink.model.ableton;


import com.brink.model.PluginFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonDevice {
    private static final Logger logger = LoggerFactory.getLogger(AbletonDevice.class);

    @XmlTransient
    private String name;
    @XmlTransient
    private boolean isNative;
    @XmlTransient
    private PluginFormat format;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNative() {
        return isNative;
    }

    public void setNative(boolean aNative) {
        isNative = aNative;
    }

    public PluginFormat getFormat() {
        return format;
    }

    public void setFormat(PluginFormat format) {
        this.format = format;
    }
}
