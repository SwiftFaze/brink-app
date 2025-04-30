package com.brink.model.ableton;


import com.brink.shared.AbletonAudioClipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonEvents {
    private static final Logger logger = LoggerFactory.getLogger(AbletonEvents.class);

    @XmlAnyElement
    private List<Element> audioClipElements;


    public List<Element> getAudioClipElements() {
        return audioClipElements;
    }

    public void setAudioClipElements(List<Element> audioClipElements) {
        this.audioClipElements = audioClipElements;
    }


    public List<AbletonAudioClip> getAudioClipList() {
        if (audioClipElements == null) return new ArrayList<>();

        List<AbletonAudioClip> audioClipList = new ArrayList<>();

        for (Element element : audioClipElements) {
            String tagName = element.getTagName(); // e.g., "GrainDelay", "PluginDevice"


            if ("AudioClip".equals(tagName)) {
                AbletonAudioClip audioClip = new AbletonAudioClip();
                audioClip.setName(extractName(element));
                audioClip.setRelativePath(extractRelativePath(element));
                audioClip.setInProject(AbletonAudioClipService.isInAbletonProject(audioClip));
                audioClipList.add(audioClip);
            }


        }

        return audioClipList;
    }


    private String extractRelativePath(Element audioClipElement) {
        // Find the <SampleRef> element inside the <AudioClip>
        NodeList sampleRefNodes = audioClipElement.getElementsByTagName("SampleRef");
        if (sampleRefNodes != null && sampleRefNodes.getLength() > 0) {
            Element sampleRefElement = (Element) sampleRefNodes.item(0);

            // Find the <FileRef> element inside the <SampleRef>
            NodeList fileRefNodes = sampleRefElement.getElementsByTagName("FileRef");
            if (fileRefNodes != null && fileRefNodes.getLength() > 0) {
                Element fileRefElement = (Element) fileRefNodes.item(0);

                // Find the <RelativePath> element inside <FileRef>
                NodeList relativePathNodes = fileRefElement.getElementsByTagName("RelativePath");
                if (relativePathNodes != null && relativePathNodes.getLength() > 0) {
                    Element relativePathElement = (Element) relativePathNodes.item(0);

                    // Return the "Value" attribute of the <RelativePath> element
                    return relativePathElement.getAttribute("Value");
                }
            }
        }
        return null;  // Return null if RelativePath element isn't found
    }

    private String extractName(Element pluginElement) {
        NodeList plugNameNodes = pluginElement.getElementsByTagName("Name");
        if (plugNameNodes != null && plugNameNodes.getLength() > 0) {
            Element plugNameElement = (Element) plugNameNodes.item(0);
            return plugNameElement.getAttribute("Value");
        }
        return null;
    }


}
