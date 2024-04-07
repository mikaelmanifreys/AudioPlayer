package com.example.audioplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import vinnsla.Askrifandi;
import vinnsla.Lagalisti;

import java.io.IOException;

/**
 * Player controller klasi. Fylgir með heima-view.fxml
 */
public class PlayerController {
    public ListView<Lagalisti> fxLagalistar;
    public Button fxLogInButton;
    public RadioButton fxIslenska;
    public RadioButton fxEnska;
    public boolean LoggedIn;

    public PlayerController() {

    }


    /**
     * Býr til notanda og setur í label.
     *
     * @param askrifandi Notandi forritsins
     */
    public void setjaNotanda(Askrifandi askrifandi) {
        fxLogInButton.setText(askrifandi.getNafn());
        LoggedIn = true;
    }

    /**
     * Setur upp lagalista listViewið
     */
    @FXML
    public void initialize() {
        fxLagalistar.getItems().clear();
        Lagalisti lagalisti1 = new Lagalisti("Lagalisti 1");
        Lagalisti lagalisti2 = new Lagalisti("Lagalisti 2");
        fxLagalistar.getItems().addAll(lagalisti1, lagalisti2);
        ToggleGroup tungumal = new ToggleGroup();
        fxEnska.setToggleGroup(tungumal);
        fxIslenska.setToggleGroup(tungumal);
    }


    /**
     * Aðferð virkjast þegar tvísmellt er á lagalista. Opnar og setur upp listi-view.fxml
     *
     * @param mouseEvent tvísmellt á lista
     * @throws IOException
     */
    @FXML
    private void onVeljaLista(MouseEvent mouseEvent) throws IOException {

        if (mouseEvent.getClickCount() == 2) {
            try {
                Lagalisti valinnListi = fxLagalistar.getSelectionModel().getSelectedItem();
                if (valinnListi != null) {
                    valinnListi.lesaLog();
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/listi-view.fxml"));
                Stage stage = (Stage) fxLagalistar.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.setTitle(valinnListi.toString());
                ListiController controller = loader.getController();
                controller.setValinnListi(valinnListi);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void LogInButtonClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/askrifandi-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            Stage stage = (Stage) fxLogInButton.getScene().getWindow();
            stage.setTitle("Áskrifandi");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isIslenskaSelected() {
        return fxIslenska.isSelected();
    }

    public void onIslenskaClicked(ActionEvent actionEvent) {
        if (!LoggedIn) {
            fxLogInButton.setText("Skrá Inn");
        }
    }


    public void onEnskaClicked(ActionEvent actionEvent) {
        if (!LoggedIn) {
            fxLogInButton.setText("Log In");
        }
    }
}


