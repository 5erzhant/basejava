package com.urise.webapp.web;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        SqlStorage sqlStorage = (SqlStorage) Config.get().getStorage();
        SqlStorage sqlStorage = new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "postgres");
        List<Resume> resumes = sqlStorage.getAllSorted();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        String name = request.getParameter("name");
//        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + '!');
        response.getWriter().write("<style>" +
                "table, th, td {" +
                "border:2px solid black;" +
                "}" +
                "</style>" +
                "<table style=\"width:70%\">" +
                "<tr>" +
                "<th>UUID</th>" +
                "<th>Full Name</th>" +
                "</tr>");
        for (Resume resume : resumes) {
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
