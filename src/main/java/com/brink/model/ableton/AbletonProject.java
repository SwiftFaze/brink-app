package com.brink.model.ableton;


import com.brink.model.Collaborator;
import com.brink.model.Compatibility;
import com.brink.model.FileData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.io.StringReader;

@XmlRootElement(name = "Ableton")
@XmlAccessorType(XmlAccessType.FIELD)
public class AbletonProject {
    private static final Logger logger = LoggerFactory.getLogger(AbletonProject.class);

    @XmlAttribute(name = "SchemaChangeCount")
    private int schemaChangeCount;

    @XmlAttribute(name = "Creator")
    private String version;

    @XmlElement(name = "LiveSet")
    private AbletonLiveSet liveSet;

    private FileData fileData;

    private Compatibility compatibility;

    private Collaborator collaborator;

    public AbletonProject() {
    }

    public AbletonProject(File alsFile) {
        try {
            this.fileData = new FileData(alsFile);


            JAXBContext jaxbContext = JAXBContext.newInstance(AbletonProject.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            AbletonProject loadedProject = (AbletonProject) jaxbUnmarshaller.unmarshal(new StringReader(fileData.getXmlAlsFile()));

            this.schemaChangeCount = loadedProject.schemaChangeCount;
            this.version = loadedProject.version;
            this.liveSet = loadedProject.liveSet;
            this.compatibility = new Compatibility(this);

        } catch (JAXBException e) {
            logger.error("Error reading Ableton project file", e);
        }
    }


    public int getSchemaChangeCount() {
        return schemaChangeCount;
    }

    public void setSchemaChangeCount(int schemaChangeCount) {
        this.schemaChangeCount = schemaChangeCount;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public AbletonLiveSet getLiveSet() {
        return liveSet;
    }

    public void setLiveSet(AbletonLiveSet liveSet) {
        this.liveSet = liveSet;
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
}
