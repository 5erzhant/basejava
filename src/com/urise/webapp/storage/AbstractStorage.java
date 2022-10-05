package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    public void clear() {
    }

    public void update(Resume r) {
    }

    public void save(Resume r) {
    }

    public void delete(String uuid) {
    }

    public Resume get(String uuid) {
        return null;
    }

    public Resume[] getAll() {
        return new Resume[0];
    }

    public int size() {
        return 0;
    }
}
