<%
    String xml = "http://localhost:8080/demo_project/rest/api/people";
    String xsl = "WEB-INF/PetOwners.xsl";

    String transformer = "../common/transformer.jsp?xml=" + xml + "&xsl=" + xsl;
%>

<jsp:include page="<%= transformer %>"/>