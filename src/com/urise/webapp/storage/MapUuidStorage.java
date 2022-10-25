package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return map.containsKey((String) searchKey);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        map.put((String) searchKey, r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return map.get((String) searchKey);
    }

    @Override
    protected Object findSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        map.put((String) searchKey, r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        map.remove((String) searchKey);
    }
}