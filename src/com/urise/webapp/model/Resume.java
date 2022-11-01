package com.urise.webapp.model;

import java.util.*;

public class Resume {

    private final String uuid;
    private final String fullName;

    Map<ContactType, String> contacts = new HashMap<>();
    Map<SectionType, AbstractSection> sections = new HashMap<>();

    {
        sections.put(SectionType.ACHIEVEMENT, )
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void setContent(Enum section, String text) {
        if (section instanceof ContactType) {
            contacts.put((ContactType) section, text);
        } else if (section.equals(SectionType.PERSONAL) || section.equals(SectionType.OBJECTIVE)) {
            sections.put((SectionType) section, new TextSection(text));
        } else if (section.equals(SectionType.ACHIEVEMENT) || section.equals(SectionType.QUALIFICATIONS)) {
            sections.put((SectionType) section, );

        }
    }

    public String getContent(Enum section) {

        return null;
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
