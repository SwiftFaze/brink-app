package com.brink.model.ableton;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonDeviceChainParent {
    private static final Logger logger = LoggerFactory.getLogger(AbletonDeviceChainParent.class);


    @Expose
    @SerializedName("DeviceChain")
    private AbletonDeviceChain deviceChain = new AbletonDeviceChain();

    @Expose
    @SerializedName("MainSequencer")
    private AbletonMainSequencer MainSequencer = new AbletonMainSequencer();

    public AbletonDeviceChain getDeviceChain() {
        return deviceChain;
    }

    public void setDeviceChain(AbletonDeviceChain deviceChain) {
        this.deviceChain = deviceChain;
    }

    public AbletonMainSequencer getMainSequencer() {
        return MainSequencer;
    }

    public void setMainSequencer(AbletonMainSequencer mainSequencer) {
        MainSequencer = mainSequencer;
    }
}
