package com.brink.shared;

import com.brink.model.app.AppSettings;
import com.brink.model.FileData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);


    public static String PLUGIN_FILE_NAME = "\\plugins.json";
    public static String REPO_COLLABORATOR_FOLDER_PATH = "\\brink\\collaborators\\";

    public static String createUserPluginFilePath(AppSettings appSettings, FileData fileData) {
        return fileData.getFolderPath() + REPO_COLLABORATOR_FOLDER_PATH + appSettings.getGitUsername() + PLUGIN_FILE_NAME;
    }

    public static LocalDateTime getFileLocalDateTime(File file) {
        try {
            Path path = file.toPath();
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            return LocalDateTime.ofInstant(attrs.creationTime().toInstant(), ZoneId.systemDefault());
        } catch (IOException e) {
            return null;
        }
    }

}
