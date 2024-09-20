package org.example.gui;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileChooserBox {
    private final String title;
    private final boolean save;

    public FileChooserBox(String title, boolean save) {
        this.title = title;
        this.save = save;
    }

    public File show(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Game save (*.bin)", "*.bin"),
                new FileChooser.ExtensionFilter("All files", "*.*")
        );

        if(save) {
            return fileChooser.showSaveDialog(stage);
        }
        return fileChooser.showOpenDialog(stage);
    }

    public static FileChooserBox of(String title, boolean save) {
        return new FileChooserBox(title, save);
    }
}
