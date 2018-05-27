<%@page import="java.util.Optional"%>
<%    
    String xml = Optional.ofNullable(request.getParameter("xml")).orElseThrow(() -> new Exception("You must supply an 'xml' parameter."));
    String xsl = Optional.ofNullable(request.getParameter("xsl")).orElseThrow(() -> new Exception("You must supply an 'xsl' parameter."));
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<c:set var="xml" value="<%=xml%>"/>

<c:set var="xsl" value="<%=xsl%>"/>

<c:import url="${xml}" var="document"/>

<c:import url="${xsl}" var="stylesheet"/>

<x:transform xml="${document}" xslt="${stylesheet}"/>