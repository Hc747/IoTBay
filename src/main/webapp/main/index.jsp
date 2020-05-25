<%@ page import="au.edu.uts.isd.iotbay.model.user.User" %>
<%@ page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil" %>
<%@ page import="au.edu.uts.isd.iotbay.Constants" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <jsp:include page="/WEB-INF/includes/common/handle_action.jsp"/>
    <jsp:include page="/WEB-INF/includes/common/head.jsp"/>
    <body>
        <jsp:include page="/WEB-INF/includes/common/header.jsp"/>
        <main role="main">
            <jsp:include page="/WEB-INF/includes/common/render_action.jsp"/>
            <jsp:include page="/WEB-INF/includes/common/banner.jsp"/>
            <%
                final User user = AuthenticationUtil.user(session);
            %>
            <% if (user == null) { %>
                You are not logged in.</br>
                Click <a href="<%= Constants.path(true, "register") %>">here</a> to register.
            <% } else { %>
                Logged in as: '<%= user.getUsername() %>' with id: '<%= user.getId() %>'</br>
                Your role is: <%= user.getRole().name() %></br>
                Your password is: <%= user.getPassword() %>
            <% } %>
        </main>
        <jsp:include page="/WEB-INF/includes/common/footer.jsp"/>
        <jsp:include page="/WEB-INF/includes/common/scripts.jsp"/>
    </body>
</html>
