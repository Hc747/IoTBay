<%@ page import="au.edu.uts.isd.iotbay.model.user.User" %>
<%@ page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.sql.Time" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.time.Instant" %>
<%@ page import="static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.log.UserLogRepository" %>
<%@ page import="au.edu.uts.isd.iotbay.model.log.UserLog" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Optional" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
    User user = AuthenticationUtil.user(session);
    request.setAttribute("user", user);

    Date date;
    final String input = request.getParameter("date");

    if (isNullOrEmpty(input)) {
        date = Date.from(Instant.now());

    } else {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(input);
        } catch (Exception e) {
            date = Date.from(Instant.now());
        }
    }
    final IoTBayApplicationContext context = IoTBayApplicationContext.getInstance(application);
    final UserLogRepository repository = context.getUserLogs();
    final Collection<UserLog> userlogs = repository.whereDateLessThan(user, date);
    request.setAttribute("date", date);
%>
<t:layout>
    <jsp:body>
        <div class="container d-flex justify-content-between">
            <a href="payment/">View payment details.</a>
        </div>
        <div class="container d-flex justify-content-between">
            <a href="userlogs/">Your logs.</a>
        </div>
        <br>

        <div class="container d-flex justify-content-between">
            <form action="?action=user&type=update" method="post">
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
                    <input type="tel" class="form-control" id="role" readonly value="${user.role}">
                </div>
                <div class="form-group">
                    <p><small><b>Account created: </b>${user.created}</small></p>
                </div>
                <div class="form-group">
                    <p><small><b>Account verified: </b>${user.verified}</small></p>
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
        <div class="container d-flex justify-content-between">
            <form action="?date=${nowDate}" method="get">
                <div class="form-group">
                    <h3>View activity logs</h3>
                    <label for="logDate">Up to date:</label>
                    <input id="logDate" name="date" type="date" value="${nowDate}">
                </div>
                <div class="form-group">
                    <input class="submit" type="submit">
                </div>
            </form>
        </div>
        <div>
            <c:if test="${not input.isEmpty()}">
                <c:if test="${not userlogs.isEmpty()}">
                <div class="container">
                    <div class="row">
                        <div class="col-1">#</div>
                        <div class="col-1">Type</div>
                        <div class="col-1">Timestamp</div>
                    </div>
                    <c:forEach var="method" items="${methods}">
                        <div class="row">
                            <div class="col-1">${userlogs.id}</div>
                            <div class="col-1">${userlogs.type}</div>
                            <div class="col-1">${method.timestamp}</div>
                        </div>
                    </c:forEach>
                </c:if>
            </c:if>
        </div>
    </jsp:body>
</t:layout>
