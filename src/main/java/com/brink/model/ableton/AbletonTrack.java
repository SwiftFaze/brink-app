package com.brink.model.ableton;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonTrack {
    private static final Logger logger = LoggerFactory.getLogger(AbletonTrack.class);

    @Expose
    @SerializedName("Id")
    private int id;

    @Expose
    @SerializedName("LomId")
    private AbletonIntValue lomId;

    @Expose
    @SerializedName("Name")
    private AbletonTrackName name;

    @Expose
    @SerializedName("Freeze")
    private boolean isFrozen;

    @Expose
    @SerializedName("DeviceChain")
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
