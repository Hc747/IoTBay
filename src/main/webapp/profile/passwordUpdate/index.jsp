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
<t:layout>
    <jsp:body>
<html>
<head>
    <form action="?action=password" method="POST">
        <h1 class="jumbotron-heading" style="text-align: center">Create an account</h1>
        <div class="form-group">
            <label for="cPass">
                Current Password
            </label>
            <input type="password" class="form-control" id="cPass" name="cPass" required>
        </div>
        <div class="form-group">
            <label for="nPass">
                New password
            </label>
            <input type="password" class="form-control" id="nPass" name="nPass" required>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    </div>
</head>
<body>

</body>
</html>
    </jsp:body>
</t:layout>