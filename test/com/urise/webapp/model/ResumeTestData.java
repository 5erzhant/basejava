package com.urise.webapp.model;

import java.time.LocalDate;

public class ResumeTestData {
    public static void main(String[] args) {
        String phoneNumber = "+7(921) 855-0482";
        String skype = "skype:grigory.kislin";
        String mail = "gkislin@yandex.ru";

        String personal = "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.";
        String objective = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям";
        TextSection personalSection = new TextSection(personal);
        TextSection objectiveSection = new TextSection(objective);

        String achievment1 = "Организация команды и успешная реализация Java проектов для " +
                "сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга " +
                "показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot " +
                "+ Vaadin проект для комплексных DIY смет";
        String achievment2 = "С 2013 года: разработка проектов \"Разработка Web приложения\"," +
                "\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы " +
                "(JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов." +
                " Более 3500 выпускников.";
        ListSection achievmentList = new ListSection();
        achievmentList.addContent(achievment1);
        achievmentList.addContent(achievment2);

        String qualifications1 = "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2";
        String qualifications2 = "Version control: Subversion, Git, Mercury, ClearCase, Perforce";
        String qualifications3 = "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, " +
                "SQLite, MS SQL, HSQLDB";
        ListSection qualificationList = new ListSection();
        qualificationList.addContent(qualifications1);
        qualificationList.addContent(qualifications2);
        qualificationList.addContent(qualifications3);

        LocalDate begin = LocalDate.of(2013, 10, 1);
        LocalDate end = LocalDate.now();
        String title = "Автор проекта";
        String description = "Создание, организация и проведение Java онлайн проектов и стажировок.";
        Period period = new Period(begin, end, title, description);

        String name = "Java Online Projects";
        String website = "topjava.ru";
        Company company = new Company(name, website);

        LocalDate begin2 = LocalDate.of(2014, 10, 1);
        LocalDate end2 = LocalDate.of(2016, 1, 1);
        String title2 = "Старший разработчик";
        String description2 = "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, " +
                "Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.";
        Period period2 = new Period(begin2, end2, title2, description2);

        String name2 = "Wrike";
        String website2 = "wrike.com";
        Company company2 = new Company(name2, website2);

        company.addPeriod(period);
        company2.addPeriod(period2);

        CompanySection companySection = new CompanySection();
        companySection.addCompany(company);
        companySection.addCompany(company2);

        Resume resume = new Resume("Григорий Кислин");
        resume.addContact(ContactType.PHONE_NUMBER, phoneNumber);
        resume.addContact(ContactType.SKYPE, skype);
        resume.addContact(ContactType.MAIL, mail);
        resume.addSection(SectionType.PERSONAL, personalSection);
        resume.addSection(SectionType.OBJECTIVE, objectiveSection);
        resume.addSection(SectionType.ACHIEVEMENT, achievmentList);
        resume.addSection(SectionType.QUALIFICATIONS, qualificationList);
        resume.addSection(SectionType.EXPERIENCE, companySection);

        for (ContactType contactType : ContactType.values()) {
            System.out.println(contactType.getTitle() + "\n");
            System.out.println(resume.getContact(contactType) + "\n");
        }
        for (SectionType sectionType : SectionType.values()) {
            System.out.println(sectionType.getTitle() + "\n");
            System.out.println(resume.getSection(sectionType) + "\n");
        }


    }
}