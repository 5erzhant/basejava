package com.urise.webapp.exception;

public class StorageException extends RuntimeException {

    public String uuid;

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, Exception e) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(Exception e) {
        this(e.getMessage(), null, e);
    }

    public StorageException(String str) {
        super(str);
    }

    public String getUuid() {
        return uuid;
    }

}
