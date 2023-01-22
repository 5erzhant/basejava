<%@ page import="com.urise.webapp.model.CompanySection" %>
<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 required value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
            <dl>
                <dt><b>${type.title}</b></dt>
                <dd>
                    <c:choose>
                        <c:when test="${type=='PERSONAL' || type=='OBJECTIVE'}">
                            <textarea cols="30" rows="10" name="${type}"><%=section%></textarea>
                        </c:when>
                        <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                            <textarea cols="30" rows="10"
                                      name="${type}"><%=String.join("\n", ((ListSection) section).getContentList())%></textarea>
                        </c:when>
                        <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                            <c:forEach var="company" items="<%=((CompanySection) section).getCompanies()%>"
                                       varStatus="counter">
                                <dl>
                                    <dt>Название учереждения:</dt>
                                    <dd><input type="text" name='${type}' size=100 value="${company.name}"></dd>
                                </dl>
                                <dl>
                                    <dt>Сайт учереждения:</dt>
                                    <dd><input type="text" name='${type}url' size=100 value="${company.website}"></dd>
                                    </dd>
                                </dl>
                                <br>
                                <div style="margin-left: 30px">
                                    <c:forEach var="period" items="${company.periods}">
                                        <jsp:useBean id="period"
                                                     type="com.urise.webapp.model.Period"/>
                                        <dl>
                                            <dt>Начальная дата:</dt>
                                            <dd>
                                                <input type="text" name="${type}${counter.index}startDate" size=10
                                                       value="<%=DateUtil.format(period.getBegin())%>"
                                                       placeholder="MM/yyyy">
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt>Конечная дата:</dt>
                                            <dd>
                                                <input type="text" name="${type}${counter.index}endDate" size=10
                                                       value="<%=DateUtil.format(period.getEnd())%>"
                                                       placeholder="MM/yyyy">
                                        </dl>
                                        <dl>
                                            <dt>Должность:</dt>
                                            <dd><input type="text" name='${type}${counter.index}title' size=75
                                                       value="${period.title}">
                                        </dl>
                                        <dl>
                                            <dt>Описание:</dt>
                                            <dd><textarea name="${type}${counter.index}description" rows=5
                                                          cols=75>${period.description}</textarea></dd>
                                        </dl>
                                    </c:forEach>
                                </div>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </dd>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>