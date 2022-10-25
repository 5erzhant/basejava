package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

//    protected static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> {
//        int result = o1.getFullName().compareTo(o2.getFullName());
//        return result != 0 ? result : o1.getUuid().compareTo(o2.getUuid());
//    };

    protected static final Comparator<Resume> COMPARATOR = Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

    protected abstract void doUpdate(Resume r, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doSave(Resume r, Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract Object findSearchKey(String uuid);

    protected abstract List<Resume> getAll();

    public void update(Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(r, searchKey);
    }

    public void save(Resume r) {
        Object searchKey = getNotExistingSearchKey(r.getUuid());
        doSave(r, searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    public Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public List<Resume> getAllSorted() {
        List<Resume> list = getAll();
        list.sort(COMPARATOR);
        return list;
    }

    protected Object getExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    protected Object getNotExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

        protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }
}
