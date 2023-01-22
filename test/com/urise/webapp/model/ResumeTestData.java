package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;

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

        String achievment1 = "Achievment1";
        String achievment2 = "Achievment2";
        String achievment3 = "Achievment3";
        ListSection achievmentList = new ListSection();
        achievmentList.addContent(achievment1);
        achievmentList.addContent(achievment2);
        achievmentList.addContent(achievment3);

        String qualifications1 = "Qualification1";
        String qualifications2 = "Qualification2";
        String qualifications3 = "Qualification3";
        ListSection qualificationList = new ListSection();
        qualificationList.addContent(qualifications1);
        qualificationList.addContent(qualifications2);
        qualificationList.addContent(qualifications3);

        LocalDate begin = LocalDate.of(2013, 10, 1);
        LocalDate end = LocalDate.now();
        String title = "Автор проекта 1";
        String description1 = "Description1";
        Period period1 = new Period(begin, end, title, description1);
        String name1 = "Java Online Projects";
        String website = "https://javaops.ru/";
        Company company1 = new Company(name1, website);

        LocalDate begin2 = LocalDate.of(2014, 10, 1);
        LocalDate end2 = LocalDate.of(2016, 1, 1);
        String title2 = "Старший разработчик";
        String description2 = "Description2";
        Period period2 = new Period(begin2, end2, title2, description2);
        String name2 = "Wrike";
        String website2 = "https://www.wrike.com/";
        Company company2 = new Company(name2, website2);

        company1.addPeriod(period1);
        company1.addPeriod(period1);
        company2.addPeriod(period2);
        company2.addPeriod(period2);

        CompanySection companySection = new CompanySection();
        companySection.addCompany(company1);
        companySection.addCompany(company2);

        LocalDate begin3 = DateUtil.of(2013, Month.MARCH);
        LocalDate end3 = DateUtil.of(2013, Month.MAY);
        String description3 = "Description3";
        Period educationPeriod = new Period(begin3, end3, "", description3);
        String edName = "Coursera";
        String edWebsite = "https://www.coursera.org/learn/scala-functional-programming";

        Company edCompany = new Company(edName, edWebsite);
        edCompany.addPeriod(educationPeriod);
        CompanySection edCompanySection = new CompanySection();
        edCompanySection.addCompany(edCompany);

        Resume resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.PHONE, mobile);
        resume.addContact(ContactType.SKYPE, skype);
        resume.addContact(ContactType.MAIL, mail);
        resume.addContact(ContactType.LINKEDIN, linkedin);
        resume.addContact(ContactType.GITHUB, github);
        resume.addContact(ContactType.STACKOVERFLOW, stackoverflow);
        resume.addContact(ContactType.HOME_PAGE, homepage);
        resume.setSection(SectionType.PERSONAL, personalSection);
        resume.setSection(SectionType.OBJECTIVE, objectiveSection);
        resume.setSection(SectionType.ACHIEVEMENT, achievmentList);
        resume.setSection(SectionType.QUALIFICATIONS, qualificationList);
        resume.setSection(SectionType.EXPERIENCE, companySection);
        resume.setSection(SectionType.EDUCATION, edCompanySection);
        return resume;
    }
}