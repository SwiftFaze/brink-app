package com.brink.shared;

import com.brink.model.AppSettings;
import com.brink.model.ProjectSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);


    public static String PLUGIN_FILE_NAME = "\\plugins.json";
    public static String REPO_COLLABORATOR_FOLDER_PATH = "\\brink\\collaborators\\";


    public static String createUserPluginFilePath(AppSettings appSettings, ProjectSummary projectSummary) {
        return projectSummary.getFolderPath() + REPO_COLLABORATOR_FOLDER_PATH + appSettings.getGitUsername() + PLUGIN_FILE_NAME;
    }


}
