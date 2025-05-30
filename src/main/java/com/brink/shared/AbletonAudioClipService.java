package com.brink.shared;

import com.brink.model.ableton.AbletonAudioClip;
import com.brink.model.ableton.AbletonLiveSet;
import com.brink.model.ableton.AbletonTrack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AbletonAudioClipService {
    private static final Logger logger = LoggerFactory.getLogger(AbletonAudioClipService.class);


    public static boolean isInAbletonProject(AbletonAudioClip audioClip) {
        return audioClip.getRelativePath().startsWith("Samples/");
    }


    public static List<AbletonAudioClip> getUniqueAudioClips(AbletonLiveSet liveSet) {

        List<AbletonAudioClip> audioClipList = new ArrayList<>();
        Set<String> addedDeviceNames = new HashSet<>();

        for (AbletonTrack track : liveSet.getTracks().getTracks()) {
            for (AbletonAudioClip audioClip : track.getDeviceChain().getMainSequencer().getSample().getArrangerAutomation().getEvents().getAudioClipList()) {
                String deviceName = audioClip.getName();
                if (!addedDeviceNames.contains(deviceName)) {
                    audioClipList.add(audioClip);
                    addedDeviceNames.add(deviceName);
                }
            }
        }
        return audioClipList;

    }
}
