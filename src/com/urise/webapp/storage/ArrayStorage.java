package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    public Resume[] storage = new Resume[10000];
    int countResumes = 0;

    public void clear() {
        Arrays.fill(storage, 0, countResumes - 1, null);
        countResumes = 0;
        System.out.println("Хранилище очищенно.");
    }

    public void update(Resume r) {
        int i;
        if ((i = isExist(r.uuid)) > -1) {
            storage[i] = r;
        } else {
            System.out.println("uuid: " + r.uuid + " не существует.");
        }
    }

    public void save(Resume r) {
        if (isExist(r.uuid) > -1) {
            System.out.println("uuid: " + r.uuid + " уже существует.");
            return;
        }
        if (countResumes < storage.length) {
            storage[countResumes] = r;
            countResumes++;
        } else {
            System.out.println("Хранилище переполнено!");
        }

    }

    public void delete(String uuid) {
        int i;
        if ((i = isExist(uuid)) > -1) {
            System.out.println("uuid: " + storage[i].uuid + " удален.");
            storage[i] = storage[countResumes - 1];
            storage[countResumes - 1] = null;
            countResumes--;
        } else {
            System.out.println("uuid: " + uuid + " не найден.");
        }
    }

    public Resume get(String uuid) {
        if (isExist(uuid) > -1) {
            return storage[isExist(uuid)];
        }
        System.out.println("uuid: " + uuid + " не найден.");
        return null;
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

    public int isExist(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
