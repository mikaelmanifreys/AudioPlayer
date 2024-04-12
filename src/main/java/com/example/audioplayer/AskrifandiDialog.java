package com.example.audioplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    public RadioButton fxIslenska3;
    public RadioButton fxEnska3;
    public PasswordField fxLykilorð;

    /**
     * Tómur ÁskrifandiDialog smiður
     */
    public AskrifandiDialog() {
        
    }

    public void initialize() {
        ToggleGroup tungumal = new ToggleGroup();
        fxEnska3.setToggleGroup(tungumal);
        fxIslenska3.setToggleGroup(tungumal);
    }

    /**
     * Aðferð virkjast þegar ýtt er á Skrá inn takkann. Virkjar heima-view.fxml
     */
    @FXML
    private void onLogin() {
        boolean loginSuccess;
        loginSuccess = !fxNafnField.getText().isEmpty() && !fxLykilorð.getText().isEmpty();
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


    public void onIslenskaClicked3(ActionEvent actionEvent) {
        fxLoginButton.setText("Skrá Inn");
        fxNafnField.setPromptText("Notendanafn");
        fxLykilorð.setPromptText("Lykilorð");


    }

    public void onEnskaClicked3(ActionEvent actionEvent) {
        fxLoginButton.setText("Log In");
        fxNafnField.setPromptText("Username");
        fxLykilorð.setPromptText("Password");

    }
}
