package com.brink.model.ui;

import com.brink.model.app.Track;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AbletonTrackTableView extends VBox {
    private static final Logger logger = LoggerFactory.getLogger(AbletonTrackTableView.class);

    public AbletonTrackTableView(List<Track> trackList) {
        setSpacing(12);
        setStyle("-fx-padding: 10;");

        TableView<Track> trackTable = createAudioTrackTable();

        trackTable.getItems().addAll(trackList);

        getChildren().add(trackTable);
    }

    private static TableView<Track> createAudioTrackTable() {
        // Create the TableView for AbletonAudioTrack
        TableView<Track> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        // Name Column
        TableColumn<Track, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        // IsFrozen Column
        TableColumn<Track, Boolean> isFrozen = new TableColumn<>("Frozen");
        isFrozen.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isFrozen()));


        // Add the columns to the table
        table.getColumns().addAll(nameCol, isFrozen);
        return table;
    }


}
