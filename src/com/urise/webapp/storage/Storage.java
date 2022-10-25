package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

public interface Storage {
    void clear();

    void update(Resume r);

    void save(Resume r);

    void delete(String uuid);

    Resume get(String uuid);

    List<Resume> getAllSorted();

    int size();
}
