<%@page import="au.edu.uts.isd.iotbay.Constants"%>
<%@page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil"%>
<%
    final boolean authenticated = AuthenticationUtil.isAuthenticated(session);
    final String template = authenticated ? "authenticated" : "unauthenticated";
    final String header = String.format("../../../WEB-INF/includes/common/header/%s.jsp", template);
%>
<header>
    <div class="navbar navbar-dark bg-dark box-shadow">
        <div class="container d-flex justify-content-between">
            <a href="<%= Constants.path(false) %>" class="navbar-brand d-flex align-items-center">
                <strong>
                    <%
                        final StringBuilder location = new StringBuilder(Constants.APPLICATION_NAME);
                        final String current = request.getServletPath().replace(Constants.path(false), "");
                        final String[] segments = current.split("/");
                        
                        for (int index = 1; index < segments.length - 1; index++) {
                            location.append(" > ").append(segments[index]);
                        }
                    %>
                    <%= location %>
                </strong>
            </a>
            <div class="text-white">
                <jsp:include page="<%= header %>"/>
            </div>
        </div>
    </div>

</header>