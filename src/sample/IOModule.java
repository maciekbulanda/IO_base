package sample;

public class IOModule extends HardwareItem {
    private int numberOfInOuts;
    private InOut[] inOuts;
    private IoType ioType;
    public IOModule(String name) {
        super(name);
    }
    void setNumberOfInOuts(int numberOfInOuts) {
        this.numberOfInOuts = numberOfInOuts;
        this.inOuts = new InOut[numberOfInOuts];
    }
    void setIoType(IoType ioType) {
        this.ioType = ioType;
    }
}
