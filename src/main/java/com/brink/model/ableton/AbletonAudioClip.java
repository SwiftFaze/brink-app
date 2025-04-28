package com.brink.model.ableton;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonAudioClip {
    private static final Logger logger = LoggerFactory.getLogger(AbletonAudioClip.class);

    private String name;

    private String relativePath;

    private boolean isInProject;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public boolean isInProject() {
        return isInProject;
    }

    public void setInProject(boolean inProject) {
        isInProject = inProject;
    }
}
