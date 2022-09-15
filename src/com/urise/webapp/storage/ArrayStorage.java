package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    int countResumes = 0;

    public void clear() {
        Arrays.fill(storage, 0, countResumes - 1, null);
        countResumes = 0;
        System.out.println("Хранилище очищенно.");
    }

    public void update(Resume r) {
        int index = 0;
        if ((index = findIndex(r.getUuid())) == -1) {
            System.out.println("uuid: " + r.getUuid() + " не существует.");
        } else {
            storage[index] = r;
        }
    }

    public void save(Resume r) {
        if (countResumes == STORAGE_LIMIT) {
            System.out.println("Хранилище переполнено!");
        } else if (findIndex(r.getUuid()) > -1) {
            System.out.println("uuid: " + r.getUuid() + " уже существует.");
        } else {
            storage[countResumes] = r;
            countResumes++;
            }
    }

    public void delete(String uuid) {
        int index = 0;
        if ((index = findIndex(uuid)) == -1) {
            System.out.println("uuid: " + uuid + " не найден.");
        } else {
            System.out.println("uuid: " + storage[index].getUuid() + " удален.");
            storage[index] = storage[countResumes - 1];
            storage[countResumes - 1] = null;
            countResumes--;
        }
    }

    public Resume get(String uuid) {
        int index = 0;
        if ((index = findIndex(uuid)) == -1) {
            System.out.println("uuid: " + uuid + " не найден.");
            return null;
        }
        return storage[index];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    public int size() {
        return countResumes;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
