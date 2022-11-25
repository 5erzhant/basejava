package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.DataStreamSerializer;
import org.junit.Test;

public class DataStreamPathStorageTest extends AbstractStorageTest {

    public DataStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new DataStreamSerializer()));
    }

    @Override
    @Test
    public void saveOverflow() {
    }
}