package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.ObjectStreamSerializer;
import org.junit.Test;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectStreamSerializer()));
    }

    @Override
    @Test
    public void saveOverflow() {
    }
}