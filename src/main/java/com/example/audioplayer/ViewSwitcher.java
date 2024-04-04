package com.example.audioplayer;

import javafx.stage.Stage;

/**
 * ViewSwitcher klasinn
 */
public class ViewSwitcher {
    private static Stage stage;

    /**
     * Skiptir um view
     *
     * @param view
     */
    public static void switchView(View view) {
        stage.setScene(view.getScene());
        stage.setTitle(view.getTitle());
        stage.show();
    }

    public static void main(String[] args) {

    }
}
