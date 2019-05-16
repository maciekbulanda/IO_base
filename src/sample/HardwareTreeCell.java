package sample;

import javafx.scene.control.TreeCell;

public class HardwareTreeCell extends TreeCell<HardwareItem> {
    @Override
    protected void updateItem(HardwareItem item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setText(item.getName());
        } else {
            setText(null);
        }
    }
}
