<%@ page import="au.edu.uts.isd.iotbay.model.user.User" %>
<%@ page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil" %>
<%@ page import="au.edu.uts.isd.iotbay.Constants" %>
<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.category.CategoryRepository" %>
<%@ page import="au.edu.uts.isd.iotbay.model.category.Category" %>
<%@ page import="static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty" %>
<%@ page import="org.bson.types.ObjectId" %>
<%@ page import="au.edu.uts.isd.iotbay.model.product.Product" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.product.ProductRepository" %>
<%@ page import="javax.persistence.criteria.CriteriaBuilder" %>
<%@ page import="javax.persistence.criteria.Predicate" %>
<%@ page import="javax.persistence.criteria.CriteriaQuery" %>
<%@ page import="javax.persistence.criteria.Root" %>
<%@ page import="java.util.Collection" %><%--
  Created by IntelliJ IDEA.
  User: matt
  Date: 8/6/20
  Time: 1:49 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    User user = AuthenticationUtil.user(session);
    request.setAttribute("user", user);
    request.setAttribute("home", Constants.path(false));
    final IoTBayApplicationContext context = IoTBayApplicationContext.getInstance(application);
    final CategoryRepository repository = context.getCategories();
    final ProductRepository productRepository = context.getProducts();
    final String id = request.getParameter("id");
    final Category category;


    if (isNullOrEmpty(id) || !ObjectId.isValid(id)) {
        category = null;
    } else {
        category = repository.findById(id);
    }
    ObjectId objectId = new ObjectId(id);
    Collection<Product> match = productRepository.findAll(product -> {
        if (product.getCategories().contains(objectId)){
            return true;
        }
        return false;
    });
    request.setAttribute("match", match);
    request.setAttribute("category", category);

%>
<t:layout>
    <jsp:body>
        <div class="container">
            <c:if test="${category == null}">
                <div class="container p-3 my-3 bg-dark text-white">
                    <h1 style="text-align: center">Unable to find the category.</h1>
                    <br>
                    <p>We were unable to display the category. Try going back to the home page using the button bellow and then try find the category again.</p>
                    <a type="button" href="${home}" class="btn btn-light">Home</a>
                </div>
                <c:if test="${user.getRole().isStaff()}">
                    <div>
                        <a href="${home}/categories/create/">Create new category.</a>
                    </div>
                </c:if>
            </c:if>
            <c:if test="${category != null}">
                <div class="container p-3 my-3 bg-dark text-white">
                    <h1 style="text-align: center">${category.name}</h1>
                    <h3 style="text-align: center" >Products in Category: ${match.size()}</h3>
                </div>

                <div class="container p-3 my-3 bg-dark text-white">
                    <div class="box">
                        <h4>Category Description:</h4>
                        <div class="card">
                            <p class="text-body">${category.description}</p>
                        </div>
                        <br>
                        <h4>Category Products:</h4>
                        <div class="card-columns">
                            <c:forEach var="product" items="${match}">
                                <div class="card">
                                    <a href="${home}product/?id=${product.id}">
                                        <h4 style="text-align: center; color: black">${product.name}</h4>
                                        <p style="text-align: center; color: black">$${product.formatPrice()}</p>
                                    </a>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <c:if test="${user.getRole().isStaff()}">
                    <div>
                        <a href="${home}/categories/create">Create a new category.</a>
                        <a href="edit/?action=product&type=edit&id=${category.id}">Edit current category.</a>
                    </div>
                </c:if>
            </c:if>
        </div>
    </jsp:body>
</t:layout>
