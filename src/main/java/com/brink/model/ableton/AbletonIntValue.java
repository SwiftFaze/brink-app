package com.brink.model.ableton;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonIntValue {
    private static final Logger logger = LoggerFactory.getLogger(AbletonIntValue.class);

    @Expose
    @SerializedName("Value")
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int id) {
        this.value = value;
    }

}
