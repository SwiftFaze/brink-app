module com.brink.brink {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires com.fasterxml.jackson.dataformat.xml;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires jakarta.xml.bind;
    requires com.google.gson;
    requires jakarta.activation;

    exports com.brink.brink;
    exports com.brink.model;
    opens com.brink.brink to javafx.fxml;
    opens com.brink.model to jakarta.xml.bind;
    opens com.brink.model.ableton to jakarta.xml.bind;
}
