package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    int countResumes = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, countResumes - 1, null);
        countResumes = 0;
        System.out.println("Хранилище очищенно.");
    }

    @Override
    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index == -1) {
            System.out.println("uuid: " + r.getUuid() + " не существует.");
        } else {
            storage[index] = r;
        }
    }

    @Override
    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (countResumes == STORAGE_LIMIT) {
            System.out.println("Хранилище переполнено!");
        } else if (index > -1) {
            System.out.println("uuid: " + r.getUuid() + " уже существует.");
        } else {
            storage[countResumes] = r;
            countResumes++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("uuid: " + uuid + " не найден.");
        } else {
            System.out.println("uuid: " + storage[index].getUuid() + " удален.");
            storage[index] = storage[countResumes - 1];
            storage[countResumes - 1] = null;
            countResumes--;
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("uuid: " + uuid + " не найден.");
            return null;
        }
        return storage[index];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    @Override
    public int size() {
        return countResumes;
    }

    @Override
    public int findIndex(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
