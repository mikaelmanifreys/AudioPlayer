package vinnsla;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

/**
 * Lagalisti klasi
 */
public class Lagalisti {
    public ObservableList<Lag> listi = FXCollections.observableArrayList();
    private String nafn;

    public Lagalisti(String nafn) {
        this.nafn = nafn;
    }

    /**
     * Skilar nafni lagalista toString svo það sjáist á lista
     *
     * @return nafn lagalista
     */
    @Override
    public String toString() {
        return nafn;
    }

    /**
     * @return lagalista
     */
    public ObservableList<Lag> getListi() {
        return listi;
    }

    /**
     * Les inn lögin á Lagalista, hérna er hægt að bæta við nýjum lögum.
     *
     * @throws IOException
     */
    public void lesaLog() throws IOException {
        if (this.nafn.equals("Lagalisti 1")) {
            listi.add(new Lag("/css/media/Minning.mp3", "Minning", 249, "/css/media/BjörgvinHalldórsson.jpg"));
            listi.add(new Lag("/css/media/ÉgséAkureyri.mp3", "Ég sé Akureyri", 250, "/css/media/ÓskarPétursson.jpg"));
            listi.add(new Lag("/css/media/Pabbiþarfaðvinna.mp3", "Pabbi þarf að vinna", 239, "/css/media/Baggalútur.jpg"));
        } else if (this.nafn.equals("Lagalisti 2")) {
            listi.add(new Lag("/css/media/Viðstöndumhérenn.mp3", "Við stöndum hér enn", 234, "/css/media/LjótuHálfvitarnir.jpg"));
            listi.add(new Lag("/css/media/Lalalagið.mp3", "Lala lagið", 271, "/css/media/Hvanndalsbræður.jpg"));
        } else if (this.nafn.equals("Lagalisti 3")) {
            listi.add(new Lag("/css/media/AllIN.mp3", "ALL IN", 159, "/css/media/herra.jpg"));
            listi.add(new Lag("/css/media/Skína.mp3", "Skína", 180, "/css/media/skina.jpeg"));
        }
    }
}
