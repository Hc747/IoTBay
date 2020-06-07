<%@ page import="au.edu.uts.isd.iotbay.Constants" %>
<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.product.ProductRepository" %>
<%@ page import="au.edu.uts.isd.iotbay.model.product.Product" %>
<%@ page import="org.bson.types.ObjectId" %>
<%@ page import="static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
  Created by IntelliJ IDEA.
  User: matt
  Date: 27/5/20
  Time: 9:46 pm
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%//TODO: Add actions to buttons
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
                    <p>ProductId: ${product.id}</p>
                    <div class="container p-3 my-3 bg-dark text-white">
                        <button type="button" class="btn btn-danger">Delete Product</button>
                    </div>
                </div>
                <div class="container p-3 my-3 bg-dark text-white" >
                    <h1 class="jumbotron-heading" style="text-align: center">Update Product Details</h1>
                    <form action="?action=product&type=update&id=${product.id}" method="post">
                        <div class="form-group">
                            <label for="name">
                                Product Name
                            </label>
                            <input id="name" name="name"type="text" placeholder="Product Name" required value="${product.name}">
                        </div>
                        <div class="form-group">
                            <label for="description">
                                Product Description
                            </label>
                            <input id="description" name="description" type="text" placeholder="Product Description" required value="${product.description}">
                        </div>
                        <div class="form-group">
                            <label for="quantity">
                                Product Quantity
                            </label>
                            <input id="quantity" name="quantity"type="number" placeholder="0" required value="${product.quantity}">
                        </div>
                        <div class="form-group">
                            <label for="price">
                                Product Price: $
                            </label>
                            <input id="price" name="price" type="number" placeholder="$0.00" required value="${product.price}">
                        </div>
                        <div class="form-group">
                            <label for="categories">
                                Product Categories
                            </label>
                            <input id="categories" name="categories" type="text" placeholder="Product Categories">
                        </div>
                        <div>
                            <button type="submit" class="btn btn-warning">Confirm Product Edit</button>
                            <a type="button" href="${home}" class="btn btn-light">Cancel</a>
                            <input type="reset" style="float: right">
                        </div>
                    </form>
                </div>
            </c:if>
        </div>
    </jsp:body>
</t:layout>
