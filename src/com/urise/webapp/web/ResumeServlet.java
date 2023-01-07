package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    private Storage sqlStorage;

    private void initSql() {
        sqlStorage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        initSql();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write("<style>" +
                "table, th, td {" +
                "border:2px solid black;" +
                "}" +
                "</style>" +
                "<table style=\"width:70%\">" +
                "<tr>" +
                "<th>uuid</th>" +
                "<th>Имя</th>" +
                "</tr>");
        for (Resume resume : sqlStorage.getAllSorted()) {
            response.getWriter().write("<tr>" +
                                          " <td>" + resume.getUuid() + "    </td>" +
                    "                       <td>" + resume.getFullName() + "</td>" +
                    "                      </tr>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
