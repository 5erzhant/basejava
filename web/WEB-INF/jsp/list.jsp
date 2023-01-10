<%@ page import="java.util.List" %>
<%@ page import="com.urise.webapp.model.Resume" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
    </style>
</head>
<body>
<section>
    <table>
        <tr>
            <th>uuid</th>
            <th>Full name</th>
        </tr>
        <%
            for (Resume resume : (List<Resume>) request.getAttribute("resumes")) {
        %>
        <tr>
            <td><%=resume.getUuid()%>
            </td>
            <td><%=resume.getFullName()%>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</section>
</body>
</html>
