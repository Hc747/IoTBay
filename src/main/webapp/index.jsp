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
    final DecimalFormat priceFormat = new DecimalFormat("###,##0.00");
%>
<t:layout>
    <jsp:body>
        <jsp:include page="/WEB-INF/includes/common/banner.jsp"/>
            <div class="container">
                <div class="card-columns">
                    <div></div>
                    <c:forEach var="product" items="${products}">
                        <div class="card">
                            <a href="/iotbay/product/?id=${product.id}" style="align-content: center">
                                    <h4 style="text-align: center; color: black">${product.name}</h4>
                                    <p style="text-align: center; color: black">${product.price}</p>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>
    </jsp:body>
</t:layout>
