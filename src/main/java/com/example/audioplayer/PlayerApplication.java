package com.example.audioplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * PlayerApplication klasi
 */
public class PlayerApplication extends Application {
    /**
     * Ræsir forritið og setur upp áskrifendaviðmótið
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/heima-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 320, 340);
            stage.setTitle("Heimaskjár");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
