package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.AbstractArrayStorage;
import com.urise.webapp.storage.SortedArrayStorage;

public class MainTestArrayStorage {
    static final AbstractArrayStorage SORTED_ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid3", "Alex");
        Resume r2 = new Resume("uuid2","Petr");
        Resume r3 = new Resume("uuid1", "Alex");
        Resume r4 = new Resume("uuid3", "Egor");

        SORTED_ARRAY_STORAGE.save(r1);
        SORTED_ARRAY_STORAGE.save(r2);
        SORTED_ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + SORTED_ARRAY_STORAGE.get(r1.getUuid()));

        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());

//        System.out.println("Get dummy: " + SORTED_ARRAY_STORAGE.get("dummy"));

        printAll();
        SORTED_ARRAY_STORAGE.delete(r1.getUuid());
        printAll();

        Resume prev = SORTED_ARRAY_STORAGE.get(r3.getUuid());
        SORTED_ARRAY_STORAGE.update(r4);
        Resume post = SORTED_ARRAY_STORAGE.get(r3.getUuid());
        System.out.print("После update: ");
        System.out.println(prev == post);

        printAll();
        SORTED_ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : SORTED_ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
