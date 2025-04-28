package com.brink.model.ableton;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonDeviceChainParent {
    private static final Logger logger = LoggerFactory.getLogger(AbletonDeviceChainParent.class);


    @XmlElement(name = "DeviceChain")
    private AbletonDeviceChain deviceChain = new AbletonDeviceChain();

    @XmlElement(name = "MainSequencer")
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
