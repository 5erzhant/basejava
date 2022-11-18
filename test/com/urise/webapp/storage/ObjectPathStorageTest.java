package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.ObjectStreamStrategy;
import org.junit.Test;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectStreamStrategy()));
    }

    @Override
    @Test
    public void saveOverflow() {
    }
}