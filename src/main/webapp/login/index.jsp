<%@page import="au.edu.uts.wsd.action.impl.LoginAction"%>
<%@page import="au.edu.uts.wsd.action.Action"%>
<%@page import="au.edu.uts.wsd.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <jsp:include page="../WEB-INF/includes/common/head.jsp"/>

    <body>

        <jsp:include page="../WEB-INF/includes/common/header.jsp"/>

        <main role="main">

            <jsp:include page="../WEB-INF/includes/common/info.jsp"/>

            <div class="container">
                
                <%                     
                    if ("login".equals(request.getParameter("action"))) {
                        Action action = new LoginAction();
                        
                        try {
                            action.invoke(application, session, request, response);
                            %>
                                <div class="alert alert-success" role="alert">
                                    Login Successful. Click <a href="<%= Constants.BASE_URL %>">here</a> to get back to the main page.
                                </div>
                            <%
                        } catch (Exception e) {
                            %>
                                <div class="alert alert-warning" role="alert">
                                    Warning: <%= e.getMessage() %>
                                </div>
                            <%
                        }
                    }
                %>
                
                <form action="<%= Constants.BASE_URL %>/login/?action=login" method="POST">
                    <div class="form-group">
                        <label for="email">
                            Email address
                        </label>
                        <input type="email" class="form-control" id="email" name="email" placeholder="Enter email" required>
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

        </main>

        <jsp:include page="../WEB-INF/includes/common/footer.jsp"/>

        <jsp:include page="../WEB-INF/includes/common/scripts.jsp"/>

    </body>
</html>
