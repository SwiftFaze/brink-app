package com.brink.model.dto.AbletonProjectData;


import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonPreHearTrack {

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
