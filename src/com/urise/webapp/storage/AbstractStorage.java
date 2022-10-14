package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(searchKey, r);
    }

    public void save(Resume r) {
        Object searchKey = getNotExistingSearchKey(r.getUuid());
        doSave(r, searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        doDelete(uuid, searchKey);
    }

    public Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey, uuid);
    }

    protected Object getExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if ((int) searchKey < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    protected Object getNotExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if ((int) searchKey >= 0) {
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    protected abstract void doUpdate(Object searchKey, Resume r);

    protected abstract Resume doGet(Object searchKey, String uuid);

    protected abstract Object findSearchKey(String uuid);

    protected abstract void doSave(Resume r, Object searchKey);

    protected abstract void doDelete(String uuid, Object searchKey);
}
