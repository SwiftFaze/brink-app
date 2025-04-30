package com.brink.model.ableton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonStringValue {
    private static final Logger logger = LoggerFactory.getLogger(AbletonStringValue.class);

    @XmlAttribute(name = "Value")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String id) {
        this.value = value;
    }

}
