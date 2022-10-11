package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[map.size()]);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected void updateResume(int index, Resume r) {
        for (String str : map.keySet()) {
            if (str.equals(r.getUuid())) {
                map.replace(str, r);
                return;
            }
        }
    }

    @Override
    protected Resume getResume(int index, String uuid) {
        return map.get(uuid);
    }

    @Override
    protected int findIndex(String uuid) {
        for (String str : map.keySet()) {
            if (str.equals(uuid)) {
                return 1;
            }
        }
        return -1;
    }

    @Override
    protected void saveResume(Resume r, int index) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected void deleteResume(String uuid, int index) {
        map.remove(uuid);
    }
}