package com.brink.model.ableton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonIntValue {
    private static final Logger logger = LoggerFactory.getLogger(AbletonIntValue.class);

    @XmlAttribute(name = "Value")
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int id) {
        this.value = value;
    }

}
