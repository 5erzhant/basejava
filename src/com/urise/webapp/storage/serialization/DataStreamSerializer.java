package com.urise.webapp.storage.serialization;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume r, OutputStream outputStream) {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
//            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
//                dos.writeUTF(entry.getKey().name());
//                dos.writeUTF(entry.getValue());

            Consumer<Map.Entry<ContactType, String>> action = entry -> {
                try {
                    dos.writeUTF(entry.getKey().name());
                    dos.writeUTF(entry.getValue());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };
            writeWithException(contacts.entrySet(), dos, action);

            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                AbstractSection section = entry.getValue();
                String className = section.getClass().getName();
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case PERSONAL, OBJECTIVE -> {
                        dos.writeUTF(className);
                        writeTextSection((TextSection) section, dos);
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        dos.writeUTF(className);
                        writeListSection((ListSection) section, dos);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        dos.writeUTF(className);
                        writeCompanySection((CompanySection) section, dos);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + entry.getValue());
                }
            }
        } catch (IOException e) {
            throw new StorageException("Write object error", null);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int sizeContacts = dis.readInt();
            for (int i = 0; i < sizeContacts; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sizeSections = dis.readInt();
            for (int i = 0; i < sizeSections; i++) {
                String sectionName = dis.readUTF();
                String className = dis.readUTF();
                AbstractSection as = switch (className) {
                    case "com.urise.webapp.model.TextSection" -> readTextSection(dis);
                    case "com.urise.webapp.model.ListSection" -> readListSection(dis);
                    case "com.urise.webapp.model.CompanySection" -> readCompanySection(dis);
                    default -> throw new IllegalStateException("Unexpected value: " + className);
                };
                resume.addSection(SectionType.valueOf(sectionName), as);
            }
            return resume;
        } catch (IOException e) {
            throw new StorageException("Read object error", null);
        }
    }

    private void writeCompanySection(CompanySection cs, DataOutputStream dos) {
        try {
            dos.writeInt(cs.getCompanies().size());
            for (Company company : cs.getCompanies()) {
                dos.writeUTF(company.getName());
                dos.writeUTF(company.getWebsite() != null ? company.getWebsite() : "no site");
                dos.writeInt(company.getPeriods().size());
                for (Period period : company.getPeriods()) {
                    dos.writeUTF(period.getBegin().toString());
                    dos.writeUTF(period.getEnd().toString());
                    dos.writeUTF(period.getTitle());
                    dos.writeUTF(period.getDescription() != null ? period.getDescription() : "no description");
                }
            }
        } catch (IOException e) {
            throw new StorageException("Company section not written", null);
        }
    }

    private void writeListSection(ListSection ls, DataOutputStream dos) {
        try {
            dos.writeInt(ls.getContentList().size());
            for (String str : ls.getContentList()) {
                dos.writeUTF(str);
            }
        } catch (IOException e) {
            throw new StorageException("List section not written", null);
        }
    }

    private void writeTextSection(TextSection ts, DataOutputStream dos) {
        try {
            dos.writeUTF(ts.getContent());
        } catch (IOException e) {
            throw new StorageException("Text section not written", null);
        }
    }

    private CompanySection readCompanySection(DataInputStream dis) {
        CompanySection cs = new CompanySection();
        try {
            int sizeCompanies = dis.readInt();
            for (int i = 0; i < sizeCompanies; i++) {
                String name = dis.readUTF();
                String webSite = dis.readUTF();
                Company company;
                if (!webSite.equals("no site")) {
                    company = new Company(name, webSite);
                } else {
                    company = new Company(name);
                }
                int sizePeriods = dis.readInt();
                for (int j = 0; j < sizePeriods; j++) {
                    LocalDate begin = LocalDate.parse(dis.readUTF());
                    LocalDate end = LocalDate.parse(dis.readUTF());
                    String title = dis.readUTF();
                    String description = dis.readUTF();
                    Period period;
                    if (!description.equals("no description")) {
                        period = new Period(begin, end, title, description);
                    } else {
                        period = new Period(begin, end, title);
                    }
                    company.addPeriod(period);
                }
                cs.addCompany(company);
            }
        } catch (IOException e) {
            throw new StorageException("Read CompanySection Error", null);
        }
        return cs;
    }

    private ListSection readListSection(DataInputStream dis) {
        ListSection ls;
        try {
            ls = new ListSection();
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                ls.addContent(dis.readUTF());
            }
        } catch (IOException e) {
            throw new StorageException("Read ListSection Error", null);
        }
        return ls;
    }

    private TextSection readTextSection(DataInputStream dis) {
        TextSection ts;
        try {
            ts = new TextSection(dis.readUTF());
        } catch (IOException e) {
            throw new StorageException("Read TextSection Error", null);
        }
        return ts;
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, Consumer<T> action) {
        Objects.requireNonNull(action);
        for (T entry : collection) {
            action.accept(entry);
        }
    }
}
