package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    public final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes = 0;

    protected abstract void insertResume(int index, Resume r);

    protected abstract void deleteResume(int index);

    @Override
    public int size() {
        return countResumes;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    @Override
    public Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void doSave(Resume r, Integer searchKey) {
        if (countResumes == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insertResume(searchKey, r);
        countResumes++;
    }

    @Override
    public void doUpdate(Resume r, Integer searchKey) {
        storage[searchKey] = r;
    }

    @Override
    protected void doDelete(Integer searchKey) {
        countResumes--;
        deleteResume(searchKey);
    }

    @Override
    protected List<Resume> getAll() {
        Resume[] getAll = Arrays.copyOf(storage, countResumes);
        return new ArrayList<>(Arrays.asList(getAll));
    }
}
