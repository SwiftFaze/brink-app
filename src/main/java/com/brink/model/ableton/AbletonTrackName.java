package com.brink.model.ableton;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonTrackName {
    private static final Logger logger = LoggerFactory.getLogger(AbletonTrackName.class);

    @Expose
    @SerializedName("EffectiveName")
    private AbletonStringValue effectiveName;

    @Expose
    @SerializedName("UserName")
    private AbletonStringValue userName;

    @Expose
    @SerializedName("Annotation")
    private AbletonStringValue annotation;

    @Expose
    @SerializedName("MemorizedFirstClipName")
    private AbletonStringValue memorizedFirstClipName;

    public String getEffectiveName() {
        return effectiveName.getValue();
    }

    public void setEffectiveName(AbletonStringValue effectiveName) {
        this.effectiveName = effectiveName;
    }

    public String getUserName() {
        return userName.getValue();
    }

    public void setUserName(AbletonStringValue userName) {
        this.userName = userName;
    }

    public String getAnnotation() {
        return annotation.getValue();
    }

    public void setAnnotation(AbletonStringValue annotation) {
        this.annotation = annotation;
    }

    public String getMemorizedFirstClipName() {
        return memorizedFirstClipName.getValue();
    }

    public void setMemorizedFirstClipName(AbletonStringValue memorizedFirstClipName) {
        this.memorizedFirstClipName = memorizedFirstClipName;
    }
}
