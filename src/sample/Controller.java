package sample;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Controller {

    private Stage stage;
    ArrayList<Unit> unitsList;
    ArrayList<HardwareItem> mainHardwareList = new ArrayList<>();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private MenuItem exitMenuItem;

    @FXML
    private TextField unitNameText,unitNameDetails,unitDescriptionDetails;

    @FXML
    private ListView<Unit> unitListView;

    @FXML
    private ListView<HardwareItem> konfCabListView;
    @FXML
    private ListView<HardwareItem> konfModListView;

    @FXML VBox inOutsVbox;

    @FXML
    private void initialize() {
        exitMenuItem.setOnAction(e -> stage.close());
        unitListView.getSelectionModel().selectedItemProperty().addListener((e,oldVal,newVal)->changeSelection(oldVal,newVal));
        unitNameText.textProperty().addListener((e, oldVal, newVal)-> textPropertyChanged(oldVal, newVal));
        unitListView.setCellFactory(unitListView -> new UnitListCell());
        configTreeView.setCellFactory(configTreeView -> new HardwareTreeCell());
        konfCabListView.getItems().addListener(new ListChangeListener<HardwareItem>() {
            @Override
            public void onChanged(Change<? extends HardwareItem> change) {
                System.out.println(change);
                fillTreeView();
            }
        });
        konfCabListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<HardwareItem>() {
            @Override
            public String toString(HardwareItem cabinet) {
                return cabinet.getName();
            }

            @Override
            public HardwareItem fromString(String s) {
                HardwareItem item = konfCabListView.getSelectionModel().getSelectedItem();
                item.setName(s);
                return item;
            }
        }));
        konfCabListView.setOnEditCommit(cabinetEditEvent -> {
            konfCabListView.getItems().set(cabinetEditEvent.getIndex(), cabinetEditEvent.getNewValue());
        });
        konfModListView.getItems().addListener(new ListChangeListener<HardwareItem>() {
            @Override
            public void onChanged(Change<? extends HardwareItem> change) {
                System.out.println(change);
                fillTreeView();
            }
        });
        konfCabListView.getSelectionModel().selectedItemProperty().addListener((e, oldVal, newVal)->konfCabChangeSelection(oldVal, newVal));
        konfModListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<HardwareItem>() {
            @Override
            public String toString(HardwareItem hardwareItem) {
                return hardwareItem.getName();
            }

            @Override
            public HardwareItem fromString(String s) {
                HardwareItem item = konfModListView.getSelectionModel().getSelectedItem();
                item.setName(s);
                return item;
            }
        }));
    }
    @FXML
    private void addButtonAction() {
        Unit nowy = new Unit(unitNameText.getText().toUpperCase());
        unitsList.add(nowy);
        unitListView.getItems().add(nowy);
        unitNameText.setText("");
    }

    @FXML
    private void removeButtonAction() {
        List<Unit> toRemove = unitListView.getSelectionModel().getSelectedItems();
        unitsList.removeAll(toRemove);
        unitListView.getItems().removeAll(toRemove);
    }

    @FXML private void loadButtonAction() {
        unitListView.getItems().clear();
        if (unitsList != null) {
            unitsList.clear();
        } else {
            unitsList = new ArrayList<>();
        }
        unitsList.addAll(DataHandler.readData("plik.db"));
        Collections.sort(unitsList);
        unitListView.getItems().addAll(FXCollections.observableList(unitsList));
    }

    @FXML private void saveButtonAction() {
        DataHandler.writeData("plik.db", unitsList);
    }

    @FXML private TreeView<HardwareItem> configTreeView;

    private void changeSelection(Unit oldValue,Unit newValue) {
        if (newValue != null) {
            unitNameDetails.setText(newValue.getUnitName());
            unitDescriptionDetails.setText(newValue.getUnitDescription());
            setLayout(newValue.getInOuts());
        } else {
            unitNameDetails.setText("");
            unitDescriptionDetails.setText("");
            inOutsVbox.getChildren().clear();
        }
    }

    private void textPropertyChanged(String oldValue, String newValue) {
        unitListView.getItems().clear();
        unitListView.getItems().addAll(DataHandler.filterArrayList(unitsList, newValue));
    }

    private void setLayout (ArrayList<InOut> inOuts) {
        inOutsVbox.getChildren().clear();
        for (InOut inOut : inOuts) {
            addRow(inOutsVbox, inOut);
        }
        Button addEmptyRowButton = new Button("+");
        addEmptyRowButton.setOnAction(e->addRow(inOutsVbox,null));
        inOutsVbox.getChildren().add(addEmptyRowButton);
    }

    private void addRow(VBox vBox, InOut inOut) {

        int rowsCount = vBox.getChildren().size();
        CheckBox ioSelected = new CheckBox();
        TextField ioNameText = new TextField();
        TextField ioDescText = new TextField();
        ComboBox<String> ioType = new ComboBox<>();
        ComboBox<HardwareItem> ioCabinetCB = new ComboBox<>();
        ComboBox<String> ioModule = new ComboBox<>();
        ComboBox<String> ioChannel = new ComboBox<>();
        ioNameText.setPromptText("Nazwa");
        ioNameText.setPrefWidth(80.0);
        ioDescText.setPromptText("Opis...");
        ioDescText.setPrefWidth(200.0);
        ioType.getItems().addAll("DI", "DO", "AI", "AO", "F-DI", "F-DO");
        ioType.setPrefWidth(75.0);
        ioCabinetCB.setPrefWidth(140.0);
        ioCabinetCB.getItems().addAll(mainHardwareList);
        ioModule.setPrefWidth(100.0);
        ioModule.setPrefWidth(70.0);
        ioChannel.setPrefWidth(70.0);
        HBox.setHgrow(ioDescText, Priority.SOMETIMES);
        if (inOut != null) {
            ioNameText.setText(inOut.getInOutName());
            ioDescText.setText(inOut.getInOutDesc());
            ioType.setValue(inOut.getInOutType());
        }
        HBox row = new HBox(ioSelected, ioNameText, ioDescText, ioType, ioCabinetCB, ioModule, ioChannel);

        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(2.0);
        vBox.getChildren().add(rowsCount>0?rowsCount-1:0,row);
    }

    private void updateUnit (Unit unit) {
        unit.setUnitName(unitNameDetails.getText().toUpperCase());
        unit.setUnitDescription(unitDescriptionDetails.getText());
        ArrayList<Node> rows = new ArrayList<>(inOutsVbox.getChildren());
        unit.getInOuts().clear();
        for (int i = 0; i < (rows.size() - 1); i++) {
            InOut inOut = new InOut();
            HBox row = (HBox)(rows.get(i));
            inOut.setInOutName(((TextField)(row.getChildren().get(1))).getText()); //nazwa
            inOut.setInOutDesc(((TextField)(row.getChildren().get(2))).getText()); //opis
            inOut.setInOutType(((ComboBox<String>)(row.getChildren().get(3))).getValue()); //typ
            //inOut.
            unit.getInOuts().add(inOut);
        }
    }

    @FXML private void updateButtonAction() {
        updateUnit(unitListView.getSelectionModel().getSelectedItem());
        unitListView.refresh();
    }

    private void fillTreeView() {
        if (configTreeView.getRoot() == null) {
            System.out.println("configTreeView init...");
            TreeItem<HardwareItem> root = new TreeItem<>(new HardwareItem("root"));
            configTreeView.setShowRoot(false);
            configTreeView.setRoot(root);
        }
        configTreeView.getRoot().getChildren().clear();
        for (HardwareItem hardwareItem: mainHardwareList) {
            configTreeView.getRoot().getChildren().add(getTree(hardwareItem));
        }
    }

    private TreeItem<HardwareItem> getTree(HardwareItem item) {
        TreeItem<HardwareItem> treeItem = new TreeItem<>(item);
        if (item.getChildren()!=null) {
            for (HardwareItem child: item.getChildren()) {
                treeItem.getChildren().add(getTree(child));
            }
        }
        return treeItem;
    }
//*********** konfiguracja ************
    @FXML
    private void konfCabAddBtnAction() {
        Cabinet nowy = new Cabinet("--- new ---");
        mainHardwareList.add(nowy);
        konfCabListView.getItems().add(nowy);
        konfCabListView.getSelectionModel().select(nowy);
    }
    @FXML
    private void konfCabRemBtnAction() {
        List<HardwareItem> toRemove = konfCabListView.getSelectionModel().getSelectedItems();
        mainHardwareList.removeAll(toRemove);
        konfCabListView.getItems().removeAll(toRemove);
    }
    @FXML
    private void konfModAddBtnAction() {
        IOModule nowy = new IOModule("--- new ---");
        konfCabListView.getSelectionModel().getSelectedItem().getChildren().add(nowy);
        konfModListView.getItems().add(nowy);
    }
    @FXML
    private void konfModRemBtnAction() {

    }
    private void konfCabChangeSelection(HardwareItem oldVal, HardwareItem newVal) {
        if (newVal != null) {
            konfModListView.getItems().clear();
            for (HardwareItem ioModule:newVal.getChildren()) {
                konfModListView.getItems().add(ioModule);
            }
        } else {
            konfModListView.getItems().clear();
        }
    }
}
