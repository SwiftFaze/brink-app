package com.brink.model.ui;

import com.brink.model.app.Device;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AbletonDeviceTableView extends VBox {
    private static final Logger logger = LoggerFactory.getLogger(AbletonDeviceTableView.class);

    public AbletonDeviceTableView(List<Device> deviceList) {
        setSpacing(12);
        setStyle("-fx-padding: 10;");

        TableView<Device> deviceTable = createDeviceTable();

        deviceTable.getItems().addAll(deviceList);

        getChildren().add(deviceTable);
    }

    private static TableView<Device> createDeviceTable() {
        TableView<Device> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Device, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlugin().getName()));

        TableColumn<Device, String> formatCol = new TableColumn<>("Format");
        formatCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlugin().getFormat().getName()));

        TableColumn<Device, Boolean> isNativeCol = new TableColumn<>("Native");
        isNativeCol.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isNative()));

        table.getColumns().addAll(nameCol, formatCol, isNativeCol);

        return table;
    }


}
