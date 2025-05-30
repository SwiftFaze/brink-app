package com.brink.model.ableton;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonDeviceChain {
    private static final Logger logger = LoggerFactory.getLogger(AbletonDeviceChain.class);

    @XmlElement(name = "Devices")
    private AbletonDevices devices;


    public AbletonDevices getDevices() {
        return devices;
    }

    public void setDevices(AbletonDevices devices) {
        this.devices = devices;
    }

}
