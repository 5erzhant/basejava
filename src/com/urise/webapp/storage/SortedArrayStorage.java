package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override
    protected Object findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, countResumes, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void insertResume(int index, Resume r) {
        int insertIndex = Math.abs(index) - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, countResumes - insertIndex);
        storage[insertIndex] = r;
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, countResumes - index);
        storage[countResumes] = null;
    }
}
