package com.brink.model.ableton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "Tracks")
@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonTracks {
    private static final Logger logger = LoggerFactory.getLogger(AbletonTracks.class);

    @XmlElements({
            @XmlElement(name = "AudioTrack", type = AbletonTrack.class),
            @XmlElement(name = "MidiTrack", type = AbletonTrack.class),
            @XmlElement(name = "GroupTrack", type = AbletonTrack.class)
    })
    private List<AbletonTrack> tracks;


    public List<AbletonTrack> getTracks() {
        return tracks;
    }

    public void setTracks(List<AbletonTrack> tracks) {
        this.tracks = tracks;
    }
}
