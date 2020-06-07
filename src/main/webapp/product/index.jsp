<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %>
<%@ page import="javafx.application.Application" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.product.ProductRepository" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
    final IoTBayApplicationContext context = IoTBayApplicationContext.getInstance(application);
    final ProductRepository repository = context.getProducts();
%>
<t:layout>
    <jsp:body>
        <br>
        <div class="container p-3 my-3 bg-dark text-white">
        <h1 style="text-align: center">Product Title</h1>
            <div>
                <h3 style="text-align: left" >$ Product Price</h3>
            </div>
            <div style="align-self: end">
                <button type="button" class="btn btn-primary" style="right: auto">Add To Cart</button>
            </div>
        </div>

        <div class="container p-3 my-3 bg-dark text-white">
            <div class="box">
                <h4>Product Description:</h4>
                <div class="card">
                    <p class="text-body">Bordered Box around me</p>
                </div>
                <br>
                <h4>Product Categories:</h4>
                <div class="card">
                    <p class="text-body">Will implement mechanism to search by catagory.</p>
                </div>
            </div>
        </div>

    </jsp:body>
</t:layout>