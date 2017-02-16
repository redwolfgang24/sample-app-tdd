package com.sampleapptdd.demo.type

public enum Status {

    ACTIVE("ACTIVE"),
    IN_ACTIVE("IN_ACTIVE"),
    DELETED("DELETED")

    String status
    public Status(String status) {
        this.status = status
    }

    String toString() {
        return status
    }

    public String value() {
        return status
    }

    public static Status findByName(String status) {
        return values().find { status.toUpperCase() == it.status }
    }
}
