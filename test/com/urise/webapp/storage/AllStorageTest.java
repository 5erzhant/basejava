package com.urise.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ArrayStorageTest.class, ArraySortedStorageTest.class,
        ListStorageTest.class, MapUuidStorageTest.class, MapResumeStorageTest.class, ObjectFileStorageTest.class,
        ObjectPathStorageTest.class, XmlPathStorageTest.class, JsonPathStorageTest.class, DataStreamPathStorageTest.class,
        SqlStorageTest.class})
public class AllStorageTest {
}
