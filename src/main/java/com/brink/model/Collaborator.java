package com.brink.model;

import com.brink.model.app.AppSettings;
import com.brink.model.app.Plugin;
import com.brink.shared.FileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Collaborator {

    private static final Logger logger = LoggerFactory.getLogger(Collaborator.class);

    private String username;

    private List<Plugin> pluginList = new ArrayList<>();

    public Collaborator() {
    }

    public Collaborator(AppSettings appSettings) {
        this.username = appSettings.getGitUsername();
        this.pluginList = this.getUserPlugins(appSettings);
    }

    private List<Plugin> getUserPlugins(AppSettings appSettings) {
        logger.debug("Collecting user plugin List...");

        File pluginFolder = new File(appSettings.getPluginFolderPath());
        if (!pluginFolder.exists() || !pluginFolder.isDirectory()) {
            logger.error("Plugin folder does not exist or is not a directory.");
            new ArrayList<>();
        }

        List<Plugin> userPluginList = new ArrayList<>();

        // ITERATES ALL FOLDERS OF USER PLUGIN FOLDER
        try (Stream<Path> paths = Files.walk(Paths.get(appSettings.getPluginFolderPath()))) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> PluginFormat.isSupportedPluginFormat(path.getFileName().toString()))
                    .forEach(path -> {
                        // IF SUPPORTED PLUGIN EXTENSION, CREATES PLUGIN
                        Plugin plugin = new Plugin(path.getFileName().toString());
                        userPluginList.add(plugin);
                    });

            logger.debug("Collected user plugin list");
            return userPluginList;
        } catch (IOException e) {
            logger.error("Error scanning plugin folder: {}", e.getMessage(), e);
        }


        return new ArrayList<>();
    }


    public void save(AppSettings appSettings, FileData fileData) {
        logger.debug("Saving collaborator file...");

        String filePath = FileService.createUserPluginFilePath(appSettings, fileData);

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filePath);

        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, this);
            logger.info("Saved to: {}", filePath);
        } catch (IOException e) {
            logger.error("Error saving collaborator data to JSON file: {}", e.getMessage());
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Plugin> getPluginList() {
        return pluginList;
    }

    public void setPluginList(List<Plugin> pluginList) {
        this.pluginList = pluginList;
    }
}


