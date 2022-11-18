package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.XmlStreamSerializer;
import org.junit.Test;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamSerializer()));
    }

    @Override
    @Test
    public void saveOverflow() {
    }
}
