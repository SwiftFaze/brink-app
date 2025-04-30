package com.brink.model.ableton;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonTrack {
    private static final Logger logger = LoggerFactory.getLogger(AbletonTrack.class);

    @XmlAttribute(name = "Id")
    private int id;

    @XmlElement(name = "LomId")
    private AbletonIntValue lomId;

    @XmlElement(name = "Name")
    private AbletonTrackName name;

    @XmlElement(name = "Freeze")
    private boolean isFrozen;

    @XmlElement(name = "DeviceChain")
    private AbletonDeviceChainParent deviceChain;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLomId() {
        return lomId.getValue();
    }

    public void setLomId(AbletonIntValue lomId) {
        this.lomId = lomId;
    }

    public AbletonTrackName getTrackName() {
        return name;
    }

    public void setTrackName(AbletonTrackName name) {
        this.name = name;
    }

    public AbletonTrackName getName() {
        return name;
    }

    public void setName(AbletonTrackName name) {
        this.name = name;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    public AbletonDeviceChainParent getDeviceChain() {
        return deviceChain;
    }

    public void setDeviceChain(AbletonDeviceChainParent deviceChain) {
        this.deviceChain = deviceChain;
    }
}
