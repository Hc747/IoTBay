<%@page import="au.edu.uts.wsd.Constants"%>
<%@page import="java.util.Optional"%>
<%
    Optional<String> renderer = Optional.ofNullable(request.getParameter("renderer"));
%>
<% if (renderer.isPresent()) { %>
    <% if (renderer.get().equalsIgnoreCase("xslt")) { %>
        <jsp:include page="render_xslt.jsp"/>
    <% } else if (renderer.get().equalsIgnoreCase("pojo")) { %>
        <jsp:include page="render_pojo.jsp"/>
    <% } %>
<% }%>