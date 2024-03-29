<%@ page import="au.edu.uts.isd.iotbay.Constants" %>
<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.product.ProductRepository" %>
<%@ page import="au.edu.uts.isd.iotbay.model.product.Product" %>
<%@ page import="org.bson.types.ObjectId" %>
<%@ page import="static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.category.CategoryRepository" %>
<%@ page import="java.util.Collection" %>
<%@ page import="au.edu.uts.isd.iotbay.model.category.Category" %>
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
    final CategoryRepository catalogueRepository = context.getCategories();
    final String id = request.getParameter("id");
    final Product product;
    final Collection<Category> categories = catalogueRepository.all();

    if (isNullOrEmpty(id) || !ObjectId.isValid(id)) {
        product = null;
    } else {
        product = repository.findById(id);
    }
    request.setAttribute("categories", categories);
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
                    <p style="text-align: center">ProductId: ${product.id}</p>
                    <div class="container p-3 my-3 bg-dark text-white">
                        <div class="row">
                            <div class="col-sm-4">
                                <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#confirmDeleteProduct">Delete Product</button>
                            </div>
                            <div class="col-sm-4">
                                <a type="button" class="btn btn-light" href="/iotbay/product/?id=${product.id}">Return to Product Page</a>
                            </div>
                            <div class="col-sm-4">
                                <a type="button" class="btn btn-light" href="/iotbay/product/edit/?action=product&type=edit&id=${product.id}">Reload Form Values</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container p-3 my-3 bg-dark text-white" >
                    <h1 style="text-align: center">Update Product Details</h1>
                    <p>Please reload the form values using the button above after updating the product before making further product updates.</p>
                    <form action="?action=product&type=update&id=${product.id}" method="post">
                        <div class="form-group">
                            <label for="name">
                                Product Name:
                            </label>
                            <input class="form-control" id="name" name="name"type="text" placeholder="Product Name" required value="${product.name}">
                        </div>
                        <div class="form-group">
                            <label for="description">
                                Product Description:
                            </label>
                            <textarea class="form-control" id="description" name="description" rows="3" placeholder="ProductDescription" required>${product.description}</textarea>
<%--                            <input class="form-control" id="description" name="description" type="text" placeholder="Product Description" required value="${product.description}">--%>
                        </div>
                        <div class="form-group">
                            <label for="quantity">
                                Product Quantity:
                            </label>
                            <input class="form-control" id="quantity" name="quantity"type="number" placeholder="0" required value="${product.quantity}">
                        </div>
                        <div class="form-group">
                            <label for="price">
                                Product Price:
                            </label>
                            <input class="form-control" id="price" name="price" type="number" step="0.01" placeholder="$0.00" required value="${product.price}">
                        </div>
                        <div class="form-group">
                            <label for="categories">
                                Product Categories:
                            </label>
                            <select multiple class="form-control" id="categories" name="categories">
                                <c:forEach var="category" items="${categories}">
                                    <c:if test="${product.categories.contains(category.id) == true}">
                                        <option selected value="${category.id}">${category.name}</option>
                                    </c:if>
                                    <c:if test="${product.categories.contains(category.id) == false}">
                                        <option value="${category.id}">${category.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
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
        <!-- Confirm Window - Source: https://getbootstrap.com/docs/4.0/components/modal/ -->
        <div class="modal fade" id="confirmDeleteProduct" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteProduct" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmDeleteProductLabel">Are you sure you want to delete this product?</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        This will delete the product from the IoTBay inventory.<br>
                        This action can not be undone.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <form action="/iotbay/product/edit/?action=product&type=delete&id=${product.id}" method="post">
                            <button type="submit" class="btn btn-danger">Confirm Delete</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:layout>
