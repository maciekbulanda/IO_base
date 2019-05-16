package sample;

public class Cabinet extends HardwareItem {
    private String cabinetDesc;

    public Cabinet(String name) {
        super(name);
    }

    public String getCabinetDesc() {
        return cabinetDesc;
    }

    public void setCabinetDesc(String cabinetDesc) {
        this.cabinetDesc = cabinetDesc;
    }

}
