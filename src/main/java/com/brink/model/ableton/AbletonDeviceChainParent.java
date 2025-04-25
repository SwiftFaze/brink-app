package com.brink.model.ableton;


import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonDeviceChainParent {


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
