package com.brink.model;

import com.brink.shared.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.zip.GZIPInputStream;

public class FileData {
    private static final Logger logger = LoggerFactory.getLogger(FileData.class);

    private String projectName;
    private File alsFile;
    private String xmlAlsFile;
    private String folderPath;
    private LocalDateTime createdDate;

    public FileData() {
    }

    public FileData(File alsFile) {
        this.projectName = alsFile.getName().replace(".als", "");
        this.alsFile = alsFile;
        this.xmlAlsFile = decompressGzipFileToString(alsFile);
        this.folderPath = alsFile.getParent();
        this.createdDate = FileService.getFileLocalDateTime(alsFile);

    }


    private String decompressGzipFileToString(File file) {
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(file));
             InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                output.append(line).append("\n");
            }
            return output.toString();
        } catch (IOException e) {
            logger.error("Cannot decompress gzip file", e);
            return null;
        }
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public File getAlsFile() {
        return alsFile;
    }

    public void setAlsFile(File alsFile) {
        this.alsFile = alsFile;
    }

    public String getXmlAlsFile() {
        return xmlAlsFile;
    }

    public void setXmlAlsFile(String xmlAlsFile) {
        this.xmlAlsFile = xmlAlsFile;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
