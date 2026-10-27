
package model;

public class Asset {
    private int id;
    private String deviceName;
    private String assignedTo;
    private String serialNo;
    private String condition;

    public Asset(int id, String deviceName, String assignedTo,
                 String serialNo, String condition) {
        this.id = id;
        this.deviceName = deviceName;
        this.assignedTo = assignedTo;
        this.serialNo = serialNo;
        this.condition = condition;
    }

    public Asset(String deviceName, String assignedTo,
                 String serialNo, String condition) {
        this(0, deviceName, assignedTo, serialNo, condition);
    }

    public int getId() {
        return id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public String getCondition() {
        return condition;
    }
}
