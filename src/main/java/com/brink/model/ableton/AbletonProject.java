package com.brink.model.ableton;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Ableton")
@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonProject {
    private static final Logger logger = LoggerFactory.getLogger(AbletonProject.class);


    @XmlAttribute(name = "MajorVersion")
    private int majorVersion;
    @XmlAttribute(name = "MinorVersion")
    private String minorVersion;
    @XmlAttribute(name = "SchemaChangeCount")
    private int schemaChangeCount;
    @XmlAttribute(name = "Creator")
    private String creator;
    @XmlAttribute(name = "Revision")
    private String revision;
    @XmlElement(name = "LiveSet")
    private AbletonLiveSet liveSet;

    public int getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(int majorVersion) {
        this.majorVersion = majorVersion;
    }

    public String getMinorVersion() {
        return minorVersion;

    }

    public void setMinorVersion(String minorVersion) {
        this.minorVersion = minorVersion;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public int getSchemaChangeCount() {
        return schemaChangeCount;
    }

    public void setSchemaChangeCount(int schemaChangeCount) {
        this.schemaChangeCount = schemaChangeCount;
    }

    public AbletonLiveSet getLiveSet() {
        return liveSet;
    }

    public void setLiveSet(AbletonLiveSet liveSet) {
        this.liveSet = liveSet;
    }


}
