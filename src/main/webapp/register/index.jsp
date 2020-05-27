<%@page import="au.edu.uts.isd.iotbay.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
    <jsp:body>
        <div class="container">
            <form action="?action=register" method="POST">
                <div class="form-group">
                    <label for="name">
                        Name
                    </label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Enter name" value="${name}" required>
                </div>
                <div class="form-group">
                    <label for="username">
                        Username / email address
                    </label>
                    <input type="email" class="form-control" id="username" name="username" placeholder="Enter username / email address" value="${username}" required>
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


