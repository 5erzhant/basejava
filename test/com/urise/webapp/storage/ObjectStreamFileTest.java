package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.ObjectStreamStrategy;
import org.junit.Test;

public class ObjectStreamFileTest extends AbstractStorageTest {

    public ObjectStreamFileTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStrategy()));
    }

    @Override
    @Test
    public void saveOverflow() {
    }
}