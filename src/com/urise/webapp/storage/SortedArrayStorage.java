package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, countResumes, searchKey);
    }

    @Override
    protected void insertResume(int index, Resume r) {
        int insertIndex = Math.abs(index) - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, countResumes - insertIndex);
        storage[insertIndex] = r;
    }

    @Override
    protected void extractResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, countResumes - index);
        storage[countResumes] = null;
    }
}
