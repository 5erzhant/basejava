package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        int index = getIndexIfExist(r.getUuid());
        updateResume(index, r);
    }

    public void save(Resume r) {
        int index = getIndexIfNotExist(r.getUuid());
        saveResume(r, index);
    }

    public void delete(String uuid) {
        int index = getIndexIfExist(uuid);
        deleteResume(uuid, index);
    }

    public Resume get(String uuid) {
        int index = getIndexIfExist(uuid);
        return getResume(index, uuid);
    }

    protected int getIndexIfExist(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return index;
        }
    }

    protected int getIndexIfNotExist(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        } else {
            return index;
        }
    }

    protected abstract void updateResume(int index, Resume r);

    protected abstract Resume getResume(int index, String uuid);

    protected abstract int findIndex(String uuid);

    protected abstract void saveResume(Resume r, int index);

    protected abstract void deleteResume(String uuid, int index);
}
