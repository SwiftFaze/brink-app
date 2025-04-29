package com.brink.shared;

import javafx.scene.control.TextField;

public class JavaFxService {


    public static TextField createReadOnlyField(String text) {
        TextField field = new TextField(text);
        field.setEditable(false);
        field.setFocusTraversable(false);
        field.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        return field;
    }

}
