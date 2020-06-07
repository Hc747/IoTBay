<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %>
<%@ page import="javafx.application.Application" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.product.ProductRepository" %>
<%@ page import="au.edu.uts.isd.iotbay.model.user.User" %>
<%@ page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil" %>
<%@ page import="static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty" %>
<%@ page import="au.edu.uts.isd.iotbay.model.product.Product" %>
<%@ page import="au.edu.uts.isd.iotbay.util.Misc" %>
<%@ page import="org.bson.types.ObjectId" %>
<%@ page import="au.edu.uts.isd.iotbay.Constants" %>
<%@ page import="au.edu.uts.isd.iotbay.model.category.Category" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    request.setAttribute("home", Constants.path(false));
    final IoTBayApplicationContext context = IoTBayApplicationContext.getInstance(application);
    final ProductRepository repository = context.getProducts();
    final String id = request.getParameter("id");
    final Product product;

    if (isNullOrEmpty(id) || !ObjectId.isValid(id)) {
        product = null;
    } else {
        product = repository.findById(id);
    }

    request.setAttribute("product", product);
%>
<t:layout>
    <jsp:body>
        <div class="container">
            <c:if test="${product == null}">
                <div class="container p-3 my-3 bg-dark text-white">
                    <h1 style="text-align: center">Unable to find the product.</h1>
                    <br>
                    <p>We were unable to display the product. Try going back to the home page using the button bellow and then try find the product again.</p>
                    <a type="button" href="${home}" class="btn btn-light">Home</a>
                </div>
            </c:if>
            <c:if test="${product != null}">
                <div class="container p-3 my-3 bg-dark text-white">
                    <h1 style="text-align: center">${product.name}</h1>
                    <div>
                        <h3 style="text-align: left" >$${product.price}</h3>
                    </div>
                    <div style="align-self: end">
                        <button type="button" class="btn btn-primary" style="right: auto">Add To Cart</button>
                    </div>
                </div>

                <div class="container p-3 my-3 bg-dark text-white">
                    <div class="box">
                        <h4>Product Description:</h4>
                        <div class="card">
                            <p class="text-body">${product.description}</p>
                        </div>
                        <br>
                        <h4>Product Categories:</h4>
                            <c:forEach var="category" items="${product.categories}">
                                <div class="card">
                                    <a href="/category/?id=${category.id}">${category.name}</a>
                                </div>
                            </c:forEach>
                    </div>
                </div>
                <div>
                    <a href="create/">Create new product.</a>
                    <a href="edit/?action=product&type=edit&id=${product.id}">Edit current product.</a>
                </div>
            </c:if>
        </div>

    </jsp:body>
</t:layout>