package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    public final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes = 0;

    public int size() {
        return countResumes;
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("uuid: " + uuid + " не найден.");
            return null;
        }
        return storage[index];
    }

    public void clear() {
        Arrays.fill(storage, 0, countResumes - 1, null);
        countResumes = 0;
        System.out.println("Хранилище очищенно.");
    }

    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (countResumes == STORAGE_LIMIT) {
            System.out.println("Хранилище переполнено!");
        } else if (index > -1) {
            System.out.println("uuid: " + r.getUuid() + " уже существует.");
        } else {
            insert(index, r);
            countResumes++;
        }
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index < 0) {
            System.out.println("uuid: " + r.getUuid() + " не существует.");
        } else {
            storage[index] = r;
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("uuid: " + uuid + " не найден.");
        } else {
            System.out.println("uuid: " + storage[index].getUuid() + " удален.");
            extract(index);
            countResumes--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }


    protected abstract int findIndex(String uuid);

    protected abstract void insert(int index, Resume r);

    protected abstract void extract(int index);

}
