package com.brink.model.dto.AbletonProjectData;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonTrackName {

    @XmlElement(name = "EffectiveName")
    private AbletonStringValue effectiveName;

    @XmlElement(name = "UserName")
    private AbletonStringValue userName;

    @XmlElement(name = "Annotation")
    private AbletonStringValue annotation;

    @XmlElement(name = "MemorizedFirstClipName")
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
