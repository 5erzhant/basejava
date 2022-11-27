package com.urise.webapp.storage.serialization;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.util.CustomConsumer;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume r, OutputStream outputStream) {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            CustomConsumer<Map.Entry<ContactType, String>> actionContact = entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            };
            writeWithException(contacts.entrySet(), dos, actionContact);

            Map<SectionType, AbstractSection> sections = r.getSections();
            CustomConsumer<Map.Entry<SectionType, AbstractSection>> actionSection = entry -> {
                AbstractSection section = entry.getValue();
                SectionType type = entry.getKey();
                dos.writeUTF(type.name());
                switch (type) {
                    case PERSONAL, OBJECTIVE -> {
                        TextSection ts = (TextSection) section;
                        dos.writeUTF(ts.getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> writeListSection((ListSection) section, dos);
                    case EXPERIENCE, EDUCATION -> writeCompanySection((CompanySection) section, dos);
                    default -> throw new IllegalStateException("Unexpected value: " + entry.getValue());
                }
            };
            writeWithException(sections.entrySet(), dos, actionSection);
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
                SectionType type = SectionType.valueOf(dis.readUTF());
                AbstractSection section = switch (type) {
                    case PERSONAL, OBJECTIVE -> new TextSection(dis.readUTF());
                    case ACHIEVEMENT, QUALIFICATIONS -> readListSection(dis);
                    case EXPERIENCE, EDUCATION -> readCompanySection(dis);
                };
                resume.addSection(type, section);
            }
            return resume;
        } catch (IOException e) {
            throw new StorageException("Read object error", null);
        }
    }

    private void writeCompanySection(CompanySection cs, DataOutputStream dos) throws IOException {
        CustomConsumer<Period> actionPeriod = period -> {
            dos.writeUTF(period.getBegin().toString());
            dos.writeUTF(period.getEnd().toString());
            dos.writeUTF(period.getTitle());
            dos.writeUTF(period.getDescription() != null ? period.getDescription() : "no description");
        };

        CustomConsumer<Company> actionCompany = company -> {
            dos.writeUTF(company.getName());
            dos.writeUTF(company.getWebsite() != null ? company.getWebsite() : "no site");
            writeWithException(company.getPeriods(), dos, actionPeriod);
        };
        writeWithException(cs.getCompanies(), dos, actionCompany);
    }

    private void writeListSection(ListSection ls, DataOutputStream dos) throws IOException {
//      CustomConsumer<String> action = str -> dos.writeUTF(str);
        CustomConsumer<String> action = dos::writeUTF;
        writeWithException(ls.getContentList(), dos, action);
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

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, CustomConsumer<T> action)
            throws IOException {
        Objects.requireNonNull(action);
        dos.writeInt(collection.size());
        for (T entry : collection) {
            action.accept(entry);
        }
    }
}
