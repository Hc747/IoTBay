<%@ page import="au.edu.uts.isd.iotbay.action.ActionProcessor" %>
<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %>
<%
    final IoTBayApplicationContext context = IoTBayApplicationContext.getInstance(application);
    final ActionProcessor processor = context.getProcessor();
    processor.process(request, response);
%>