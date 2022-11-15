package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.ObjectStreamStrategy;
import org.junit.Test;

public class ObjectStreamPathTest extends AbstractStorageTest {

    public ObjectStreamPathTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectStreamStrategy()));
    }

    @Override
    @Test
    public void saveOverflow() {
    }
}