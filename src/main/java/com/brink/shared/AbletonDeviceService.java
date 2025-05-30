package com.brink.shared;

import com.brink.model.ableton.AbletonDevice;
import com.brink.model.ableton.AbletonLiveSet;
import com.brink.model.ableton.AbletonTrack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AbletonDeviceService {
    private static final Logger logger = LoggerFactory.getLogger(AbletonDeviceService.class);


    public static List<AbletonDevice> getUniqueDevices(AbletonLiveSet liveSet) {
        List<AbletonDevice> abletonDeviceList = new ArrayList<>();
        Set<String> addedDeviceNames = new HashSet<>();

        for (AbletonTrack track : liveSet.getTracks().getTracks()) {
            for (AbletonDevice device : track.getDeviceChain().getDeviceChain().getDevices().getDevices()) {
                String deviceName = device.getName();
                if (!addedDeviceNames.contains(deviceName)) {
                    abletonDeviceList.add(device);
                    addedDeviceNames.add(deviceName);
                }
            }
        }
        return abletonDeviceList;
    }


}
