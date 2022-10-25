package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MainCollection {
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_NOT_EXIST = "dummy";
    private static final String FULL_NAME_1 = "Alexander";
    private static final String FULL_NAME_2 = "Ivan";
    private static final String FULL_NAME_3 = "Petr";
    protected static final Resume RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
    protected static final Resume RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
    protected static final Resume RESUME_3 = new Resume(UUID_3, FULL_NAME_3);
    protected static final Resume RESUME_4 = new Resume(UUID_4, FULL_NAME_2);

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();
        collection.add(RESUME_1);
        collection.add(RESUME_2);
        collection.add(RESUME_3);

//        Iterator<Resume> iterator = collection.iterator();
//        while (iterator.hasNext()) {
//            Resume resume = iterator.next();
//            System.out.println(resume);
//            if (Objects.equals(resume.getUuid(), UUID_1)) {
//                iterator.remove();
//            }
//        }

        for (Resume resume : collection) {
            System.out.println(resume);
        }

        System.out.println(collection);

        Map<String, Resume> map = new HashMap<>();
        map.put(UUID_1, RESUME_1);
        map.put(UUID_2, RESUME_2);
        map.put(UUID_3, RESUME_3);

        for (Map.Entry<String, Resume> entry : map.entrySet() ) {
            System.out.println(entry.getValue());
        }

        List<Resume> resumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);

    }
}
