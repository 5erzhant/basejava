package com.urise.webapp.storage;

import org.junit.Test;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    @Test
    public void saveOverflow() {
    }

    @Override
    @Test
    public void getAll() {
    }
}