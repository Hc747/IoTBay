<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--TODO: add login redirection--%>
<%--<%--%>
<%--    final String query = request.getQueryString();--%>
<%--    final StringBuilder endpoint = new StringBuilder("?");--%>

<%--    if (query == null) {--%>
<%--        endpoint.append("action=login");--%>
<%--    } else {--%>
<%--        if (query.contains("action=login")) {--%>
<%--            endpoint.append(query);--%>
<%--        } else {--%>
<%--            endpoint.append("action=login&").append(query);--%>
<%--        }--%>
<%--    }--%>

<%--    request.setAttribute("endpoint", endpoint.toString());--%>
<%--%>--%>
<t:layout>
    <jsp:body>
        <div class="container">
            <form action="?action=login" method="POST">
                <h1 class="jumbotron-heading" style="text-align: center">Login to your account</h1>
                <div class="form-group">
                    <label for="username">
                        Username
                    </label>
                    <input type="email" class="form-control" id="username" name="username" placeholder="Enter username / email address" value="${username}" required>
                </div>
                <div class="form-group">
                    <label for="password">
                        Password
                    </label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </jsp:body>
</t:layout>