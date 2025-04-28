package com.brink.model.ableton;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonMasterTrack {
    private static final Logger logger = LoggerFactory.getLogger(AbletonMasterTrack.class);

    @XmlAttribute(name = "Id")
    private int id;

    @XmlElement(name = "LomId")
    private AbletonIntValue lomId;

    @XmlElement(name = "Name")
    private AbletonTrackName name;


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
}
