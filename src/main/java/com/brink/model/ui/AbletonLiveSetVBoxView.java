package com.brink.model.ui;import com.brink.model.ableton.AbletonLiveSet;import javafx.scene.control.Label;import javafx.scene.layout.VBox;public class AbletonLiveSetVBoxView extends VBox {    public AbletonLiveSetVBoxView(AbletonLiveSet liveSet) {        setSpacing(12);        setStyle("-fx-padding: 10;");        // Set dynamic title for the Ableton Live Set        Label title = new Label("Tracks");        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");        getChildren().add(title);        // Create the AbletonTrackTableView and pass the audio tracks        if (liveSet.getTracks() != null) {            AbletonTrackTableView trackTableView = new AbletonTrackTableView(liveSet.getTracks());            getChildren().add(trackTableView);        }    }}