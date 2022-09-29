package com.urise.webapp.storage;

import org.junit.Before;

public class ArrayStorageTest extends AbstractArrayStorageTest {

    @Before
    public void initStorage() {
        storage = new ArrayStorage();
        setUp();
    }
}