package com.brink.model.ableton;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonDeviceChain {
    private static final Logger logger = LoggerFactory.getLogger(AbletonDeviceChain.class);

    @Expose
    @SerializedName("Devices")
    private AbletonDevices devices;


    public AbletonDevices getDevices() {
        return devices;
    }

    public void setDevices(AbletonDevices devices) {
        this.devices = devices;
    }

}
