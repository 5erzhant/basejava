package com.urise.webapp.storage;

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
    protected void updateResume(int index, Resume r) {
        list.set(index, r);
    }

    @Override
    protected void saveResume(Resume r, int index) {
        list.add(r);
    }

    @Override
    protected void deleteResume(String uuid, int index) {
        Iterator<Resume> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (Objects.equals((iterator.next()).getUuid(), uuid)) {
                iterator.remove();
            }
        }
    }

    @Override
    protected Resume getResume(int index, String uuid) {
        return list.get(index);
    }

    @Override
    protected int findIndex(String uuid) {
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
        return list.toArray(new Resume[list.size()]);
    }
}
