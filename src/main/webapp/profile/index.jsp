<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %>
<%@ page import="au.edu.uts.isd.iotbay.model.log.UserLog" %>
<%@ page import="au.edu.uts.isd.iotbay.model.user.User" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.log.UserLogRepository" %>
<%@ page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.time.Instant" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Date" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    User user = AuthenticationUtil.user(session);
    request.setAttribute("user", user);

    Date date;
    final String input = request.getParameter("date");

    if (isNullOrEmpty(input)) {
        date = Date.from(Instant.now());

    } else {
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        try {
            date = format.parse(input);
        } catch (Exception e) {
            date = Date.from(Instant.now());
        }
    }
    final IoTBayApplicationContext context = IoTBayApplicationContext.getInstance(application);
    final UserLogRepository repository = context.getUserLogs();
    final Collection<UserLog> userlogs = repository.findByUserBeforeDate(user, date);
    request.setAttribute("logs", userlogs);
    request.setAttribute("date", date);
%>
<t:layout>
    <jsp:body>

        <br><div class="container d-flex justify-content-between">
            <a class="btn btn-outline-secondary" href="payment/">View payment details.</a>
            <a class="btn btn-outline-secondary" href="payment/history/">View payment history.</a>
            <a class="btn btn-outline-secondary" href="passwordUpdate/">Update Password.</a>
<%--            <a href="userlogs/">View your activity logs.</a>--%>
        </div>
        <br>

        <div class="container d-flex justify-content-between">
            <form action="?action=user&type=update" method="post">
                <div class="form-group">
                    <h4>Account details</h4>
                </div>
                <div class="form-group">
                    <label for="name">Full Name: </label>
                    <input type="text" class="form-control" name="name" id="name" value="${user.name}">
                </div>
                <div class="form-group">
                    <label for="username">Email Address: </label>
                    <input type="email" class="form-control" name="username" id="username" value="${user.username}">
                </div>
                <div class="form-group">
                    <label for="phone">Phone Number: </label>
                    <input type="tel" class="form-control" name="phone" id="phone" value="${user.phone}">
                </div>
                <div class="form-group">
                    <label for="role">Role: </label>
                    <input type="text" class="form-control" id="role" readonly value="${user.role}">
                </div>
                <div class="form-group">
                    <p><small><b>Account created: </b>${user.created}</small></p>
                </div>
                <div class="form-group">
                    <label for="disable">Disable account: </label>
                    <input type="checkbox" name="disable" id="disable">
                </div>
                <div class="form-group">
                    <input class="submit" type="submit">
                </div>
            </form>
        </div>

        <br>
        <br>
        <div class="container d-flex justify-content-between">
            <form action="?date=${date}" method="get">
                <div class="form-group">
                    <h3>View activity logs</h3>
                    <label for="logDate">Up to date:</label>
                    <input id="logDate" name="date" type="date">
                </div>
                <div class="form-group">
                    <input class="submit" type="submit">
                </div>
            </form>
        </div>
        <div>
            <c:if test="${date != null}">
                <c:if test="${not logs.isEmpty()}">
                <div class="container">
                    <table class="table">
                    <thead>
                        <th scope="col">#</th>
                        <th scope="col">Type</th>
                        <th scope="col">Date</th>
                    </thead>
                        <tbody>
                    <c:forEach var="log" items="${logs}">
                        <tr>
                            <td>${log.id}</td>
                            <td>${log.type}</td>
                            <td>${log.date}</td>
                        </tr>
                    </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </c:if>
        </div>
    </jsp:body>
</t:layout>
