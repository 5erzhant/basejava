package com.urise.webapp.storage;

import org.junit.Before;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    @Before
    public void init() {
        storage = new SortedArrayStorage();
        setUp();
    }
}