<%--
  Created by IntelliJ IDEA.
  User: matt
  Date: 27/5/20
  Time: 9:46 pm
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%//TODO: Add actions to buttons%>
<t:layout>
    <jsp:body>
        <div class="container p-3 my-3 bg-dark text-white">
            <h1 class="jumbotron-heading" style="text-align: center">Product Title</h1>
            <p>ProductId: 123421321</p>
            <div class="container p-3 my-3 bg-dark text-white">
                <button style="float: right" type="button" class="btn btn-warning">Confirm Product Edit</button>
                <button style="float: right" type="button" class="btn btn-danger">Delete Product</button>
                <button style="float: right" type="button" class="btn btn-light">Cancel</button>
            </div>
        </div>
        <hr>
        <div class="container p-3 my-3 border" >
            <form method="post">
                <div class="form-group">
                    <label for="productName">
                        Product Name
                    </label>
                    <input id="productName" name="productName"type="text" placeholder="Product Name" required>
                </div>
                <div class="form-group">
                    <label for="productDescription">
                        Product Description
                    </label>
                    <input id="productDescription" name="productDescription" type="text" placeholder="Product Description" required>
                </div>
                <div class="form-group">
                    <label for="productQuantity">
                        Product Quantity
                    </label>
                    <input id="productQuantity" name="productQuantity"type="number" placeholder="0" required>
                </div>
                <div class="form-group">
                    <label for="productName">
                        Product Quantity: $
                    </label>
                    <input id="productPrice" name="productPrice" type="number" placeholder="$0.00" required>
                </div>
                <div class="form-group">
                    <label for="productImageURL">
                        Product Image URL
                    </label>
                    <input id="productImageURL" name="productImageURL" type="url" placeholder="Product Image URL's">
                </div>
                <div class="form-group">
                    <label for="productCategories">
                        Product Categories
                    </label>
                    <input id="productCategories" name="productCategories" type="text" placeholder="Product Categories">
                </div>
                <button type="submit" class="btn btn-warning">Confirm Product Edit</button>
            </form>
        </div>
    </jsp:body>
</t:layout>
