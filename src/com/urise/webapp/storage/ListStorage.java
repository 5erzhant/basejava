package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {

    private static final ArrayList<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    protected void doUpdate(Object searchKey, Resume r) {
        list.set((int) searchKey, r);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        list.add(r);
    }

    @Override
    protected void doDelete(String uuid, Object searchKey) {
        list.remove((int) searchKey);
    }

    @Override
    protected Resume doGet(Object searchKey, String uuid) {
        return list.get((int) searchKey);
    }

    @Override
    protected Object findSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return list.size();
    }

    public Resume[] getAll() {
        return list.toArray(new Resume[0]);
    }
}
