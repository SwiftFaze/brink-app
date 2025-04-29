package com.brink.model.ui;

import com.brink.model.app.Project;
import com.brink.shared.JavaFxService;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbletonProjectVBoxView extends VBox {
    private static final Logger logger = LoggerFactory.getLogger(AbletonProjectVBoxView.class);

    public AbletonProjectVBoxView(Project project) {
        setSpacing(12);
        setStyle("-fx-padding: 15;");

        Label titleLabel = new Label("Ableton Project Info");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        TextField projectNameField = JavaFxService.createReadOnlyField("Project name: " + project.getName());
        TextField compatibleField = JavaFxService.createReadOnlyField("Is compatible: " + project.getCompatibility().isCompatible());
        TextField collectedField = JavaFxService.createReadOnlyField("Is collected: " + project.getCompatibility().isCollected());
        TextField creatorField = JavaFxService.createReadOnlyField("Version: " + project.getVersion());
//        TextField userListField = JavaFxService.createReadOnlyField("Version: " + project.getCollaborator());

        getChildren().addAll(
                titleLabel,
                projectNameField,
                compatibleField,
                collectedField,
                creatorField
        );

        getChildren().add(new AbletonTrackTableView(project.getTracksList()));
        getChildren().add(new AbletonDeviceTableView(project.getDeviceList()));
        getChildren().add(new AbletonAudioClipTableView(project.getAudioClipList()));
    }


}
