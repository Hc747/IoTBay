<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 23/05/2020
  Time: 6:07 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
    <jsp:body>
        <h1 class="jumbotron-heading" style="text-align: center">Orders</h1>
        <div class="container">
            <form action="?action=order" method="POST">
            <div class="form-group">
                <label>
                    Order ID:
                </label>
                <input type="number" name="OrderID" class="form-control">
                <button type="submit" class="btn btn-primary">Search By Order ID</button>
            </form>
            <div class="form-group">
                <label>
                    Date
                </label>
                <input type="date" class="form-control" id="date" name="date" required>
            </div>
        </div>
    </jsp:body>
</t:layout>