package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {
    private final Storage storage;
    protected static final String UUID_1 = "uuid3";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid1";
    protected static final String UUID_4 = "uuid4";
    private static final String UUID_NOT_EXIST = "dummy";
    private static final String FULL_NAME_1 = "Alex";
    private static final String FULL_NAME_2 = "Petr";
    private static final String FULL_NAME_3 = "Alex";
    protected static final Resume RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
    protected static final Resume RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
    protected static final Resume RESUME_3 = new Resume(UUID_3, FULL_NAME_3);
    protected static final Resume RESUME_4 = new Resume(UUID_4, FULL_NAME_2);

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXIST);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID_NOT_EXIST, FULL_NAME_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_NOT_EXIST);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_1, FULL_NAME_1));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(FULL_NAME_1));
            }
        } catch (StorageException storageException) {
            Assert.fail();
        }
        storage.save(new Resume(FULL_NAME_1));
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        Assert.assertEquals(storage.getAllSorted(), new ArrayList<>());
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1, FULL_NAME_2);
        storage.update(newResume);
        Assert.assertSame(newResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = Arrays.asList(RESUME_3, RESUME_1, RESUME_2);
        List<Resume> actual = storage.getAllSorted();
        Assert.assertEquals(actual, expected);
        Assert.assertEquals(storage.size(), actual.size());
    }

    private void assertSize(int assertSize) {
        Assert.assertEquals(storage.size(), assertSize);
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, (storage.get(resume.getUuid())));
    }
}