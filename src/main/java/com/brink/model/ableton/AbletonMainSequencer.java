package com.brink.model.ableton;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonMainSequencer {

    @XmlElement(name = "Sample")
    private AbletonSample sample = new AbletonSample();

    public AbletonSample getSample() {
        return sample;
    }

    public void setSample(AbletonSample sample) {
        this.sample = sample;
    }
}
