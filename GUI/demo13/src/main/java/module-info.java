module com.example.demo13 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.gemsfx;
    requires fontawesomefx;
    requires com.azure.cosmos;
    requires com.google.gson;
    requires io.netty.codec.http2;
    requires tyrus.standalone.client;
    requires signalr;

    opens com.example.demo13 to javafx.fxml, com.fasterxml.jackson.databind;
    exports com.example.demo13;
}