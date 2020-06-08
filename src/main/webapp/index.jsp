<%@ page import="au.edu.uts.isd.iotbay.repository.product.ProductRepository" %>
<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.category.CategoryRepository" %>
<%@ page import="java.util.Collection" %>
<%@ page import="au.edu.uts.isd.iotbay.model.product.Product" %>
<%@ page import="java.text.DecimalFormat" %>
<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    final IoTBayApplicationContext context = IoTBayApplicationContext.getInstance(application);
    final ProductRepository productRepository = context.getProducts();
    final Collection<Product> products = productRepository.all();

    request.setAttribute("products", products);
%>
<t:layout>
    <jsp:body>
        <jsp:include page="/WEB-INF/includes/common/banner.jsp"/>
            <div class="container">
                <div class="row">
                    <div class="col-sm-6">
                        <h4>Products:</h4>
                    </div>
                    <div class="col-sm-6">
                        <a style="float: right" type="button" href="categories/">View Categories</a>
                    </div>
                </div>
                <div class="card-columns">
                    <c:forEach var="product" items="${products}">
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
