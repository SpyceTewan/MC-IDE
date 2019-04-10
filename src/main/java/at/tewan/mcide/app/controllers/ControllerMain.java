package at.tewan.mcide.app.controllers;

import at.tewan.mcide.Resources;
import at.tewan.mcide.app.NewProjectDialog;
import at.tewan.mcide.project.Project;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {

    @FXML
    private SubScene assetsScene, functionScene, recipesScene, advancementsScene, loottablesScene, tagsScene;

    @FXML
    private AnchorPane assetsTab, functionTab, recipesTab, advancementsTab, loottablesTab, tagsTab;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assetsScene.widthProperty().bind(assetsTab.widthProperty());
        assetsScene.heightProperty().bind(assetsTab.heightProperty());

        functionScene.widthProperty().bind(functionTab.widthProperty());
        functionScene.heightProperty().bind(functionTab.heightProperty());

        recipesScene.widthProperty().bind(recipesTab.widthProperty());
        recipesScene.heightProperty().bind(recipesTab.heightProperty());

        advancementsScene.widthProperty().bind(advancementsTab.widthProperty());
        advancementsScene.heightProperty().bind(advancementsTab.heightProperty());

        loottablesScene.widthProperty().bind(loottablesTab.widthProperty());
        loottablesScene.heightProperty().bind(loottablesTab.heightProperty());

        tagsScene.widthProperty().bind(tagsTab.widthProperty());
        tagsScene.heightProperty().bind(tagsTab.heightProperty());

        try {

            assetsScene.setRoot(Resources.getFXML("assets"));
            functionScene.setRoot(Resources.getFXML("functions"));
            recipesScene.setRoot(Resources.getFXML("recipes"));
            advancementsScene.setRoot(Resources.getFXML("advancements"));
            loottablesScene.setRoot(Resources.getFXML("loottables"));
            tagsScene.setRoot(Resources.getFXML("tags"));

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void newproject() {
        new NewProjectDialog().show();
    }

    @FXML
    private void openproject() {

    }

    @FXML
    private void saveproject() {
        Project.save();
    }

    @FXML
    private void closeproject() {

    }

    @FXML
    private void projectsettings() {

    }

    @FXML
    private void settings() {

    }

    @FXML
    private void exit() {
        System.exit(-1);
    }
}
