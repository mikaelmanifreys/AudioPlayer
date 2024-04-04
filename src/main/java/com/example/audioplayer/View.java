package com.example.audioplayer;

import javafx.scene.Scene;

/**
 * View klasi
 */
public class View {
    private Scene scene;
    private String title;

    /**
     * View smiður
     *
     * @param scene Senan
     * @param title Titill senunar
     */
    public View(Scene scene, String title) {
        this.scene = scene;
        this.title = title;
    }

    public static void main(String[] args) {

    }

    /**
     * @return titlinum sem á að setja á topp gluggans
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return Senunni
     */
    public Scene getScene() {
        return scene;
    }
}
