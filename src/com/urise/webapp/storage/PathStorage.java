package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serialization.SerializationStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private SerializationStrategy strategy;

    protected PathStorage(String dir, SerializationStrategy strategy) {
        directory = Paths.get(dir);
        requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not directory");
        }
        this.strategy = strategy;
    }

    public void setStrategy(SerializationStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    protected void doUpdate(Resume r, Path searchKey) {
        try {
            strategy.doWrite(r, new BufferedOutputStream(new FileOutputStream(String.valueOf(searchKey))));
        } catch (IOException e) {
            throw new StorageException("File not found", searchKey.toString());
        }
    }

    @Override
    protected Resume doGet(Path searchKey) {
        try {
            return strategy.doRead(new BufferedInputStream(new FileInputStream(String.valueOf(searchKey))));
        } catch (IOException e) {
            throw new StorageException("File not found", searchKey.toString());
        }
    }

    @Override
    protected void doSave(Resume r, Path searchKey) {
        try {
            Files.createFile(searchKey);
        } catch (IOException e) {
            throw new StorageException("File not created", searchKey.toString());
        }
        doUpdate(r, searchKey);
    }

    @Override
    protected void doDelete(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("File not deleted", searchKey.toString());
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(String.valueOf(directory), uuid);
    }

    @Override
    protected List<Resume> getAll() {
        List<Resume> list = new ArrayList<>();
        getFilesList(directory).forEach(path -> list.add(doGet(path)));
        return list;
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    public void clear() {
        getFilesList(directory).forEach(this::doDelete);
    }

    @Override
    public int size() {
        return getFilesList(directory).toList().size();
    }

    public Stream<Path> getFilesList(Path directory) {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory error", directory.toString());
        }
    }
}