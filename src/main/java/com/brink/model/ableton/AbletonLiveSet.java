package com.brink.model.ableton;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "LiveSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonLiveSet {
    private static final Logger logger = LoggerFactory.getLogger(AbletonLiveSet.class);

    @Expose
    @SerializedName("Tracks")
    private AbletonTracks tracks;

    @Expose
    @SerializedName("MasterTrack")
    private AbletonMasterTrack masterTrack;

    @Expose
    @SerializedName("PreHearTrack")
    private AbletonPreHearTrack preHearTrack;


    public AbletonTracks getTracks() {
        return tracks;
    }

    public void setTracks(AbletonTracks tracks) {
        this.tracks = tracks;
    }

    public AbletonMasterTrack getMasterTrack() {
        return masterTrack;
    }

    public void setMasterTrack(AbletonMasterTrack masterTrack) {
        this.masterTrack = masterTrack;
    }

    public AbletonPreHearTrack getAbletonPreHearTrack() {
        return preHearTrack;
    }

    public void setAbletonPreHearTrack(AbletonPreHearTrack preHearTrack) {
        this.preHearTrack = preHearTrack;
    }

}
