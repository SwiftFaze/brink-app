module com.brink.brink {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires com.google.gson;
    requires java.xml.bind;
    requires com.fasterxml.jackson.dataformat.xml;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    opens com.brink.model.ableton to com.google.gson;

    exports com.brink.brink;
    opens com.brink.brink to javafx.fxml;
}
