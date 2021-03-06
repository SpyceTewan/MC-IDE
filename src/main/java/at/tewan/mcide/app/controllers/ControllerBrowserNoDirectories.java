package at.tewan.mcide.app.controllers;

import at.tewan.mcide.project.Project;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Wie at.tewan.mcide.app.controllers.ControllerBrowser aber ohne directory tree
 *
 * */
public abstract class ControllerBrowserNoDirectories implements Initializable {

    ControllerBrowserNoDirectories(String rootName, boolean packType) {
        this.rootName = rootName;
        this.packType = packType;
    }

    private String rootName;
    private File rootDir;
    private boolean packType;

    static final boolean DATAPACK = true;
    static final boolean RESOURCEPACK = false;

    @FXML
    private Accordion browser;


    public void initialize(URL location, ResourceBundle resources) {
        refresh();
    }

    @FXML
    private void refresh() {

        if(packType == DATAPACK && Project.getCurrentProject().getProjectRoot() != null) {
            rootDir = Project.getCurrentProjectDatapackDir();
        } else {
            rootDir = Project.getCurrentProjectResourceDir();
        }

        browser.getPanes().clear();

        for(String namespace : Project.getNamespaces()) {
            TitledPane pane = new TitledPane();
            ListView list = new ListView();

            File dir = new File(rootDir.toString() + "/" + namespace + "/" + rootName);
            for(File f : dir.listFiles()) {
                list.getItems().add(f.getName());
            }

            pane.setText(namespace);
            pane.setContent(list);
            list.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
                try {
                    File f = new File(dir + "/" + newValue);
                    System.out.println(f);
                    openFile(f);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }));

            browser.getPanes().add(pane);
        }
    }

    protected abstract void openFile(File file) throws IOException;
}
