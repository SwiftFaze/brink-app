package com.brink.model.ableton;


import com.brink.model.Collaborator;
import com.brink.model.Compatibility;
import com.brink.model.FileData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class AbletonProject {
    private static final Logger logger = LoggerFactory.getLogger(AbletonProject.class);

    @Expose
    @SerializedName("SchemaChangeCount")
    private int schemaChangeCount;

    @Expose
    @SerializedName("Creator")
    private String version;

    @Expose
    @SerializedName("LiveSet")
    private AbletonLiveSet liveSet;

    private FileData fileData;

    private Compatibility compatibility;

    private Collaborator collaborator;

    public AbletonProject() {

    }

    public AbletonProject(File alsFile) {
        try {
            this.fileData = new FileData(alsFile);

            // Convert the XML string to JSON first (or directly parse if in JSON format)
            String json = convertXmlToJson(fileData.getXmlAlsFile());

            // Use Gson to parse the JSON into your AbletonProject object
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            AbletonProject loadedProject = gson.fromJson(json, AbletonProject.class);
System.out.println(loadedProject.schemaChangeCount);
            // Set your fields
            this.schemaChangeCount = loadedProject.schemaChangeCount;
            this.version = loadedProject.version;
            this.liveSet = loadedProject.liveSet;
            this.compatibility = new Compatibility(this);

        } catch (JsonSyntaxException e) {
            logger.error("Error reading Ableton project file", e);
        }
    }

    public String convertXmlToJson(String xml) {
        try {
            // Create a Jackson XML Mapper
            XmlMapper xmlMapper = new XmlMapper();

            // Deserialize the XML into a map
            Object obj = xmlMapper.readValue(xml, Object.class);

            // Convert that object to a JSON string
            ObjectMapper jsonMapper = new ObjectMapper();
            return jsonMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("Error converting XML to JSON", e);
            return null;
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
