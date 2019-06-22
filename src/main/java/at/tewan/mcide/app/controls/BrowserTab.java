package at.tewan.mcide.app.controls;

import at.tewan.mcide.app.subapp.BrowserApplication;
import at.tewan.mcide.app.subapps.BrowserConfig;
import at.tewan.mcide.project.Project;
import at.tewan.mcide.util.Icons;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.File;

public class BrowserTab extends Tab {

    private TreeView<String> treeView;
    private TreeItem<String> treeRoot;
    private BrowserApplication app;
    private BrowserConfig config;
    private String namespace;

    private BrowserTab(BrowserApplication app, String namespace) {
        this.app = app;
        this.config = app.getConfig();
        this.namespace = namespace;
        this.treeView = new TreeView<>();
        this.treeRoot = new TreeItem<>();

        treeView.setRoot(treeRoot);
        treeView.setShowRoot(false);

        setContent(treeView);
        setText(namespace);
        setClosable(false);

        // Das alles brauch ich um ein Click Event vom TreeItem abzufangen...
        treeView.setCellFactory(tree -> {
            BrowserCell cell = new BrowserCell();
            cell.setOnMouseClicked(event -> {
                if(!event.isSecondaryButtonDown()) {
                    if (cell.getTreeItem() instanceof BrowserFileItem)
                        app.openFile(((BrowserFileItem) cell.getTreeItem()).getFile());
                }
            });

            return cell;
        });
    }

    public static BrowserTab[] getBrowserTabsForAllNamespaces(BrowserApplication app) {
        String[] namespaces = Project.getNamespaces();
        BrowserTab[] tabs = new BrowserTab[namespaces.length];

        for(int i = 0; i < namespaces.length; i++) {
            tabs[i] = new BrowserTab(app, namespaces[i]);
        }

        return tabs;
    }

    public void refresh(String rootDir) {

        // Alten Baum löschen bevor man einen neuen erstellt
        treeRoot.getChildren().clear();


        refresh(new File(rootDir + namespace + "/" + config.getSearchedFolder() + "/"), treeRoot);
    }

    /**
     *
     * Jeder mag Rekursionen.
     * @param currentDir Wird für die Rekursion benötigt
     */
    private void refresh(File currentDir, TreeItem<String> currentItem) {

        // Jetziges Directory hinzufügen
        TreeItem<String> currentDirItem = new TreeItem(currentDir.getName());
        currentDirItem.setExpanded(true);
        currentItem.getChildren().add(currentDirItem);

        for(File currentFile : currentDir.listFiles()) {

            if(currentFile.isDirectory()) {
                refresh(currentFile, currentDirItem);
            } else if(currentFile.isFile()){
                BrowserFileItem item = new BrowserFileItem(currentFile.getName(), Icons.getIcon("file"));
                item.setFile(currentFile);
                currentDirItem.getChildren().add(item);
            }
        }

    }

    //////////////////////////////////////////////
    //                                          //
    //             GETTER & SETTER              //
    //                                          //
    //////////////////////////////////////////////

    public TreeView getTreeView() {
        return treeView;
    }

    //////////////////////////////////////////////
    //                                          //
    //               INNER CASSES               //
    //                                          //
    //////////////////////////////////////////////

    public class BrowserFileItem extends TreeItem<String> {

        private File file;

        public BrowserFileItem(String value) {
            super(value);
        }

        public BrowserFileItem(String value, Node graphic) {
            super(value, graphic);
        }

        public void setFile(File file) {
            this.file = file;
        }

        public File getFile() {
            return file;
        }
    }

    private class BrowserCell extends TreeCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if(empty)
                setText(null);
            else
                setText(item);

            ContextMenu menu = new ContextMenu();

            MenuItem itemDelete = new MenuItem("Delete");
            MenuItem itemRename = new MenuItem("Rename");

            itemDelete.setOnAction(event -> delete());
            itemRename.setOnAction(event -> rename());

            menu.getItems().addAll(itemDelete, itemRename);

            if(getTreeItem() instanceof BrowserFileItem) {

            } else {
                Menu itemNew = new Menu("New");
                MenuItem itemNewFolder = new MenuItem("Folder");

                // TODO: Filetypes wie .mcfunction .json erstellen können
                MenuItem itemNewFile = new MenuItem("File");

                itemNewFolder.setOnAction(event -> newFolder());
                itemNewFile.setOnAction(event -> newFile());

                itemNew.getItems().addAll(itemNewFolder, itemNewFile);
                menu.getItems().addAll(itemNew);

            }

            setContextMenu(menu);


        }

        private void delete() {

        }

        private void rename() {
            TextInputDialog renameDialog = new TextInputDialog();
            renameDialog.setTitle("Rename");
            renameDialog.getEditor().setPromptText("New file name");

            String input = renameDialog.showAndWait().get();
            if(!input.isEmpty()) {

            }
        }

        private void newFolder() {

        }

        private void newFile() {

        }

    }
}