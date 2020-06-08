<%@ page import="au.edu.uts.isd.iotbay.model.category.Category" %>
<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.category.CategoryRepository" %>
<%@ page import="java.util.Collection" %>
<%@ page import="au.edu.uts.isd.iotbay.Constants" %>
<%@ page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil" %>
<%@ page import="au.edu.uts.isd.iotbay.model.user.User" %><%--
  Created by IntelliJ IDEA.
  User: matt
  Date: 8/6/20
  Time: 1:49 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    User user = AuthenticationUtil.user(session);
    request.setAttribute("user", user);
    request.setAttribute("home", Constants.path(false));
    final IoTBayApplicationContext context = IoTBayApplicationContext.getInstance(application);
    final CategoryRepository productRepository = context.getCategories();
    final Collection<Category> categories = productRepository.all();
    request.setAttribute("categories", categories);
%>
<t:layout>
    <jsp:body>
        <div class="container">
            <div class="container p-3 my-3 bg-dark text-white">
                <h1 style="text-align: center">IoTBay Categories</h1>
                <h4 style="text-align: center" >Number of Categories: ${categories.size()}</h4>
            </div>
            <div class="card-columns">
                <c:forEach var="category" items="${categories}">
                    <div class="card">
                        <a href="${home}categories/category/?id=${category.id}" style="align-content: center">
                            <h4 style="text-align: center; color: black">${category.name}</h4>
                            <p style="text-align: center; color: black">${category.products.size()} items</p>
                        </a>
                    </div>
                </c:forEach>
            </div>
            <c:if test="${user.getRole().isStaff()}">
                <div>
                    <a href="/create">Create a new category.</a>
                </div>
            </c:if>
        </div>
    </jsp:body>
</t:layout>
