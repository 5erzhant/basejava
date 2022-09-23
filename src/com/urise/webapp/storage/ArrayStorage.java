package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    protected int findIndex(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if ((storage[i].getUuid()).equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void insertResume(int index, Resume r) {
        storage[countResumes] = r;
    }

    @Override
    protected void deleteResume(int index) {
        storage[index] = storage[countResumes];
        storage[countResumes] = null;
    }
}
