package com.brink.model.ableton;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonSample {

    @XmlElement(name = "ArrangerAutomation")
    private AbletonArrangerAutomation arrangerAutomation = new AbletonArrangerAutomation();

    public AbletonArrangerAutomation getArrangerAutomation() {
        return arrangerAutomation;
    }

    public void setArrangerAutomation(AbletonArrangerAutomation arrangerAutomation) {
        this.arrangerAutomation = arrangerAutomation;
    }
}
