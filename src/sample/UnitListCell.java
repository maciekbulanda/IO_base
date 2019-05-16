package sample;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class UnitListCell extends ListCell<Unit> {

    VBox content;
    Label line1;
    Label line2;
    public UnitListCell() {
        super();
        line1 = new Label();
        line2 = new Label();
        line1.setFont(Font.font(null, FontWeight.NORMAL,15));
        line2.setFont(Font.font("Verdana", FontPosture.ITALIC,9));
        line2.setMaxWidth(171);
        content = new VBox();
        content.getChildren().addAll(line1, line2);
    }
    @Override
    protected void updateItem(Unit item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            line1.setText(item.getUnitName());
            line2.setText(item.getUnitDescription());
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }
}
