package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage extends AbstractArrayStorage {
    public void clear() {
        Arrays.fill(storage, 0, countResumes - 1, null);
        countResumes = 0;
        System.out.println("Хранилище очищенно.");
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index == -1) {
            System.out.println("uuid: " + r.getUuid() + " не существует.");
        } else {
            storage[index] = r;
        }
    }

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

    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
