module com.qunxt.qudit {
    exports com.qunxt.qudit;

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    requires org.apache.commons.lang3;
    requires java.desktop;

    requires com.fasterxml.jackson.databind;
    requires lombok;

    opens com.qunxt.qudit to javafx.fxml;
}