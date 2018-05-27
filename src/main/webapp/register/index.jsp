<%@page import="au.edu.uts.wsd.action.impl.RegisterAction"%>
<%@page import="au.edu.uts.wsd.action.Action"%>
<%@page import="au.edu.uts.wsd.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if ("register".equals(request.getParameter("action"))) {
        request.setAttribute(Action.KEY, new RegisterAction());
    }
%>
<!DOCTYPE html>
<html lang="en">

    <jsp:include page="../WEB-INF/includes/common/handle_action.jsp"/>
    
    <jsp:include page="../WEB-INF/includes/common/head.jsp"/>

    <body>

        <jsp:include page="../WEB-INF/includes/common/header.jsp"/>

        <main role="main">

            <jsp:include page="../WEB-INF/includes/common/info.jsp"/>

            <div class="container">
                
                <jsp:include page="../WEB-INF/includes/common/render_action.jsp"/>
                
                <form action="<%= Constants.BASE_URL %>register/?action=register" method="POST">
                    <div class="form-group">
                        <label for="name">
                            Name
                        </label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="Enter name" required>
                    </div>
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
