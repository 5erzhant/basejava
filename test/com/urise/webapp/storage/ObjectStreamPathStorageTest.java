package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.ObjectStreamPathStorage;
import org.junit.Test;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectStreamPathStorage()));
    }

    @Override
    @Test
    public void saveOverflow() {
    }
}