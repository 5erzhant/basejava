package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class ListStorage extends AbstractStorage {

    private static final ArrayList<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public void update(Resume r) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(r.getUuid())) {
                list.set(i, r);
                return;
            }
        }
        throw new NotExistStorageException(r.getUuid());
    }

    @Override
    public void save(Resume r) {
        if (!list.contains(r)) {
            list.add(r);
        } else {
            throw new ExistStorageException(r.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        Iterator<Resume> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (Objects.equals((iterator.next()).getUuid(), uuid)) {
                iterator.remove();
            } else {
                throw new NotExistStorageException(uuid);
            }
        }
    }

    @Override
    public Resume get(String uuid) {
        for (Resume resume : list) {
            if (resume.getUuid().equals(uuid)) {
                return resume;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public int size() {
        return list.size();
    }
}
