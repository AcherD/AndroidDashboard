package com.example.android_socketcan;

public enum FrameId {

    TURN_LIGHT("213"),      //BS_1    0x213
    VEHICLE_LOCK("213"),    //BS_1    0x213
    BEAMHEAD_LIGHT("213"),  //BS_1    0x213
    WIPER_WIPER("214"),//BS_2    0x214
    VEHICLE_SPEED("E0"),    //EMS_1   0xE0
    ENGINE_SPEED("E0"),     //EMS_1   0xE0
    GEAR("F0"),     //TCU_1   0xF0
    AEB_LEVEL("102");       //APC_2   0x102


    private String value;

    FrameId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FrameId fromString(String text) {
        for (FrameId frameId : FrameId.values()) {
            if (frameId.value.equalsIgnoreCase(text)) {
                return frameId;
            }
        }
        return null;
    }
}
