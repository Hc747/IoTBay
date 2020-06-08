<%--
  Created by IntelliJ IDEA.
  User: matt
  Date: 27/5/20
  Time: 9:46 pm
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="au.edu.uts.isd.iotbay.Constants" %>
<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.category.CategoryRepository" %>
<%@ page import="au.edu.uts.isd.iotbay.model.category.Category" %>
<%@ page import="java.util.Collection" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    request.setAttribute("home", Constants.path(false));
    final IoTBayApplicationContext context = IoTBayApplicationContext.getInstance(application);
    final CategoryRepository categoryRepository = context.getCategories();
    final Collection<Category> categories = categoryRepository.all();
    request.setAttribute("categories", categories);
%>
<t:layout>
    <jsp:body>
        <div class="container p-3 my-3 bg-dark text-white">
            <h1 style="text-align: center">Create New Product</h1>
            <p>Please create the product using the table bellow. To assign a category to the product, create the product then edit the product to assign a category.</p>
            <br>
            <form action="?action=product&type=create" method="post">
                <div class="form-group">
                    <label for="name">
                        Product Name:
                    </label>
                    <input class="form-control" id="name" name="name"type="text" placeholder="Product Name" required>
                </div>
                <div class="form-group">
                    <label for="description">
                        Product Description:
                    </label>
                    <textarea class="form-control" name="description" id="description" rows="3" placeholder="Product Description" required></textarea>
                </div>
                <div class="form-group">
                    <label for="quantity">
                        Product Quantity:
                    </label>
                    <input class="form-control" id="quantity" name="quantity"type="number" placeholder="0" required>
                </div>
                <div class="form-group">
                    <label for="price">
                        Product Price:
                    </label>
                    <input class="form-control" id="price" name="price" step="0.01" type="number" placeholder="$0.00" required>
                </div>
                <div class="container p-3 my-3 bg-dark text-white">
                    <button type="submit" class="btn btn-warning">Confirm Product Create</button>
                    <a type="button" href="${home}" class="btn btn-light">Cancel</a>
                </div>
            </form>
        </div>
    </jsp:body>
</t:layout>
