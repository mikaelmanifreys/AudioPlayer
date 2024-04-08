package com.example.audioplayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
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

public class NyLögController {

    @FXML
    private Button fxHeim;
    @FXML
    private Button fxPlayPause;
    @FXML
    private ProgressBar fxProgress;
    @FXML
    private Label fxLagNafn;
    @FXML
    private ImageView fxMynd;
    @FXML
    private ListView<Lag> fxListView;

    private MediaPlayer player;
    private Lagalisti lagalisti;
    private Lag lag;

    public ObservableList<Lag> listi = FXCollections.observableArrayList();


    public void initialize() {
        logALista();
        fxPlayPause.getStyleClass().add("button-play");
        fxListView.getItems().addAll(listi);

    }

    private ObservableList<Lag> getList() {
        return listi;
    }


    private void logALista(){
        listi.add(new Lag("/css/media/All IN.mp3", "ALL IN", 159, "/css/media/herra.jpeg"));
        listi.add(new Lag("/css/media/Skína.mp3", "Skína", 180, "/css/media/skina.jpeg"));
        listi.add(new Lag("/css/media/sod.mp3", "Sódóma", 234, "/css/media/salin.jpeg" ));
        listi.add(new Lag("/css/media/Fjöllin hafa vakað.mp3", "Fjöllin hafa vakað", 224, "/css/media/ego.jpeg"));
    }

    public void setValinnListi(Lagalisti listi) {
        this.lagalisti = listi;
        uppfaeraLista();
    }

    public void uppfaeraLista() {
        if (lagalisti != null && fxListView != null) {
            fxListView.setItems(getList());
        }
    }

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
            lag = fxListView.getItems().get(valinnIndex);
            fxLagNafn.setText(lag.getLagNafn());
            fxPlayPause.getStyleClass().removeAll("button-play");
            fxPlayPause.getStyleClass().add("button-pause");
            if (lag.getMyndNafn() != null) {
                setjaMynd(fxMynd, lag.getMyndNafn());

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
            fxProgress.setProgress(newValue.toSeconds() / lengd);
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
        setjaMynd(fxMynd, lag.getMyndNafn());
        if (player != null) {
            player.play();
        }
    }





    public void onHeim(){
        System.out.println("Heim");
        if (player != null) {
            player.stop();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/heima-view.fxml"));

            Stage stage = (Stage) fxHeim.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
