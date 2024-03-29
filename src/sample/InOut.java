package sample;

import java.io.Serializable;

public class InOut implements Serializable {
    private static final long serialVersionUID = -3770112338614965865L;
    private String inOutName;
    private String inOutDesc;
    private IoType inOutType;
    private HardwareItem parent;
    //private  inOutCab;

    public String getInOutName() {
        return this.inOutName;
    }

    public void setInOutName(String inOutName) {
        this.inOutName = inOutName;
    }

    public String getInOutDesc() {
        return this.inOutDesc;
    }

    public void setInOutDesc(String desc) {
        this.inOutDesc = desc;
    }

    public IoType getInOutType() {
        return this.inOutType;
    }

    public void setInOutType(IoType type) {
        this.inOutType = type;
    }

}
