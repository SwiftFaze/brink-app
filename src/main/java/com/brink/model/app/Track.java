package com.brink.model.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Track {

    private static final Logger logger = LoggerFactory.getLogger(Track.class);

    private String name;

    private boolean isFrozen;

    private List<Device> deviceList;

    private List<AudioClip> audioClipList;

    public Track() {
        this.name = "";
        this.isFrozen = false;
        this.deviceList = new ArrayList<>();
        this.audioClipList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public List<AudioClip> getAudioClipList() {
        return audioClipList;
    }

    public void setAudioClipList(List<AudioClip> audioClipList) {
        this.audioClipList = audioClipList;
    }
}
