package com.urise.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ArrayStorageTest.class, ArraySortedStorageTest.class,
        ListStorageTest.class, MapUuidStorageTest.class, MapResumeStorageTest.class, ObjectStreamFileTest.class,
        ObjectStreamPathTest.class})
public class AllStorageTest {
}
