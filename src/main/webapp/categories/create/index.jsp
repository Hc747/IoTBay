<%@ page import="au.edu.uts.isd.iotbay.Constants" %>
<%@ page import="au.edu.uts.isd.iotbay.model.product.Product" %>
<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.product.ProductRepository" %>
<%@ page import="java.util.Collection" %><%--
  Created by IntelliJ IDEA.
  User: matt
  Date: 8/6/20
  Time: 1:51 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    request.setAttribute("home", Constants.path(false));
    final IoTBayApplicationContext context = IoTBayApplicationContext.getInstance(application);
    final ProductRepository productRepository = context.getProducts();
    final Collection<Product> products = productRepository.all();
    request.setAttribute("products", products);
%>
<t:layout>
    <jsp:body>
        <div class="container p-3 my-3 bg-dark text-white">
            <h1 style="text-align: center">Create New Category</h1>
            <br>
            <form action="?action=category&type=create" method="post">
                <div class="form-group">
                    <label for="name">
                        Category Name:
                    </label>
                    <input class="form-control" id="name" name="name"type="text" placeholder="Category Name" required>
                </div>
                <div class="form-group">
                    <label for="description">
                        Category Description:
                    </label>
                    <textarea class="form-control" name="description" id="description" rows="3" placeholder="Category Description" required></textarea>
                </div>
                <div class="form-group">
                    <label for="radio">Category Status:</label>
                    <div id="radio">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" id="true" name="enabled" value="true">
                            <label class="form-check-label" for="true">
                                Enabled
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" id="false" name="enabled" value="false" checked>
                            <label class="form-check-label" for="false">
                                Disabled
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="products">
                        Category products:
                    </label>
                    <select multiple class="form-control" id="products" name="products" >
                        <c:forEach var="product" items="${products}">
                            <option value="${product.id}">${product.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="container p-3 my-3 bg-dark text-white">
                    <button type="submit" class="btn btn-warning">Confirm Product Create</button>
                    <a type="button" href="${home}" class="btn btn-light">Cancel</a>
                </div>
            </form>
        </div>
    </jsp:body>
</t:layout>
