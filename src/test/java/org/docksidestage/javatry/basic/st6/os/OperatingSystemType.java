package org.docksidestage.javatry.basic.st6.os;

public enum OperatingSystemType {
    OS_TYPE_MAC("Mac"), OS_TYPE_WINDOWS("Windows"), OS_TYPE_OLD_WINDOWS("OldWindows");
    private final String value;
    OperatingSystemType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}