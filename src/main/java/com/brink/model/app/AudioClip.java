package com.brink.model.app;

import com.brink.model.ableton.AbletonAudioClip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioClip {
    private static final Logger logger = LoggerFactory.getLogger(AudioClip.class);

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
