package com.example.audioplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import vinnsla.Lag;
import vinnsla.Lagalisti;

import java.io.IOException;
import java.util.Objects;

/**
 * ListiController klasi. Stýrir listi-view.fxml
 */
public class ListiController {
    public Button fxPlayPause;
    public ProgressBar fxProgressBar;
    public ImageView myndReitur;
    public Label lagNafnReitur;
    public Button fxHeimButton;
    public RadioButton fxIslenska2;
    public RadioButton fxEnska2;
    public CheckBox fxShuffle;
    private Lag validLag;
    @FXML
    private ListView<Lag> fxListView;
    private MediaPlayer player;
    private Lagalisti valinnListi;
    public PlayerController playerController;


    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    /**
     * Setur upp listi-view.fxml.
     */
    public void initialize() {
        fxIslenska2.setSelected(true);
        ToggleGroup tungumal = new ToggleGroup();
        fxEnska2.setToggleGroup(tungumal);
        fxIslenska2.setToggleGroup(tungumal);
        fxPlayPause.getStyleClass().add("button-play");

    }

    /**
     * Lætur vita hvaða listi var valinn
     *
     * @param listi Valinn listi
     */
    public void setValinnListi(Lagalisti listi) {
        this.valinnListi = listi;
        uppfaeraLista();
    }

    /**
     * Setur upp ListViewið með lögunum í valda listanum
     */
    public void uppfaeraLista() {
        if (valinnListi != null && fxListView != null) {
            fxListView.setItems(valinnListi.getListi());
        }
    }

    /**
     * Aðferð virkjast þegar smellt er á lag í listanum.
     *
     * @param mouseEvent tvísmellt á lag
     */
    @FXML
    public void onValidLag(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            veljaLag();
            spilaLag();
        }
    }

    /**
     * Aðferð virkjast þegar smellt er á lag í listanum og onValidLag kallar á þessa aðferð
     */
    public void veljaLag() {
        int valinnIndex = fxListView.getSelectionModel().getSelectedIndex();
        if (valinnIndex >= 0) {
            validLag = fxListView.getItems().get(valinnIndex);
            lagNafnReitur.setText(validLag.getLagNafn());
            fxPlayPause.getStyleClass().removeAll("button-play");
            fxPlayPause.getStyleClass().add("button-pause");
            if (validLag.getMyndNafn() != null) {
                setjaMynd(myndReitur, validLag.getMyndNafn());

            }
        }
    }

    /**
     * Aðferð sem virkjast þegar smellt er á play/pause takkann. Stoppar/byrjar lagið
     *
     * @param actionEvent smellt á play/pause takkann
     */
    @FXML
    public void onPlayPause(ActionEvent actionEvent) {
        if (player != null) {
            if (player.getStatus() == MediaPlayer.Status.PLAYING) {
                fxPlayPause.getStyleClass().removeAll("button-pause");
                fxPlayPause.getStyleClass().add("button-play");
                player.pause();
            } else if (player.getStatus() == MediaPlayer.Status.PAUSED || player.getStatus() == MediaPlayer.Status.STOPPED) {
                fxPlayPause.getStyleClass().removeAll("button-play");
                fxPlayPause.getStyleClass().add("button-pause");
                player.play();
            }
        }
    }

    /**
     * Aðferð virkjast þegar smellt er á Heim takkann. Fer með notanda á Heimaskjá(heima-view.fxml)
     *
     * @param actionEvent smellt á Heim takka
     */
    public void onHeim(ActionEvent actionEvent) {
        if (player != null) {
            player.stop();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/heima-view.fxml"));
            Stage stage = (Stage) fxHeimButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Spilar lag sem ýtt var á
     */
    public void spilaLag() {
        if (fxListView.getSelectionModel().getSelectedItem() != null) {
            Lag validLag = fxListView.getSelectionModel().getSelectedItem();
            setjaPlayer(validLag);
        }


    }

    /**
     * Setur mynd á myndreit sem passar með laginu sem spilað er.
     *
     * @param myndReitur Reitur fyrir mynd með laginu
     * @param nafnMynd   nafn jpg skráar sem fylgir með laginu
     */
    public void setjaMynd(ImageView myndReitur, String nafnMynd) {
        Image mynd = new Image(Objects.requireNonNull(getClass().getResourceAsStream(nafnMynd)));
        myndReitur.setImage(mynd);
    }

    /**
     * Setur upp spilara fyrir lag sem á að spila
     *
     * @param lag lag sem á að spila
     */
    public void setjaPlayer(Lag lag) {
        if (lag != null) {
            String hljodskraSlod = getClass().getResource(lag.getHljodskraNafn()).toString();
            Media media = new Media(hljodskraSlod);
            if (player != null) {
                player.stop();
            }
            player = new MediaPlayer(media);
            player.setOnReady(() -> player.play());
        }

        player.currentTimeProperty().addListener((observable, old, newValue) -> {
            assert lag != null;
            double lengd = lag.getLengd();
            fxProgressBar.setProgress(newValue.toSeconds() / lengd);
        });


        assert lag != null;
        player.setStopTime(Duration.seconds(lag.getLengd()));

        player.setOnEndOfMedia(this::naestaLag);
    }

    /**
     * Sér til þess að þegar að lag klárast að þá spilast næsta lag
     */
    public void naestaLag() {
        int currentIndex = fxListView.getSelectionModel().getSelectedIndex();
        int newIndex = currentIndex + 1;
        if (newIndex >= fxListView.getItems().size()) {
            newIndex = 0;
        }
        fxListView.getSelectionModel().select(newIndex);
        Lag naestaLag = fxListView.getItems().get(newIndex);
        setjaPlayer(naestaLag);
        setjaMynd(myndReitur, validLag.getMyndNafn());
        if (player != null) {
            player.play();
        }
    }

    public boolean isIslenskaSelected() {
        return playerController.isIslenskaSelected();
    }

    public void onEnskaClicked2(ActionEvent actionEvent) {
        fxHeimButton.setText("Home");
    }

    public void onIslenskaClicked2(ActionEvent actionEvent) {
        fxHeimButton.setText("Heim");

    }

    public void onShuffleClicked(ActionEvent actionEvent) {
        int currentIndex = fxListView.getSelectionModel().getSelectedIndex();
        int newIndex = currentIndex + 1;
        if (newIndex >= fxListView.getItems().size()) {
            newIndex = 0;
        }
        fxListView.getSelectionModel().select(newIndex);
        Lag naestaLag = fxListView.getItems().get(newIndex);
        setjaPlayer(naestaLag);
        setjaMynd(myndReitur, validLag.getMyndNafn());
        if (player != null) {
            player.play();
        }
    }
}
