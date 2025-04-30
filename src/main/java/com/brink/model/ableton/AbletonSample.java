package com.brink.model.ableton;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonSample {
    private static final Logger logger = LoggerFactory.getLogger(AbletonSample.class);

    @XmlElement(name = "ArrangerAutomation")
    private AbletonArrangerAutomation arrangerAutomation = new AbletonArrangerAutomation();

    public AbletonArrangerAutomation getArrangerAutomation() {
        return arrangerAutomation;
    }

    public void setArrangerAutomation(AbletonArrangerAutomation arrangerAutomation) {
        this.arrangerAutomation = arrangerAutomation;
    }
}
