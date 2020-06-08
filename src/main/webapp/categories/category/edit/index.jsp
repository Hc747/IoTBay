<%@ page import="au.edu.uts.isd.iotbay.repository.category.CategoryRepository" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.product.ProductRepository" %>
<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %>
<%@ page import="static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty" %>
<%@ page import="au.edu.uts.isd.iotbay.Constants" %>
<%@ page import="au.edu.uts.isd.iotbay.model.product.Product" %>
<%@ page import="au.edu.uts.isd.iotbay.model.category.Category" %>
<%@ page import="java.util.Collection" %>
<%@ page import="org.bson.types.ObjectId" %><%--
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
    final CategoryRepository repository = context.getCategories();
    final ProductRepository productRepository = context.getProducts();
    final String id = request.getParameter("id");
    final Category category;
    final Collection<Product> products = productRepository.all();

    if (isNullOrEmpty(id) || !ObjectId.isValid(id)) {
        category = null;
    } else {
        category = repository.findById(id);
    }
    request.setAttribute("products", products);
    request.setAttribute("category", category);
%>
<t:layout>
    <jsp:body>
        <div class="container">
            <c:if test="${category == null}">
                <div class="container p-3 my-3 bg-dark text-white">
                    <h1 style="text-align: center">Unable to find the Category.</h1>
                    <br>
                    <p>We were unable to display the category. Try going back to the home page using the button bellow and then try find the category again.</p>
                    <a type="button" href="${home}" class="btn btn-light">Home</a>
                </div>
            </c:if>
            <c:if test="${category != null}">
                <div class="container p-3 my-3 bg-dark text-white">
                    <h1 style="text-align: center">${category.name}</h1>
                    <p style="text-align: center">CategoryId: ${category.id}</p>
                    <div class="container p-3 my-3 bg-dark text-white">
                        <div class="row">
                            <div class="col-sm-4">
                                <button style="justify-content: center" type="button" class="btn btn-danger" data-toggle="modal" data-target="#confirmDeleteCategory">Delete Category</button>
                            </div>
                            <div class="col-sm-4">
                                <a type="button" class="btn btn-light" href="${home}/categories/category/?id=${category.id}">Return to Category Page</a>
                            </div>
                            <div class="col-sm-4">
                                <a type="button" class="btn btn-light" href="${home}/categories/category/edit/?action=category&type=edit&id=${category.id}">Reload Form Values</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container p-3 my-3 bg-dark text-white">
                    <h1 style="text-align: center">Update Category Details</h1>
                    <p>Please reload the form values using the button above after updating the category before making further category updates.</p>
                    <form action="?action=category&type=update&id=${category.id}" method="post">
                        <div class="form-group">
                            <label for="name">
                                Category Name:
                            </label>
                            <input class="form-control" id="name" name="name"type="text" placeholder="Category Name" required value="${category.name}">
                        </div>
                        <div class="form-group">
                            <label for="description">
                                Category Description:
                            </label>
                            <textarea class="form-control" name="description" id="description" rows="3" placeholder="Category Description" required>${category.description}</textarea>
                        </div>
                        <c:if test="${category.enabled == true}">
                            <div class="form-group">
                                <label for="radio">Category Status:</label>
                                <div id="radio">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" id="true" name="enabled" value="true" checked>
                                        <label class="form-check-label" for="true">
                                            Enabled
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" id="false" name="enabled" value="false">
                                        <label class="form-check-label" for="false">
                                            Disabled
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${category.enabled == false}">
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
                        </c:if>
                        <div class="form-group">
                            <label for="products">
                                Category products:
                            </label>
                            <select multiple class="form-control" id="products" name="products" >
                                <c:forEach var="product" items="${products}">
                                    <c:if test="${category.products.contains(product.id) == true}">
                                        <option selected value="${product.id}">${product.name}</option>
                                    </c:if>
                                    <c:if test="${category.products.contains(product.id) == false}">
                                        <option value="${product.id}">${product.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="container p-3 my-3 bg-dark text-white">
                            <button type="submit" class="btn btn-warning">Confirm Category Edit</button>
                            <a type="button" href="${home}" class="btn btn-light">Cancel</a>
                        </div>
                    </form>
                </div>
            </c:if>
        </div>
        <!-- Confirm Window - Source: https://getbootstrap.com/docs/4.0/components/modal/ -->
        <div class="modal fade" id="confirmDeleteCategory" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteCategory" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmDeleteCategoryLabel">Are you sure you want to delete this category?</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        This will delete the category from the IoTBay inventory.<br>
                        This action can not be undone.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <form action="${home}/categories/category/edit/?action=category&type=delete&id=${category.id}" method="post">
                            <button type="submit" class="btn btn-danger">Confirm Delete</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:layout>
