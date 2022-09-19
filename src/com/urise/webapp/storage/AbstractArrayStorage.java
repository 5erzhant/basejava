package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    public final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes = 0;

    public int size() {
        return countResumes;
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("uuid: " + uuid + " не найден.");
            return null;
        }
        return storage[index];
    }

    protected abstract int findIndex(String uuid);

}
