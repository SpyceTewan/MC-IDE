package at.tewan.mcide.app.controls;

import at.tewan.mcide.mcfunction.Syntax;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.io.*;
import java.nio.file.Files;

/**
 * Ein Tab für TabPane mit integrierter FeaturedCodeArea und Datei lade / speichersystem
 *
 *
 */
public class CodeTab extends Tab {

    private FeaturedCodeArea area;
    private File file;
    private TabPane tabPane;

    private boolean saved;

    private String fileName;

    private StackPane container;

    public CodeTab(TabPane pane, File initialFile, Syntax syntax) {
        area = new FeaturedCodeArea(syntax);
        file = initialFile;
        fileName = file.getName();
        tabPane = pane;

        container = new StackPane();
        container.setAlignment(Pos.TOP_LEFT);
        container.getChildren().add(area);
        setContent(container);

        // Datei in die FeaturedCodeArea laden.
        if(initialFile.isFile()) {
            setText(initialFile.getName());

            try {
                String content = "";
                for(String a : Files.readAllLines(initialFile.toPath())) {
                    content += a + System.lineSeparator();
                }
                area.appendText(content);
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        }

        // Wenn der Text verändert wird, soll die Datei als nicht gespeichert markiert werden.
        area.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
            unsave()
        );

        // Event listener für Key combinations
        area.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

            // Datei Speichern
            if(event.isControlDown() && event.getCode() == KeyCode.S) {
                save();
                event.consume();
            }

        });

        // Tab aus der TabPane löschen wenn geschlossen wird
        setOnClosed((event -> {
            tabPane.getTabs().remove(this);
        }));

        // Code completions
        CompletionPane completionPane = new CompletionPane(area);

        container.getChildren().add(completionPane);

        area.setOnMousePressed(event -> {
            completionPane.hide();
        });
    }

    /**
     * Datei wird als nicht gespeichert markiert.
     */
    private void unsave() {
        if(saved) {
            saved = false;
            setText("*" + fileName);
        }
    }

    private void save() {

        // Wenn die Datei bereits gespeichert ist, muss sie nicht erneut beschrieben werden.
        if(!saved) {

            try {
                FileWriter writer = new FileWriter(file);
                writer.write(area.getText());
                writer.close();

                setText(fileName);

                System.out.println("Saved file " + file.toString());
                saved = true;
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        }

    }

    public File getFile() {
        return file;
    }

    public FeaturedCodeArea getArea() {
        return area;
    }


}
