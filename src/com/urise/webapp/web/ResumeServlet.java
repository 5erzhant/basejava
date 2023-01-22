package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r = null;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "add":
                r = new Resume("");
            case "view":
            case "edit":
                if (r == null) {
                    r = storage.get(uuid);
                }
                for (SectionType type : new SectionType[]{SectionType.EXPERIENCE, SectionType.EDUCATION}) {
                    CompanySection section = (CompanySection) r.getSection(type);
                    List<Company> emptyFirstCompanies = new ArrayList<>();
                    emptyFirstCompanies.add(Company.EMPTY);
                    if (section != null) {
                        for (Company company : section.getCompanies()) {
                            List<Period> emptyFirstPeriods = new ArrayList<>();
                            emptyFirstPeriods.add(Period.EMPTY);
                            emptyFirstPeriods.addAll(company.getPeriods());
                            Company newCompany = new Company(company.getName(), company.getWebsite());
                            newCompany.getPeriods().addAll(emptyFirstPeriods);
                            emptyFirstCompanies.add(newCompany);
                        }
                    }
                    CompanySection newCompanySection = new CompanySection();
                    newCompanySection.getCompanies().addAll(emptyFirstCompanies);
                    r.setSection(type, newCompanySection);
                }
                break;
            case "clear":
                storage.clear();
                response.sendRedirect("resume");
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        try {
            r = storage.get(uuid);
        } catch (Exception e) {
            r = new Resume("");
            storage.save(r);
        }
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        storage.update(r);
        response.sendRedirect("resume");
    }
}
