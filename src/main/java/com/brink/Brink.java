package com.brink;import com.brink.model.AppSettings;import com.brink.model.ProjectSummary;import com.brink.model.ableton.AbletonProject;import com.brink.model.ui.AbletonProjectVBoxView;import com.brink.shared.AbletonProjectService;import javafx.application.Application;import javafx.application.Platform;import javafx.beans.property.SimpleBooleanProperty;import javafx.beans.property.SimpleStringProperty;import javafx.concurrent.Task;import javafx.geometry.Insets;import javafx.geometry.Pos;import javafx.scene.Scene;import javafx.scene.control.*;import javafx.scene.layout.*;import javafx.scene.paint.Color;import javafx.scene.shape.Circle;import javafx.stage.DirectoryChooser;import javafx.stage.FileChooser;import javafx.stage.Modality;import javafx.stage.Stage;import java.io.*;import java.nio.charset.StandardCharsets;import java.nio.file.Files;import java.nio.file.Path;import java.nio.file.StandardCopyOption;import java.nio.file.attribute.BasicFileAttributes;import java.time.LocalDateTime;import java.time.ZoneId;import java.time.format.DateTimeFormatter;import java.util.ArrayList;import java.util.List;import java.util.zip.GZIPInputStream;public class Brink extends Application {    private final double windowHeight = 1500;    private final double windowWidth = 900;    private final List<File> alsFiles = new ArrayList<>();    private AppSettings appSettings = new AppSettings();    private final TableView<File> projectFileList = new TableView<>();    private final ProjectSummary selectedProjectSummary = new ProjectSummary();    private final VBox projectInfoView = new VBox();    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");    private final ProgressIndicator projectInfoLoadingSpinner = new ProgressIndicator();    private final VBox projectInfoLoadingSpinnerOverlay = new VBox(projectInfoLoadingSpinner);    private final VBox bottomProjectBar = new VBox();    private final MenuBar appHeaderMenu = new MenuBar();    private final VBox mainLayout = new VBox();    @Override    public void start(Stage primaryStage) {        primaryStage.setTitle("Brink");        setAppHeaderMenuView();        setProjectListView();        setBottomBarView();        setLoadingOverLayView();        setProjectInfoView();        StackPane rootPane = new StackPane();        rootPane.getChildren().addAll(this.mainLayout);        Scene scene = new Scene(rootPane, this.windowHeight, this.windowWidth);        primaryStage.setScene(scene);        primaryStage.show();        //        updateProjectList();        onProjectSelection();    }    private void setProjectInfoView() {        // SET PROJECT INFO VIEW        StackPane infoPaneWithOverlay = new StackPane(this.projectInfoView, this.projectInfoLoadingSpinnerOverlay);        SplitPane splitPane = new SplitPane(this.projectFileList, infoPaneWithOverlay);        splitPane.setDividerPositions(0.25);        VBox.setVgrow(splitPane, Priority.ALWAYS);        this.mainLayout.getChildren().addAll(this.appHeaderMenu, splitPane, this.bottomProjectBar);    }    private void onProjectSelection() {        // Selection listener        projectFileList.getSelectionModel().selectedItemProperty().addListener((obs, previousProjectFile, selectedProjectFile) -> {            if (selectedProjectFile != null) {                setProjectInfoLoading(true);                Task<AbletonProject> loadTask = new Task<>() {                    @Override                    protected AbletonProject call() throws Exception {                        Platform.runLater(() -> {                            selectedProjectSummary.setProjectName(selectedProjectFile.getName());                            selectedProjectSummary.setCreatedDate(getFileLocalDateTime(selectedProjectFile));                        });                        String xml = decompressGzipFileToString(selectedProjectFile);                        return AbletonProjectService.convert2AbletonProject(xml);                    }                    @Override                    protected void succeeded() {                        AbletonProject project = getValue();                        Platform.runLater(() -> {                            setProjectInfoViewContents(project);                        });                    }                    @Override                    protected void failed() {                        getException().printStackTrace();                        Platform.runLater(() -> {                            projectInfoView.getChildren().setAll(new Label("Error loading project."));                            setProjectInfoLoading(false);                        });                    }                };                new Thread(loadTask).start();            }        });    }    private void setProjectInfoViewContents(AbletonProject project) {        if (project != null) {            AbletonProjectVBoxView projectView = new AbletonProjectVBoxView(project);            projectInfoView.getChildren().setAll(projectView);        } else {            projectInfoView.getChildren().setAll(new Label("Failed to load project."));        }        setProjectInfoLoading(false);    }    private void setBottomBarView() {        // Folder selection controls        Label pathLabel = new Label("Enter folder path:");        Button loadButton = new Button("Load .als Files");//        this.bottomProjectBar = new VBox(10, pathLabel, projectFolderPath, loadButton);        this.bottomProjectBar.getChildren().addAll(pathLabel, loadButton);        this.bottomProjectBar.setStyle("-fx-padding: 10;");    }    private void setProjectListView() {        TableColumn<File, Boolean> gitCol = new TableColumn<>();        gitCol.setPrefWidth(20);        TableColumn<File, String> nameCol = new TableColumn<>("Project name");        TableColumn<File, String> dateCol = new TableColumn<>("Creation date");        projectFileList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);        // GIT COL, SETS TO GREEN DOT IF .git FOLDER EXISTS IN PROJECT FOLDER**/        gitCol.setCellValueFactory(data -> {            File parent = data.getValue().getParentFile();            boolean hasGit = new File(parent, ".git").exists();            return new SimpleBooleanProperty(hasGit);        });        gitCol.setCellFactory(col -> new TableCell<>() {            @Override            protected void updateItem(Boolean item, boolean empty) {                super.updateItem(item, empty);                setGraphic((empty || item == null) ? null : new Circle(5, item ? Color.LIMEGREEN : Color.RED));            }        });        // PROJECT COL NAME        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));        // DATE COL        dateCol.setCellValueFactory(data -> {            LocalDateTime created = getFileLocalDateTime(data.getValue());            String formatted = (created != null) ? created.format(formatter) : "N/A";            return new SimpleStringProperty(formatted);        });        // ADD COLS TO TABLE        projectFileList.getColumns().setAll(gitCol, nameCol, dateCol);    }    private void setProjectInfoLoading(boolean state) {        this.projectInfoLoadingSpinnerOverlay.setVisible(state);        this.projectInfoLoadingSpinner.setVisible(state);    }    private void setLoadingOverLayView() {        this.projectInfoLoadingSpinner.setMaxSize(100, 100);        this.projectInfoLoadingSpinner.setVisible(false);        this.projectInfoLoadingSpinnerOverlay.setAlignment(Pos.CENTER);        this.projectInfoLoadingSpinnerOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");        this.projectInfoLoadingSpinnerOverlay.setVisible(false);    }    private void updateProjectList() {        File folder = new File(this.appSettings.getAbletonProjectFolderPath());        if (folder.exists() && folder.isDirectory()) {            alsFiles.clear();            findAlsFiles(folder, alsFiles);            projectFileList.getItems().setAll(alsFiles);        }    }    private static LocalDateTime getFileLocalDateTime(File file) {        try {            Path path = file.toPath();            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);            return LocalDateTime.ofInstant(attrs.creationTime().toInstant(), ZoneId.systemDefault());        } catch (IOException e) {            return null;        }    }    private String decompressGzipFileToString(File file) throws IOException {        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(file));             InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8);             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {            StringBuilder output = new StringBuilder();            String line;            while ((line = bufferedReader.readLine()) != null) {                output.append(line).append("\n");            }            return output.toString();        }    }    private void findAlsFiles(File folder, List<File> alsFiles) {        if (folder.getName().equalsIgnoreCase("Backup")) return;        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".als"));        if (files != null) {            for (File file : files) alsFiles.add(file);        }        File[] subDirs = folder.listFiles(File::isDirectory);        if (subDirs != null) {            for (File subDir : subDirs) {                if (!subDir.getName().equalsIgnoreCase("Backup")) {                    findAlsFiles(subDir, alsFiles);                }            }        }    }    private void setAppHeaderMenuView() {        Menu fileMenu = new Menu("File");        MenuItem newProject = new MenuItem("Create new project");        MenuItem quit = new MenuItem("Quit");        MenuItem settings = new MenuItem("Settings");        MenuItem about = new MenuItem("About");        quit.setOnAction(e -> System.exit(0));        newProject.setOnAction(e -> {            openCreateProjectView();        });        settings.setOnAction(e -> {            openSettingsView();        });        fileMenu.getItems().addAll(newProject, settings, about, quit);        this.appHeaderMenu.getMenus().add(fileMenu);    }    private void openSettingsView() {        Stage settingsStage = new Stage();        settingsStage.setTitle("Settings");        settingsStage.setResizable(false);        // Fields        TextField abletonProjectPathField = new TextField(this.appSettings.getAbletonProjectFolderPath());        abletonProjectPathField.setPromptText("Ableton project path");        TextField abletonTemplatePathField = new TextField(this.appSettings.getAbletonProjectTemplateFilePath());        abletonTemplatePathField.setPromptText("Ableton template path");        TextField gitUserField = new TextField(this.appSettings.getGitUsername());        gitUserField.setPromptText("Git username");        TextField gitUserEmail = new TextField(this.appSettings.getGitEmail());        gitUserEmail.setPromptText("Git email");        PasswordField gitTokenField = new PasswordField();        gitTokenField.setPromptText("Git token");        gitTokenField.setText(this.appSettings.getGitToken());        // File explorer buttons        Button browseProjectBtn = new Button("Browse...");        browseProjectBtn.setOnAction(event -> {            DirectoryChooser directoryChooser = new DirectoryChooser();            directoryChooser.setTitle("Select Ableton Project Folder");            File current = new File(abletonProjectPathField.getText());            if (current.exists() && current.isDirectory()) {                directoryChooser.setInitialDirectory(current);            }            File selectedDir = directoryChooser.showDialog(settingsStage);            if (selectedDir != null) {                abletonProjectPathField.setText(selectedDir.getAbsolutePath());            }        });        Button browseTemplateBtn = new Button("Browse...");        browseTemplateBtn.setOnAction(event -> {            FileChooser fileChooser = new FileChooser();            fileChooser.setTitle("Select Ableton Template File");            fileChooser.getExtensionFilters().add(                    new FileChooser.ExtensionFilter("Ableton Live Set (*.als)", "*.als")            );            File current = new File(abletonTemplatePathField.getText());            if (current.exists()) {                fileChooser.setInitialDirectory(current.isDirectory() ? current : current.getParentFile());            }            File selectedFile = fileChooser.showOpenDialog(settingsStage);            if (selectedFile != null) {                abletonTemplatePathField.setText(selectedFile.getAbsolutePath());            }        });        // Buttons        Button saveButton = new Button("Save");        Button cancelButton = new Button("Cancel");        saveButton.setOnAction(event -> {            saveSettings(settingsStage, abletonProjectPathField, abletonTemplatePathField, gitUserField, gitUserEmail, gitTokenField);        });        cancelButton.setOnAction(event -> settingsStage.close());        // Layout        GridPane grid = new GridPane();        grid.setPadding(new Insets(15));        grid.setVgap(10);        grid.setHgap(10);        // Ableton Project Path        HBox abletonProjectPathFieldBox = new HBox(5, abletonProjectPathField, browseProjectBtn);        HBox.setHgrow(abletonProjectPathField, Priority.ALWAYS);        grid.add(new Label("Ableton Project Path:"), 0, 0);        grid.add(abletonProjectPathFieldBox, 1, 0);        // Ableton Template Path        HBox abletonTemplatePathFieldBox = new HBox(5, abletonTemplatePathField, browseTemplateBtn);        HBox.setHgrow(abletonTemplatePathField, Priority.ALWAYS);        grid.add(new Label("Ableton Template Path:"), 0, 1);        grid.add(abletonTemplatePathFieldBox, 1, 1);        grid.add(new Label("Git Username:"), 0, 2);        grid.add(gitUserField, 1, 2);        GridPane.setHgrow(gitUserField, Priority.ALWAYS);        grid.add(new Label("Git Email:"), 0, 3);        grid.add(gitUserEmail, 1, 3);        GridPane.setHgrow(gitUserEmail, Priority.ALWAYS);        grid.add(new Label("Git Token:"), 0, 4);        grid.add(gitTokenField, 1, 4);        GridPane.setHgrow(gitTokenField, Priority.ALWAYS);        HBox buttonBox = new HBox(10, saveButton, cancelButton);        buttonBox.setAlignment(Pos.CENTER_RIGHT);        grid.add(buttonBox, 1, 5);        GridPane.setHgrow(buttonBox, Priority.ALWAYS);        Scene scene = new Scene(grid, 550, 280);        settingsStage.setScene(scene);        settingsStage.initModality(Modality.APPLICATION_MODAL);        settingsStage.showAndWait();    }    private void saveSettings(Stage settingsStage, TextField abletonProjectPathField, TextField abletonTemplatePathField, TextField gitUserField, TextField getUserEmail, PasswordField gitTokenField) {        this.appSettings = new AppSettings(                abletonProjectPathField.getText(),                abletonTemplatePathField.getText(),                gitUserField.getText(),                getUserEmail.getText(),                gitTokenField.getText());        updateProjectList();        settingsStage.close();    }    private void openCreateProjectView() {        Stage projectStage = new Stage();        projectStage.setTitle("New project");        projectStage.setResizable(false);        TextField abletonProjectName = new TextField();        abletonProjectName.setPromptText("Project name");        TextField repoUrl = new TextField();        repoUrl.setPromptText("Git repository URL");        // Buttons        Button createButton = new Button("Create");        Button cancelButton = new Button("Cancel");        createButton.setOnAction(event -> {            createProject(projectStage, abletonProjectName, repoUrl);        });        cancelButton.setOnAction(event -> projectStage.close());        // Layout        GridPane grid = new GridPane();        grid.setPadding(new Insets(15));        grid.setVgap(10);        grid.setHgap(10);        HBox abletonProjectPathFieldBox = new HBox(5, abletonProjectName);        HBox.setHgrow(abletonProjectName, Priority.ALWAYS);        grid.add(new Label("Ableton Project name:"), 0, 0);        grid.add(abletonProjectPathFieldBox, 1, 0);        HBox repoUrlFieldBox = new HBox(5, repoUrl);        HBox.setHgrow(repoUrl, Priority.ALWAYS);        grid.add(new Label("Git repository URL:"), 0, 1);        grid.add(repoUrlFieldBox, 1, 1);        HBox buttonBox = new HBox(10, createButton, cancelButton);        buttonBox.setAlignment(Pos.CENTER_RIGHT);        grid.add(buttonBox, 1, 2);        GridPane.setHgrow(buttonBox, Priority.ALWAYS);        Scene scene = new Scene(grid, 400, 150);        projectStage.setScene(scene);        projectStage.initModality(Modality.APPLICATION_MODAL);        projectStage.showAndWait();    }    private void createProject(Stage projectStage, TextField abletonProjectName, TextField repoUrl) {        projectStage.close();        String projectName = abletonProjectName.getText().trim();        if (projectName.isEmpty()) {            System.out.println("Project name cannot be empty.");            return;        }        try {            // Base directory where the project will be created            File baseDir = new File(this.appSettings.getAbletonProjectFolderPath());            File projectDir = new File(baseDir, projectName + " Project");            if (!projectDir.exists()) {                projectDir.mkdirs();            }            // 🔁 Copy template .als file into the project folder            Path templatePath = Path.of(this.appSettings.getAbletonProjectTemplateFilePath());            Path destPath = Path.of(projectDir.getAbsolutePath(), projectName + ".als");            Files.copy(templatePath, destPath, StandardCopyOption.REPLACE_EXISTING);            // Create the script file            File scriptFile;            boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");            if (isWindows) {                scriptFile = File.createTempFile("init_project", ".bat");            } else {                scriptFile = File.createTempFile("init_project", ".sh");                scriptFile.setExecutable(true);            }            String lineSep = isWindows ? "\r\n" : "\n";            StringBuilder script = new StringBuilder();            script.append("git --version").append(lineSep);            script.append("git init").append(lineSep);            script.append("git config --global user.name \"").append(this.appSettings.getGitUsername()).append("\"").append(lineSep);            script.append("git config --global user.email \"").append(this.appSettings.getGitEmail()).append("\"").append(lineSep);            script.append("echo # ").append(projectName).append(" > README.md").append(lineSep);            script.append("git lfs install").append(lineSep);            script.append("git lfs track \"*.als\" \"*.mp3\" \"*.wav\" \"*.flac\" \"*.aac\" \"*.ogg\" \"*.m4a\" \"*.alac\" \"*.aiff\"").append(lineSep);            script.append("git add .").append(lineSep);            script.append("git commit --author=\"").append(this.appSettings.getGitUsername())                    .append(" <").append(this.appSettings.getGitEmail()).append(">\" -m \"Brink project init\"").append(lineSep);            script.append("git branch -M main").append(lineSep);            script.append("git remote add origin ").append(repoUrl.getText().trim()).append(lineSep);            script.append("git push -u origin main").append(lineSep);            // Write script file            try (FileWriter fw = new FileWriter(scriptFile)) {                fw.write(script.toString());            }            // Execute script in the project directory            ProcessBuilder builder = new ProcessBuilder(scriptFile.getAbsolutePath());            builder.directory(projectDir);            builder.inheritIO();            Process process = builder.start();            int exitCode = process.waitFor();            if (exitCode == 0) {                System.out.println("✅ Project created and initialized successfully.");            } else {                System.out.println("❌ Error initializing project. Exit code: " + exitCode);            }            scriptFile.deleteOnExit();        } catch (Exception e) {            e.printStackTrace();        }    }    public static void main(String[] args) {        launch(args);    }}