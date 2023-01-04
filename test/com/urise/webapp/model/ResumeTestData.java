package com.urise.webapp.model;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = getResume("uuid1", "Григорий Кислин");
        for (ContactType contactType : ContactType.values()) {
            System.out.println(contactType.getTitle() + "\n");
            System.out.println(resume.getContact(contactType) + "\n");
        }
        for (SectionType sectionType : SectionType.values()) {
            System.out.println(sectionType.getTitle() + "\n");
            System.out.println(resume.getSection(sectionType) + "\n");
        }
    }

    public static Resume getResume(String uuid, String fullName) {
        String mobile = "+7(921) 855-0482";
        String skype = "skype:grigory.kislin";
        String mail = "gkislin@yandex.ru";
        String linkedin = "https://www.linkedin.com/in/gkislin";
        String github = "https://github.com/gkislin";
        String stackoverflow = "https://stackoverflow.com/users/548473/grigory-kislin";
        String homepage = "http://gkislin.ru/";

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
//
//        LocalDate begin = LocalDate.of(2013, 10, 1);
//        LocalDate end = LocalDate.now();
//        String title = "Автор проекта";
//        String description = "Создание, организация и проведение Java онлайн проектов и стажировок.";
//        Period period = new Period(begin, end, title);
//
//        String name = "Java Online Projects";
//        String website = "https://javaops.ru/";
//        Company company = new Company(name, website);
//
//        LocalDate begin2 = LocalDate.of(2014, 10, 1);
//        LocalDate end2 = LocalDate.of(2016, 1, 1);
//        String title2 = "Старший разработчик";
//        String description2 = "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, " +
//                "Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.";
//        Period period2 = new Period(begin2, end2, title2, description2);
//
//        String name2 = "Wrike";
//        String website2 = "https://www.wrike.com/";
//        Company company2 = new Company(name2);
//
//        company.addPeriod(period);
//        company2.addPeriod(period2);
//
//        CompanySection companySection = new CompanySection();
//        companySection.addCompany(company);
//        companySection.addCompany(company2);
//
//        LocalDate begin3 = DateUtil.of(2013, Month.MARCH);
//        LocalDate end3 = DateUtil.of(2013, Month.MAY);
//        String description3 = "Functional Programming Principles in Scala by Martin Odersky";
//
//        Period educationPeriod = new Period(begin3, end3, "", description3);
//
//        String edName = "Coursera";
//        String edWebsite = "https://www.coursera.org/learn/scala-functional-programming";
//
//        Company edCompany = new Company(edName, edWebsite);
//        edCompany.addPeriod(educationPeriod);
//
//        CompanySection edCompanySection = new CompanySection();
//        edCompanySection.addCompany(edCompany);
//
//
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.PHONE, mobile);
        resume.addContact(ContactType.SKYPE, skype);
        resume.addContact(ContactType.MAIL, mail);
        resume.addContact(ContactType.LINKEDIN, linkedin);
        resume.addContact(ContactType.GITHUB, github);
        resume.addContact(ContactType.STACKOVERFLOW, stackoverflow);
        resume.addContact(ContactType.HOME_PAGE, homepage);
        resume.addSection(SectionType.PERSONAL, personalSection);
        resume.addSection(SectionType.OBJECTIVE, objectiveSection);
        resume.addSection(SectionType.ACHIEVEMENT, achievmentList);
        resume.addSection(SectionType.QUALIFICATIONS, qualificationList);
//        resume.addSection(SectionType.EXPERIENCE, companySection);
//        resume.addSection(SectionType.EDUCATION, edCompanySection);
        return resume;
    }
}