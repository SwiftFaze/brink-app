package com.brink.model.ableton;

import com.brink.model.PluginFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonDevices {
    private static final Logger logger = LoggerFactory.getLogger(AbletonDevices.class);

    @XmlAnyElement
    private List<Element> deviceElements;  // DOM elements of unknown tag names

    public List<AbletonDevice> getDevices() {
        if (deviceElements == null) return new ArrayList<>();

        List<AbletonDevice> devices = new ArrayList<>();

        for (Element element : deviceElements) {
            String tagName = element.getTagName(); // e.g., "GrainDelay", "PluginDevice"


            if ("PluginDevice".equals(tagName)) {
                AbletonDevice device = new AbletonDevice();
                device.setName(extractPluginName(element));
                device.setNative(false);
                device.setFormat(PluginFormat.extractPluginFormat(extractPluginPath(element)));
                devices.add(device);

            } else if ("AudioEffectGroupDevice".equals(tagName) || "InstrumentGroupDevice".equals(tagName)) {
                AbletonDevice device = new AbletonDevice();
                device.setName("Plugin group");
                device.setNative(false);
                device.setFormat(PluginFormat.extractPluginFormat(extractPluginPath(element)));
                devices.add(device);

            } else {
                AbletonDevice device = new AbletonDevice();
                device.setName(tagName);
                device.setNative(true);
                device.setFormat(PluginFormat.extractPluginFormat(extractPluginPath(element)));
                devices.add(device);

            }


        }

        return devices;
    }

    private String extractPluginName(Element pluginElement) {
        NodeList plugNameNodes = pluginElement.getElementsByTagName("PlugName");
        if (plugNameNodes != null && plugNameNodes.getLength() > 0) {
            Element plugNameElement = (Element) plugNameNodes.item(0);
            return plugNameElement.getAttribute("Value");
        }
        return null;
    }
    private String extractPluginPath(Element pluginElement) {
        NodeList plugPathNodes = pluginElement.getElementsByTagName("Path");
        if (plugPathNodes != null && plugPathNodes.getLength() > 0) {
            Element plugNameElement = (Element) plugPathNodes.item(0);
            return plugNameElement.getAttribute("Value");
        }
        return null;
    }



    public List<Element> getDeviceElements() {
        return deviceElements;
    }

    public void setDeviceElements(List<Element> deviceElements) {
        this.deviceElements = deviceElements;
    }
}
