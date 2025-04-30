package com.brink.model.ableton;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonArrangerAutomation {
    private static final Logger logger = LoggerFactory.getLogger(AbletonArrangerAutomation.class);

    @XmlElement(name = "Events")
    private AbletonEvents events = new AbletonEvents();

    public AbletonEvents getEvents() {
        return events;
    }

    public void setEvents(AbletonEvents events) {
        this.events = events;
    }
}
