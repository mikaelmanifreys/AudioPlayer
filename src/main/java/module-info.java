module com.example.audioplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.audioplayer to javafx.fxml;
    exports com.example.audioplayer;
}
