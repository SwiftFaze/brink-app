package com.brink.model.app;

import com.brink.model.Collaborator;
import com.brink.model.Compatibility;
import com.brink.model.FileData;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private String name;

    private String version;

    private FileData fileData;

    private Compatibility compatibility;

    private Collaborator collaborator;

    private List<Track> tracksList;

    private List<AudioClip> audioClipList;

    private List<Device> deviceList;

    public Project() {
        this.name = "";
        this.version = "";
        this.fileData = new FileData();
        this.compatibility = new Compatibility();
        this.collaborator = new Collaborator();
        this.tracksList = new ArrayList<>();
        this.audioClipList = new ArrayList<>();
        this.deviceList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public FileData getFileData() {
        return fileData;
    }

    public void setFileData(FileData fileData) {
        this.fileData = fileData;
    }

    public Compatibility getCompatibility() {
        return compatibility;
    }

    public void setCompatibility(Compatibility compatibility) {
        this.compatibility = compatibility;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    public List<Track> getTracksList() {
        return tracksList;
    }

    public void setTracksList(List<Track> tracksList) {
        this.tracksList = tracksList;
    }

    public List<AudioClip> getAudioClipList() {
        return audioClipList;
    }

    public void setAudioClipList(List<AudioClip> audioClipList) {
        this.audioClipList = audioClipList;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }
}
