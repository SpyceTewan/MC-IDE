package at.tewan.mcide.app.factories;

import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

public final class ListViewDragDrop extends ListView<String> {

    private boolean drag = true;
    private boolean drop = true;
    private boolean removeOnDrag = true;

    private int itemLimit = -1;

    public ListViewDragDrop() {

        // Blauer Rand an
        setOnDragEntered(event -> {
            if(acceptDrop(event))
                getStyleClass().add("drag-over");
        });

        // Blauer Rand aus
        setOnDragExited(event -> {
            if(acceptDrop(event))
                getStyleClass().remove("drag-over");
        });

        // Accepted Transfer Modes zurückgeben
        setOnDragOver(event -> {
            if(acceptDrop(event))
                event.acceptTransferModes(TransferMode.ANY);
            else
                event.acceptTransferModes(TransferMode.NONE);
        });

        setOnDragDropped(event -> {

            if(acceptDrop(event)) {

                if(getItems().size() <= itemLimit || itemLimit < 0) {
                    String text = event.getDragboard().getString();

                    getItems().add(text);
                    getItems().sort(String::compareTo);

                    event.setDropCompleted(true);
                }

            }

        });

        // TODO: Remove On Drag funktioniert nicht
        setRemoveOnDrag(isRemoveOnDrag());
    }

    private boolean acceptDrop(DragEvent event) {
        boolean temp = true;

        if(event.getGestureSource() instanceof ListCellDraggable) {
            ListCellDraggable cell = (ListCellDraggable) event.getGestureSource();
            temp = cell.getListView() != this;

        }

        return event.getDragboard().hasString() && isDrop() && temp;
    }

    // ========================================================
    //                      GETTER SETTER
    // ========================================================

    public void setItemLimit(int itemLimit) {
        this.itemLimit = itemLimit;
    }

    public int getItemLimit() {
        return itemLimit;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
    }

    public void setDrop(boolean drop) {
        this.drop = drop;
    }

    public void setRemoveOnDrag(boolean removeOnDrag) {
        this.removeOnDrag = removeOnDrag;
        setCellFactory(view -> new ListCellDraggable(removeOnDrag));


    }

    public boolean isDrag() {
        return drag;
    }

    public boolean isDrop() {
        return drop;
    }

    public boolean isRemoveOnDrag() {
        return removeOnDrag;
    }
}
