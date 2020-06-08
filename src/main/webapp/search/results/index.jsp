<%@ page import="au.edu.uts.isd.iotbay.Constants" %>
<%@ page import="au.edu.uts.isd.iotbay.model.product.Product" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.product.ProductRepository" %>
<%@ page import="java.util.Collection" %>
<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %><%--
  Created by IntelliJ IDEA.
  User: matt
  Date: 9/6/20
  Time: 3:29 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    request.setAttribute("home", Constants.path(false));
    String search = request.getParameter("search");
    final IoTBayApplicationContext context = IoTBayApplicationContext.getInstance(application);
    final ProductRepository productRepository = context.getProducts();
    Collection<Product> match = productRepository.findAll(product -> {
        if (product.getName().equalsIgnoreCase(search)){
            return true;
        }
        return false;
    });
    request.setAttribute("search", search);
    request.setAttribute("match", match);
%>
<t:layout>
    <jsp:body>
        <div class="container">
            <h1 style="text-align: center">${match.size()} Results for:</h1>
            <p style="text-align: center">${search}</p>
            <div class="row">
                <div class="col-sm-4">
                    <h4>Products:</h4>
                </div>
                <div class="col-sm-4">
                    <a style="float: right" type="button" href="${home}search/">New Search</a>
                </div>
                <div class="col-sm-4">
                    <a style="float: right" type="button" href="${home}">Home</a>
                </div>
            </div>
            <div class="card-columns">
                <c:forEach var="product" items="${match}">
                    <div class="card">
                        <a href="/iotbay/product/?id=${product.id}" style="align-content: center">
                            <h4 style="text-align: center; color: black">${product.name}</h4>
                            <p style="text-align: center; color: black">$${product.formatPrice()}</p>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </jsp:body>
</t:layout>

