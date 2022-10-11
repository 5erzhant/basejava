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

    public Resume getResume(int index, String uuid) {
        return storage[index];
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    protected void saveResume(Resume r, int index) {
        if (countResumes == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insertResume(index, r);
        countResumes++;
    }

    public void updateResume(int index, Resume r) {
        storage[index] = r;
    }

    protected void deleteResume(String uuid, int index) {
        countResumes--;
        extractResume(index);
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    protected abstract int findIndex(String uuid);

    protected abstract void insertResume(int index, Resume r);

    protected abstract void extractResume(int index);

}
