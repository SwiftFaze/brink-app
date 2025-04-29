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
public class AbletonMasterTrack {
    private static final Logger logger = LoggerFactory.getLogger(AbletonMasterTrack.class);

    @Expose
    @SerializedName("Name")
    private AbletonTrackName name;

    public AbletonTrackName getTrackName() {
        return name;
    }

    public void setTrackName(AbletonTrackName name) {
        this.name = name;
    }
}
