module com.example.demo13 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.gemsfx;
    requires fontawesomefx;
    requires com.azure.cosmos;
    requires com.google.gson;

    opens com.example.demo13 to javafx.fxml;
    exports com.example.demo13;
}