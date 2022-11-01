package com.urise.webapp.model;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        resume.setContent(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        resume.setContent(ContactType.SKYPE, "skype:grigory.kislin");
        resume.setContent(ContactType.MAIL, "gkislin@yandex.ru");
        resume.setContent(SectionType.OBJECTIVE, "Ведущий стажировок и корпоративного обучения по Java Web и " +
                "Enterprise технологиям");
        resume.setContent(SectionType.PERSONAL, "Аналитический склад ума, сильная логика, креативность, " +
                "инициативность. Пурист кода и архитектуры.");
        resume.setContent(SectionType.ACHIEVEMENT, "Организация команды и успешная реализация Java проектов для " +
                "сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга " +
                "показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot " +
                "+ Vaadin проект для комплексных DIY смет");
        resume.setContent(SectionType.ACHIEVEMENT, "С 2013 года: разработка проектов \"Разработка Web приложения\"," +
                "\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы " +
                "(JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов." +
                " Более 3500 выпускников.");
        resume.setContent(SectionType.QUALIFICATIONS, "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, " +
                "WebLogic, WSO2");
        resume.setContent(SectionType.QUALIFICATIONS, "Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        resume.setContent(SectionType.QUALIFICATIONS, "DB: PostgreSQL(наследование, pgplsql, PL/Python), " +
                "Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        resume.setContent(SectionType.EXPERIENCE, "Создание, организация и проведение Java онлайн проектов и " +
                "стажировок.");
        resume.setContent(SectionType.EDUCATION, "'Functional Programming Principles in Scala' by Martin Odersky");

        for (ContactType contactType : ContactType.values()) {
            System.out.println(contactType.getTitle() + ": " + "\n" + resume.getContent(contactType) + "\n");
        }

        for (SectionType sectionType : SectionType.values()) {
            System.out.println(sectionType.getTitle() + ": " + "\n" + resume.getContent(sectionType) + "\n");
        }
    }
}