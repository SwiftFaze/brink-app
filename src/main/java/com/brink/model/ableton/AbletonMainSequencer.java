package com.brink.model.ableton;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonMainSequencer {
    private static final Logger logger = LoggerFactory.getLogger(AbletonMainSequencer.class);

    @XmlElement(name = "Sample")
    private AbletonSample sample = new AbletonSample();

    public AbletonSample getSample() {
        return sample;
    }

    public void setSample(AbletonSample sample) {
        this.sample = sample;
    }
}
