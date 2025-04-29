package com.brink.shared;

import com.brink.model.ableton.AbletonAudioClip;
import com.brink.model.ableton.AbletonDevice;
import com.brink.model.ableton.AbletonProject;
import com.brink.model.ableton.AbletonTrack;
import com.brink.model.app.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);


    public static Project convert2Project(AbletonProject abletonProject) {
        Project project = new Project();

        project.setName(abletonProject.getFileData().getProjectName());
        project.setVersion(abletonProject.getVersion());
        project.setCompatibility(abletonProject.getCompatibility());
        project.setCollaborator(abletonProject.getCollaborator());

        abletonProject.getLiveSet().getTracks().getTracks().forEach(abletonTrack -> {

            abletonTrack.getDeviceChain().getMainSequencer().getSample().getArrangerAutomation().getEvents().getAudioClipList().forEach(abletonAudioClip -> {
                project.getAudioClipList().add(convert2AudioClip(abletonAudioClip));
            });
            abletonTrack.getDeviceChain().getDeviceChain().getDevices().getDevices().forEach(abletonDevice -> {
                project.getDeviceList().add(convert2Plugin(abletonDevice));
            });

            project.getTracksList().add(convert2Track(abletonTrack));


        });

        return project;
    }


    public static AudioClip convert2AudioClip(AbletonAudioClip abletonAudioClip) {
        AudioClip audioClip = new AudioClip();
        audioClip.setInProject(abletonAudioClip.isInProject());
        audioClip.setName(abletonAudioClip.getName());
        audioClip.setRelativePath(abletonAudioClip.getRelativePath());
        return audioClip;
    }

    public static Device convert2Plugin(AbletonDevice abletonDevice) {
        Plugin plugin = new Plugin();
        plugin.setFormat(abletonDevice.getFormat());
        plugin.setName(abletonDevice.getName());

        Device device = new Device();
        device.setPlugin(plugin);
        device.setNative(abletonDevice.isNative());
        return device;
    }

    public static Track convert2Track(AbletonTrack abletonTrack) {
        Track track = new Track();
        track.setName(abletonTrack.getTrackName().getEffectiveName());
        track.setFrozen(abletonTrack.isFrozen());
        abletonTrack.getDeviceChain().getDeviceChain().getDevices().getDevices().forEach(abletonDevice -> {
            track.getDeviceList().add(convert2Plugin(abletonDevice));
        });
        abletonTrack.getDeviceChain().getMainSequencer().getSample().getArrangerAutomation().getEvents().getAudioClipList().forEach(abletonAudioClip -> {
            track.getAudioClipList().add(convert2AudioClip(abletonAudioClip));
        });
        return track;
    }

}
