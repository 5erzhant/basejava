package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;

    SerializationStrategy strategy;

    protected PathStorage(String dir, SerializationStrategy strategy) {
        directory = Paths.get(dir);
        requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not directory");
        }
        this.strategy = strategy;
    }

    @Override
    protected void doUpdate(Resume r, Path searchKey) {
        try {
            strategy.doWrite(r, new BufferedOutputStream(new FileOutputStream(String.valueOf(searchKey))));
        } catch (IOException e) {
            throw new StorageException("IO Error", null);
        }
    }

    @Override
    protected Resume doGet(Path searchKey) {
        try {
            return strategy.doRead(new BufferedInputStream(new FileInputStream(String.valueOf(searchKey))));
        } catch (IOException e) {
            throw new StorageException("IO Error", null);
        }
    }

    @Override
    protected void doSave(Resume r, Path searchKey) {
        try {
            Files.createFile(searchKey);
            strategy.doWrite(r, new BufferedOutputStream(new FileOutputStream(String.valueOf(searchKey))));
        } catch (IOException e) {
            throw new StorageException("IO Error", null);
        }
    }

    @Override
    protected void doDelete(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("IO Error", null);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(String.valueOf(directory), uuid);
    }

    @Override
    protected List<Resume> getAll() {
        List<Resume> list = new ArrayList<>();
        try {
            Files.list(directory).forEach(path -> {
                try {
                    list.add(strategy.doRead(new BufferedInputStream(new FileInputStream(String.valueOf(path)))));
                } catch (IOException e) {
                    throw new StorageException("IO Error", null);
                }
            });
        } catch (IOException e) {
            throw new StorageException("IO Error", null);
        }
        return list;
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(path -> doDelete(path));
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return Files.list(directory).toList().size();
        } catch (IOException e) {
            throw new StorageException("IO Error", null);
        }
    }
}