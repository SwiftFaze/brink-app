package com.brink.model.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AppSettings {
    private static final Logger logger = LoggerFactory.getLogger(AppSettings.class);
    private final String filePath = "brink.settings";
    private String abletonProjectFolderPath;
    private String pluginFolderPath;
    private String gitMainBranchName;
    private String gitUsername;
    private String gitEmail;
    private String gitToken;

    public AppSettings() {
        this.abletonProjectFolderPath = "R:\\MUSIC\\ABLETON PROJECTS\\11\\2025";
        this.pluginFolderPath = "R:\\MUSIC\\PLUGINS";
        this.gitMainBranchName = "main";
        this.gitUsername = "rwoolley";
        this.gitEmail = "rjjw.dev@gmail.com";
        this.gitToken = "1234";
        load();
    }

    public AppSettings(String abletonProjectFolderPath, String pluginFolderPath, String gitMainBranchName, String gitUsername, String gitEmail, String gitToken) {
        this.abletonProjectFolderPath = abletonProjectFolderPath;
        this.pluginFolderPath = pluginFolderPath;
        this.gitUsername = gitUsername;
        this.gitMainBranchName = gitMainBranchName;
        this.gitEmail = gitEmail;
        this.gitToken = gitToken;
        save();
    }


    private void load() {
        File settingsFile = new File(filePath);
        if (settingsFile.exists()) {
            try (FileInputStream inputStream = new FileInputStream(settingsFile)) {
                Properties properties = new Properties();
                properties.load(inputStream);
                this.abletonProjectFolderPath = properties.getProperty("abletonProjectFolderPath", this.abletonProjectFolderPath);
                this.pluginFolderPath = properties.getProperty("abletonProjectTemplateFilePath", this.pluginFolderPath);
                this.gitUsername = properties.getProperty("gitMainBranchName", this.gitMainBranchName);
                this.gitUsername = properties.getProperty("gitUsername", this.gitUsername);
                this.gitEmail = properties.getProperty("gitEmail", this.gitEmail);
                this.gitToken = properties.getProperty("gitToken", this.gitToken);
            } catch (IOException e) {
                logger.error("Error reading the settings file: {}", e.getMessage());
            }
        } else {
            save();
        }
    }

    public void save() {
        Properties properties = new Properties();
        properties.setProperty("abletonProjectFolderPath", this.abletonProjectFolderPath);
        properties.setProperty("pluginFolderPath", this.pluginFolderPath);
        properties.setProperty("gitMainBranchName", this.gitMainBranchName);
        properties.setProperty("gitUsername", this.gitUsername);
        properties.setProperty("gitEmail", this.gitEmail);
        properties.setProperty("gitToken", this.gitToken);

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            properties.store(outputStream, "Brink settings");
        } catch (IOException e) {
            logger.error("Error saving the settings file: {}", e.getMessage());
        }
    }


    public String getAbletonProjectFolderPath() {
        return abletonProjectFolderPath;
    }

    public void setAbletonProjectFolderPath(String abletonProjectFolderPath) {
        this.abletonProjectFolderPath = abletonProjectFolderPath;
    }

    public String getAbletonProjectTemplateFilePath() {
        return pluginFolderPath;
    }

    public void setAbletonProjectTemplateFilePath(String abletonProjectTemplateFilePath) {
        this.pluginFolderPath = abletonProjectTemplateFilePath;
    }

    public String getGitUsername() {
        return gitUsername;
    }

    public void setGitUsername(String gitUsername) {
        this.gitUsername = gitUsername;
    }

    public String getGitToken() {
        return gitToken;
    }

    public void setGitToken(String gitToken) {
        this.gitToken = gitToken;
    }

    public String getGitEmail() {
        return gitEmail;
    }

    public void setGitEmail(String gitEmail) {
        this.gitEmail = gitEmail;
    }

    public String getGitMainBranchName() {
        return gitMainBranchName;
    }

    public void setGitMainBranchName(String gitMainBranchName) {
        this.gitMainBranchName = gitMainBranchName;
    }

    public String getSettingsFilePath() {
        return filePath;
    }

    public String getPluginFolderPath() {
        return pluginFolderPath;
    }

    public void setPluginFolderPath(String pluginFolderPath) {
        this.pluginFolderPath = pluginFolderPath;
    }


}
