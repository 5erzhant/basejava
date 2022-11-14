package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.ObjectStreamStorage;
import org.junit.Test;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }

    @Override
    @Test
    public void saveOverflow() {
    }
}