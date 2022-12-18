package com.urise.webapp.storage;

import com.urise.webapp.Config;
import org.junit.Test;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword()));
    }

    @Override
    @Test
    public void saveOverflow() {
    }
}