package com.brink.model.ableton;

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

    @XmlElement(name = "Tracks")
    private AbletonTracks tracks;

    @XmlElement(name = "MasterTrack")
    private AbletonMasterTrack masterTrack;

    @XmlElement(name = "PreHearTrack")
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
