package com.urise.webapp.storage;

import com.urise.webapp.Config;
import org.junit.Test;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(Config.get().getStorage());
    }

    @Override
    @Test
    public void saveOverflow() {
    }
}