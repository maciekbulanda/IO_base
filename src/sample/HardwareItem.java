package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;

public class HardwareItem{
    private String name;
    private HardwareItem parent;
    private ArrayList<HardwareItem> children;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setParent(HardwareItem item) {
        this.parent = item;
    }
    public HardwareItem getParent () {
        return this.parent;
    }

    public HardwareItem(String name) {
        this.name = name;
    }
    public ArrayList<HardwareItem> getChildren() {
        if (children == null)
            children = new ArrayList<>();
        return children;
    }
    @Override
    public String toString() {
        return getName();
    }
}
