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
        <br>

        <div class="container p-3 my-3 bg-dark text-white">
            <h1 class="jumbotron-heading" style="text-align: center">Create New Product</h1>
            <br>
            <form action="?action=product&type=create" method="post">
                <div class="form-group">
                    <label for="name">
                        Product Name
                    </label>
                    <input id="name" name="name"type="text" placeholder="Product Name" required>
                </div>
                <div class="form-group">
                    <label for="description">
                        Product Description
                    </label>
                    <input id="description" name="description" type="text" placeholder="Product Description" required>
                </div>
                <div class="form-group">
                    <label for="quantity">
                        Product Quantity
                    </label>
                    <input id="quantity" name="quantity"type="number" placeholder="0" required>
                </div>
                <div class="form-group">
                    <label for="price">
                        Product Price: $
                    </label>
                    <input id="price" name="price" type="number" placeholder="$0.00" required>
                </div>
                <div class="form-group">
                    <label for="imageURL">
                        Product Image URL
                    </label>
                    <input id="imageURL" name="imageURL" type="url" placeholder="Product Image URL's">
                </div>
                <div class="form-group">
                    <label for="categories">
                        Product Categories
                    </label>
                    <input id="categories" name="categories" type="text" placeholder="Product Categories">
                </div>
                <div class="container p-3 my-3 bg-dark text-white">
                    <button type="submit" class="btn btn-warning">Confirm Product Create</button>
                    <button type="button" class="btn btn-light">Cancel</button>
                </div>
            </form>
        </div>
    </jsp:body>
</t:layout>
