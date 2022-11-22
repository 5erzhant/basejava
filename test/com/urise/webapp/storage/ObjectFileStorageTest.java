package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.ObjectStreamSerializer;
import org.junit.Test;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }

    @Override
    @Test
    public void saveOverflow() {
    }
}