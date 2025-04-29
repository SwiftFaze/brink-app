package com.brink.model;

import com.brink.model.ableton.AbletonAudioClip;
import com.brink.model.ableton.AbletonLiveSet;
import com.brink.model.ableton.AbletonProject;
import com.brink.model.ableton.AbletonTrack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Compatibility {
    private static final Logger logger = LoggerFactory.getLogger(Compatibility.class);

    private boolean isGitVersioned;
    private boolean isCollected;
    private boolean isCompatible;


    public Compatibility() {
        isGitVersioned = false;
        isCollected = false;
        isCompatible = false;
    }

    public Compatibility(AbletonProject abletonProject) {

        this.isGitVersioned = checkIsGitVersioned(abletonProject.getFileData());
        this.isCollected = checkIsCollected(abletonProject.getLiveSet());
        logger.info("Is GitVersioned : {}", isGitVersioned);
        logger.info("Is collected : {}", isCollected);

    }

    private boolean checkIsGitVersioned(FileData fileData) {
        File parentFile = fileData.getAlsFile().getParentFile();
        return new File(parentFile, ".git").exists();
    }

    private boolean checkIsCollected(AbletonLiveSet abletonLiveSet) {
        for (AbletonTrack abletonTrack : abletonLiveSet.getTracks().getTracks()) {
            for (AbletonAudioClip audioClip : abletonTrack.getDeviceChain()
                    .getMainSequencer()
                    .getSample()
                    .getArrangerAutomation()
                    .getEvents()
                    .getAudioClipList()) {
                if (!audioClip.isInProject()) {
                    return false;
                }
            }
        }
        return true;
    }



    public boolean isGitVersioned() {
        return isGitVersioned;
    }

    public void setGitVersioned(boolean gitVersioned) {
        isGitVersioned = gitVersioned;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public boolean isCompatible() {
        return isCompatible;
    }

    public void setCompatible(boolean compatible) {
        isCompatible = compatible;
    }
}
