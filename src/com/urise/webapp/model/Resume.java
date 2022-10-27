package com.urise.webapp.model;

import java.util.*;

public class Resume {

    private final String uuid;
    private final String fullName;

    Map<Enum, List<String>> sectionList = new HashMap<>();

    {
        sectionList.put(SectionType.ACHIEVEMENT, new ArrayList<>());
        sectionList.put(SectionType.QUALIFICATIONS, new ArrayList<>());
    }

    Map<Enum, String> sectionTypeMap = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void setContent(Enum section, String text) {
        if (section == SectionType.ACHIEVEMENT || section == SectionType.QUALIFICATIONS) {
            sectionList.get(section).add(text);
        }
        sectionTypeMap.put(section, text);
    }

    public String getContent(Enum section) {
        if (section == SectionType.ACHIEVEMENT || section == SectionType.QUALIFICATIONS) {
            StringBuilder sb = new StringBuilder();
            for (String s : sectionList.get(section)) {
                sb.append(s).append("\n");
            }
            return sb.substring(0, sb.length() - 1);
        }
        return sectionTypeMap.get(section);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }
}
