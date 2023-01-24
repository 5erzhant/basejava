<%@ page import="com.urise.webapp.model.CompanySection" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <table>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%>
            <br>
        </c:forEach>
        <hr>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
            <jsp:useBean id="type" type="com.urise.webapp.model.SectionType"/>
            <tr>
                <td colspan="2"><h3><a name="type.name">${type.title}</a></h3></td>
            </tr>
            <c:choose>
                <c:when test="${type=='PERSONAL' || type=='OBJECTIVE'}">
                    <tr>
                        <td colspan="2">
                            <h4><%=((TextSection) section).getContent() %>
                            </h4>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                    <tr>
                        <td colspan="2">
                            <ul>
                                <c:forEach var="item" items="<%=((ListSection)section).getContentList()%>">
                                    <li>${item}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="company" items="<%=((CompanySection)section).getCompanies()%>">
                        <tr>
                            <td></td>
                            <td>
                                <c:choose>
                                    <c:when test="${company.website==null}">
                                        <br><h4>${company.name}</h4>
                                    </c:when>
                                    <c:otherwise>
                                        <br><h4><a href="${company.website}">${company.name}</a></h4>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:forEach var="period" items="${company.periods}">
                            <jsp:useBean id="period" type="com.urise.webapp.model.Period"/>
                            <tr>
                                <td width="25%" style="vertical-align:top"><%=HtmlUtil.formatDates(period)%>
                                </td>
                                <td><b>${period.title}</b><br>${period.description}
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>