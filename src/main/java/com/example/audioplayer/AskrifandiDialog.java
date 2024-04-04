package com.example.audioplayer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vinnsla.Askrifandi;

import java.io.IOException;

/**
 * AskrifandiDialog klasi
 */
public class AskrifandiDialog extends Dialog<Askrifandi> {
    public Button fxLoginButton;
    public Askrifandi askrifandi;
    public TextField fxNafnField;

    /**
     * Tómur ÁskrifandiDialog smiður
     */
    public AskrifandiDialog() {

    }

    /**
     * Aðferð virkjast þegar ýtt er á Skrá inn takkann. Virkjar heima-view.fxml
     */
    @FXML
    private void onLogin() {
        boolean loginSuccess;
        loginSuccess = !fxNafnField.getText().isEmpty();
        if (loginSuccess) {
            askrifandi = new Askrifandi(fxNafnField.getText());
            close();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/heima-view.fxml"));
            Parent root = loader.load();
            PlayerController controller = loader.getController();
            controller.setjaNotanda(askrifandi);
            Stage stage = (Stage) fxLoginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Heimaskjár");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
