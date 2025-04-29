package com.brink.model.ableton;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "Tracks")
@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonTracks {
    private static final Logger logger = LoggerFactory.getLogger(AbletonTracks.class);

    @Expose
    @SerializedName("AudioTrack")
//    @SerializedName("MidiTrack")  // Mapped to 'MidiTrack' key in JSON
//    @SerializedName("GroupTrack") // Mapped to 'GroupTrack' key in JSON
    private List<AbletonTrack> tracks;


    public List<AbletonTrack> getTracks() {
        return tracks;
    }

    public void setTracks(List<AbletonTrack> tracks) {
        this.tracks = tracks;
    }
}
