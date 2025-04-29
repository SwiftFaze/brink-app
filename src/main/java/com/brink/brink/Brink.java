package com.brink.brink;

import com.brink.model.Collaborator;
import com.brink.model.FileData;
import com.brink.model.ableton.AbletonProject;
import com.brink.model.app.AppSettings;
import com.brink.model.app.Project;
import com.brink.model.ui.AbletonProjectVBoxView;
import com.brink.shared.FileService;
import com.brink.shared.ProjectService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Brink extends Application {
    private static final Logger logger = LoggerFactory.getLogger(Brink.class);

    private final double windowHeight = 1500;

    private final double windowWidth = 900;

    private final List<File> alsFiles = new ArrayList<>();
    private final TableView<File> projectFileList = new TableView<>();
    private final FileData selectedFileData = new FileData();
    private final VBox projectInfoView = new VBox();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final ProgressIndicator projectInfoLoadingSpinner = new ProgressIndicator();
    private final VBox projectInfoLoadingSpinnerOverlay = new VBox(projectInfoLoadingSpinner);
    private final VBox bottomProjectBar = new VBox();
    private final MenuBar appHeaderMenu = new MenuBar();
    private final VBox mainLayout = new VBox();
    private final Collaborator collaborator = new Collaborator();
    private AppSettings appSettings = new AppSettings();



//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
//    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("======= [BRINK APPLICATION STARTED] =======");
        primaryStage.setTitle("Brink");

        setAppHeaderMenuView();

        setProjectListView();

        setBottomBarView();

        setLoadingOverLayView();

        setProjectInfoView();

        StackPane rootPane = new StackPane();
        rootPane.getChildren().addAll(this.mainLayout);

        Scene scene = new Scene(rootPane, this.windowHeight, this.windowWidth);
        primaryStage.setScene(scene);
        primaryStage.show();

        updateProjectList();

        onProjectFileSelection();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("======= [BRINK APPLICATION EXITED] =======");
        }));
    }



    public static void main(String[] args) {
        launch();
    }




    private void addUserPluginDataToProject() {
        this.setProjectInfoLoading(true);

        Task<Collaborator> task = new Task<>() {
            @Override
            protected Collaborator call() {
                return new Collaborator(appSettings);
            }

            @Override
            protected void succeeded() {
                collaborator.save(appSettings, selectedFileData);
                setProjectInfoLoading(false);
            }

            @Override
            protected void failed() {
                setProjectInfoLoading(false);
            }
        };

        new Thread(task).start();
    }

    private void setProjectInfoView() {
        // SET PROJECT INFO VIEW
        StackPane infoPaneWithOverlay = new StackPane(this.projectInfoView, this.projectInfoLoadingSpinnerOverlay);
        SplitPane splitPane = new SplitPane(this.projectFileList, infoPaneWithOverlay);
        splitPane.setDividerPositions(0.25);
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        this.mainLayout.getChildren().addAll(this.appHeaderMenu, splitPane, this.bottomProjectBar);
    }

    private void onProjectFileSelection() {
        // PROJECT FILE SELECTION LISTENER
        projectFileList.getSelectionModel().selectedItemProperty().addListener((obs, previousProjectFile, selectedProjectFile) -> {
            if (selectedProjectFile != null) {
                setProjectInfoViewContents(null);

                setProjectInfoLoading(true);
                Task<Project> loadTask = new Task<>() {
                    // ON SELECTION MAPS ABLETON PROJECT
                    @Override
                    protected Project call() throws Exception {
                        Platform.runLater(() -> {
                            selectedFileData.setProjectName(selectedProjectFile.getName());
                            selectedFileData.setCreatedDate(FileService.getFileLocalDateTime(selectedProjectFile));
                            selectedFileData.setFolderPath(selectedProjectFile.getParent());
                        });
                        // RETURNS TO succeeded()
                        AbletonProject abletonProject = new AbletonProject(selectedProjectFile);
                        return ProjectService.convert2Project(abletonProject);
                    }

                    // ON SUCCESS LOADS VIEW
                    @Override
                    protected void succeeded() {
                        Project project = getValue();
                        Platform.runLater(() -> {
                            setProjectInfoViewContents(project);

                        });
                    }

                    @Override
                    protected void failed() {
                        getException().printStackTrace();
                        Platform.runLater(() -> {
                            projectInfoView.getChildren().setAll(new Label("Error loading project."));
                            setProjectInfoLoading(false);

                        });
                    }
                };

                new Thread(loadTask).start();
            }
        });
    }

    private void setProjectInfoViewContents(Project project) {
        if (project != null) {
            AbletonProjectVBoxView projectView = new AbletonProjectVBoxView(project);
            projectInfoView.getChildren().setAll(projectView);
        } else {
            projectInfoView.getChildren().setAll(new Label(""));
        }
        setProjectInfoLoading(false);
    }

    private void setBottomBarView() {
        // Folder selection controls
        Label pathLabel = new Label("Add user plugins");
        Button pluginButton = new Button("Add user plugins");

        pluginButton.setOnAction(event -> {
            this.addUserPluginDataToProject();

        });


        this.bottomProjectBar.getChildren().addAll(pathLabel, pluginButton);
        this.bottomProjectBar.setStyle("-fx-padding: 10;");
    }

    private void setProjectListView() {

        TableColumn<File, Boolean> gitCol = new TableColumn<>();
        gitCol.setPrefWidth(20);
        TableColumn<File, String> nameCol = new TableColumn<>("Project name");
        TableColumn<File, String> dateCol = new TableColumn<>("Creation date");

        projectFileList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // GIT COL, SETS TO GREEN DOT IF .git FOLDER EXISTS IN PROJECT FOLDER**/
        gitCol.setCellValueFactory(data -> {
            File parent = data.getValue().getParentFile();
            boolean hasGit = new File(parent, ".git").exists();
            return new SimpleBooleanProperty(hasGit);
        });
        gitCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic((empty || item == null) ? null : new Circle(5, item ? Color.LIMEGREEN : Color.RED));
            }
        });

        // PROJECT COL NAME
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        // DATE COL
        dateCol.setCellValueFactory(data -> {
            LocalDateTime created = FileService.getFileLocalDateTime(data.getValue());
            String formatted = (created != null) ? created.format(formatter) : "N/A";
            return new SimpleStringProperty(formatted);
        });

        // ADD COLS TO TABLE
        projectFileList.getColumns().setAll(gitCol, nameCol, dateCol);
    }

    private void setProjectInfoLoading(boolean state) {
        this.projectInfoLoadingSpinnerOverlay.setVisible(state);
        this.projectInfoLoadingSpinner.setVisible(state);
    }

    private void setLoadingOverLayView() {
        this.projectInfoLoadingSpinner.setMaxSize(100, 100);
        this.projectInfoLoadingSpinner.setVisible(false);
        this.projectInfoLoadingSpinnerOverlay.setAlignment(Pos.CENTER);
        this.projectInfoLoadingSpinnerOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");
        this.projectInfoLoadingSpinnerOverlay.setVisible(false);
    }

    private void updateProjectList() {
        File folder = new File(this.appSettings.getAbletonProjectFolderPath());
        if (folder.exists() && folder.isDirectory()) {
            alsFiles.clear();
            findAlsFiles(folder, alsFiles);
            projectFileList.getItems().setAll(alsFiles);
        }
    }


    private void findAlsFiles(File folder, List<File> alsFiles) {
        if (folder.getName().equalsIgnoreCase("Backup")) return;

        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".als"));
        if (files != null) {
            Collections.addAll(alsFiles, files);
        }

        File[] subDirs = folder.listFiles(File::isDirectory);
        if (subDirs != null) {
            for (File subDir : subDirs) {
                if (!subDir.getName().equalsIgnoreCase("Backup")) {
                    findAlsFiles(subDir, alsFiles);
                }
            }
        }
    }

    private void setAppHeaderMenuView() {
        Menu fileMenu = new Menu("File");
        MenuItem newProject = new MenuItem("Create new project");
        MenuItem quit = new MenuItem("Quit");
        MenuItem settings = new MenuItem("Settings");
        MenuItem about = new MenuItem("About");

        quit.setOnAction(e -> System.exit(0));

        newProject.setOnAction(e -> {
            openCreateProjectView();
        });

        settings.setOnAction(e -> {
            openSettingsView();
        });

        fileMenu.getItems().addAll(newProject, settings, about, quit);
        this.appHeaderMenu.getMenus().add(fileMenu);
    }

    private void openSettingsView() {
        Stage settingsStage = new Stage();
        settingsStage.setTitle("Settings");
        settingsStage.setResizable(false);

        // Fields
        TextField abletonProjectPathField = new TextField(this.appSettings.getAbletonProjectFolderPath());
        abletonProjectPathField.setPromptText("Ableton project path");

        TextField pluginFolderPathField = new TextField(this.appSettings.getAbletonProjectTemplateFilePath());
        pluginFolderPathField.setPromptText("Plugin folder path");

        TextField gitMainBranchNameField = new TextField(this.appSettings.getGitMainBranchName());
        gitMainBranchNameField.setPromptText("Git main branch name");

        TextField gitUserField = new TextField(this.appSettings.getGitUsername());
        gitUserField.setPromptText("Git username");

        TextField gitUserEmail = new TextField(this.appSettings.getGitEmail());
        gitUserEmail.setPromptText("Git email");

        PasswordField gitTokenField = new PasswordField();
        gitTokenField.setPromptText("Git token");
        gitTokenField.setText(this.appSettings.getGitToken());

        // File explorer buttons
        Button browseProjectBtn = new Button("Browse...");
        browseProjectBtn.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Ableton Project Folder");

            File current = new File(abletonProjectPathField.getText());
            if (current.exists() && current.isDirectory()) {
                directoryChooser.setInitialDirectory(current);
            }

            File selectedDir = directoryChooser.showDialog(settingsStage);
            if (selectedDir != null) {
                abletonProjectPathField.setText(selectedDir.getAbsolutePath());
            }
        });

        Button browseTemplateBtn = new Button("Browse...");
        browseTemplateBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Plugin Folder");

            File current = new File(pluginFolderPathField.getText());
            if (current.exists()) {
                fileChooser.setInitialDirectory(current.isDirectory() ? current : current.getParentFile());
            }

            File selectedFile = fileChooser.showOpenDialog(settingsStage);
            if (selectedFile != null) {
                pluginFolderPathField.setText(selectedFile.getAbsolutePath());
            }
        });

        // Buttons
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        saveButton.setOnAction(event -> {
            saveSettings(settingsStage, abletonProjectPathField, pluginFolderPathField, gitMainBranchNameField, gitUserField, gitUserEmail, gitTokenField);
        });

        cancelButton.setOnAction(event -> settingsStage.close());

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setVgap(10);
        grid.setHgap(10);

        // Ableton Project Path
        HBox abletonProjectPathFieldBox = new HBox(5, abletonProjectPathField, browseProjectBtn);
        HBox.setHgrow(abletonProjectPathField, Priority.ALWAYS);
        grid.add(new Label("Ableton Project Path:"), 0, 0);
        grid.add(abletonProjectPathFieldBox, 1, 0);

        // Ableton Template Path
        HBox abletonTemplatePathFieldBox = new HBox(5, pluginFolderPathField, browseTemplateBtn);
        HBox.setHgrow(pluginFolderPathField, Priority.ALWAYS);
        grid.add(new Label("Ableton Template Path:"), 0, 1);
        grid.add(abletonTemplatePathFieldBox, 1, 1);

        grid.add(new Label("Git Main branch name:"), 0, 2);
        grid.add(gitMainBranchNameField, 1, 2);
        GridPane.setHgrow(gitMainBranchNameField, Priority.ALWAYS);

        grid.add(new Label("Git Username:"), 0, 3);
        grid.add(gitUserField, 1, 3);
        GridPane.setHgrow(gitUserField, Priority.ALWAYS);

        grid.add(new Label("Git Email:"), 0, 4);
        grid.add(gitUserEmail, 1, 4);
        GridPane.setHgrow(gitUserEmail, Priority.ALWAYS);

        grid.add(new Label("Git Token:"), 0, 5);
        grid.add(gitTokenField, 1, 5);
        GridPane.setHgrow(gitTokenField, Priority.ALWAYS);

        HBox buttonBox = new HBox(10, saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        grid.add(buttonBox, 1, 6);
        GridPane.setHgrow(buttonBox, Priority.ALWAYS);

        Scene scene = new Scene(grid, 550, 280);
        settingsStage.setScene(scene);
        settingsStage.initModality(Modality.APPLICATION_MODAL);
        settingsStage.showAndWait();
    }

    private void saveSettings(Stage settingsStage, TextField abletonProjectPathField, TextField abletonTemplatePathField, TextField gitMainBranchNameField, TextField gitUserField, TextField getUserEmail, PasswordField gitTokenField) {
        this.appSettings = new AppSettings(
                abletonProjectPathField.getText(),
                abletonTemplatePathField.getText(),
                gitMainBranchNameField.getText(),
                gitUserField.getText(),
                getUserEmail.getText(),
                gitTokenField.getText());
        updateProjectList();
        settingsStage.close();
    }

    private void openCreateProjectView() {
        Stage projectStage = new Stage();
        projectStage.setTitle("New project");
        projectStage.setResizable(false);

        TextField abletonProjectName = new TextField();
        abletonProjectName.setPromptText("Project name");

        TextField repoUrl = new TextField();
        repoUrl.setPromptText("Git repository URL");

        // Buttons
        Button createButton = new Button("Create");
        Button cancelButton = new Button("Cancel");

        createButton.setOnAction(event -> {
            createProject(projectStage, abletonProjectName, repoUrl);
        });

        cancelButton.setOnAction(event -> projectStage.close());

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setVgap(10);
        grid.setHgap(10);

        HBox abletonProjectPathFieldBox = new HBox(5, abletonProjectName);
        HBox.setHgrow(abletonProjectName, Priority.ALWAYS);
        grid.add(new Label("Ableton Project name:"), 0, 0);
        grid.add(abletonProjectPathFieldBox, 1, 0);

        HBox repoUrlFieldBox = new HBox(5, repoUrl);
        HBox.setHgrow(repoUrl, Priority.ALWAYS);
        grid.add(new Label("Git repository URL:"), 0, 1);
        grid.add(repoUrlFieldBox, 1, 1);

        HBox buttonBox = new HBox(10, createButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        grid.add(buttonBox, 1, 2);
        GridPane.setHgrow(buttonBox, Priority.ALWAYS);

        Scene scene = new Scene(grid, 400, 150);
        projectStage.setScene(scene);
        projectStage.initModality(Modality.APPLICATION_MODAL);
        projectStage.showAndWait();
    }

    private void createProject(Stage projectStage, TextField abletonProjectNameField, TextField repoUrlField) {

        String projectName = abletonProjectNameField.getText().trim();
        if (projectName.isEmpty()) {
            logger.error("Project name cannot be empty.");
            return;
        }

        try {
            // Base directory where the project will be created
            File baseDir = new File(this.appSettings.getAbletonProjectFolderPath());
            File projectDir = new File(baseDir, projectName + " Project");
            if (!projectDir.exists()) {
                projectDir.mkdirs();
            }

            // Copy template .als file into the project folder
            Path templatePath = Path.of(this.appSettings.getAbletonProjectTemplateFilePath());
            Path destPath = Path.of(projectDir.getAbsolutePath(), projectName + ".als");
            Files.copy(templatePath, destPath, StandardCopyOption.REPLACE_EXISTING);

            // Create the script file
            File scriptFile;
            boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
            if (isWindows) {
                scriptFile = File.createTempFile("init_project", ".bat");
            } else {
                scriptFile = File.createTempFile("init_project", ".sh");
                scriptFile.setExecutable(true);
            }

            String lineSep = isWindows ? "\r\n" : "\n";

            String repoUrl = repoUrlField.getText().trim();
            String httpsUrlWithToken = repoUrl.replace(
                    "https://",
                    "https://" + this.appSettings.getGitUsername() + ":" + this.appSettings.getGitToken() + "@"
            );

            StringBuilder script = new StringBuilder();
            script.append("git --version").append(lineSep);
            script.append("git init").append(lineSep);
            script.append("git config --global user.name \"").append(this.appSettings.getGitUsername()).append("\"").append(lineSep);
            script.append("git config --global user.email \"").append(this.appSettings.getGitEmail()).append("\"").append(lineSep);
            script.append("echo # ").append(projectName).append(" > README.md").append(lineSep);
            script.append("git lfs install").append(lineSep);
            script.append("git lfs track \"*.als\" \"*.mp3\" \"*.wav\" \"*.flac\" \"*.aac\" \"*.ogg\" \"*.m4a\" \"*.alac\" \"*.aiff\"").append(lineSep);
            script.append("git add .").append(lineSep);
            script.append("git commit --author=\"")
                    .append(this.appSettings.getGitUsername())
                    .append(" <").append(this.appSettings.getGitEmail())
                    .append(">\" -m \"Brink project init\"").append(lineSep);
            script.append("git branch -M ").append(this.appSettings.getGitMainBranchName()).append(lineSep);
            script.append("git remote add origin ").append(httpsUrlWithToken).append(lineSep);
            script.append("git push -u origin ").append(this.appSettings.getGitMainBranchName()).append(lineSep);


            // Write script file
            try (FileWriter fw = new FileWriter(scriptFile)) {
                fw.write(script.toString());
            }

            // Execute script in the project directory
            ProcessBuilder builder = new ProcessBuilder(scriptFile.getAbsolutePath());
            builder.directory(projectDir);
            builder.inheritIO();
            Process process = builder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                logger.info("Project created and initialized successfully.");
            } else {
                logger.error("Error initializing project. Exit code: {}", exitCode);
            }

            scriptFile.deleteOnExit();
            projectStage.close();
            this.updateProjectList();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

