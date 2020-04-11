<%@page import="au.edu.uts.isd.iotbay.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
    <jsp:body>
        <div class="container">
            <form action="?action=login" method="POST">
                <div class="form-group">
                    <label for="username">
                        Username
                    </label>
                    <input type="email" class="form-control" id="username" name="username" placeholder="Enter username / email address" required>
                </div>
                <div class="form-group">
                    <label for="password">
                        Password
                    </label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
                </div>
                <div class="form-group form-check">
                    <input type="checkbox" class="form-check-input" id="remember" name="remember">
                    <label class="form-check-label" for="remember">Remember me</label>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </jsp:body>
</t:layout>