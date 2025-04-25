package com.brink.model.ableton;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonDeviceChain {

    @XmlElement(name = "Devices")
    private AbletonDevices devices;


    public AbletonDevices getDevices() {
        return devices;
    }

    public void setDevices(AbletonDevices devices) {
        this.devices = devices;
    }

}
