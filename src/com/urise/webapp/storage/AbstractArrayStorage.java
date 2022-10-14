package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    public final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes = 0;

    @Override
    public int size() {
        return countResumes;
    }

    public Resume doGet(Object searchKey, String uuid) {
        return storage[(int) searchKey];
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    protected void doSave(Resume r, Object searchKey) {
        if (countResumes == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insertResume((int) searchKey, r);
        countResumes++;
    }

    public void doUpdate(Object searchKey, Resume r) {
        storage[(int) searchKey] = r;
    }

    protected void doDelete(String uuid, Object searchKey) {
        countResumes--;
        deleteResume((int) searchKey);
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    protected abstract void insertResume(int index, Resume r);

    protected abstract void deleteResume(int index);

}
