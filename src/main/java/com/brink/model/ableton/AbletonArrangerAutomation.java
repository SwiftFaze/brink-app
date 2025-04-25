package com.brink.model.ableton;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonArrangerAutomation {

    @XmlElement(name = "Events")
    private AbletonEvents events = new AbletonEvents();

    public AbletonEvents getEvents() {
        return events;
    }

    public void setEvents(AbletonEvents events) {
        this.events = events;
    }
}
