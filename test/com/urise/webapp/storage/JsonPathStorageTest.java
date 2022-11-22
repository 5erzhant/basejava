package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.JsonStreamSerializer;
import org.junit.Test;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializer()));
    }

    @Override
    @Test
    public void saveOverflow() {
    }
}
