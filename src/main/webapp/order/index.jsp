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
        <h1 class="jumbotron-heading" style="text-align: center">Make Order</h1>
        <div class="container">
            <form action="?action=order&" method="POST">
            <div class="form-group">
                <label for="invoiceId">Invoice Id:</label>
                <input type="text" name="invoiceId" id="invoiceId" required><br>
                <label for="shipmentId">Choose Payment Method:</label>
                <input type="text" name="shipmentId" id="shipmentId" required><br>
                <label for="payment">Choose Payment Method:</label>
                <select name="payment" id="payment" required>
                    <option value="paypal">Paypal</option>
                    <option value="credit card">Credit Card</option>
                </select><br>
                <label for="products">Choose Payment Method:</label>
                <select name="product" id="products" required>
                    <option value="google home">Google Home</option>
                    <option value="amazon alexa">Amazon Alexa</option>
                    <option value="smart fridge">Smart Fridge</option>
                    <option value="smart sensors">Smart Sensor</option>
                    <option value="Home hub">Home Hub</option>
                    <option value="smart display">smart display</option>
                </select><br>
                <label for="quantity">Quantity</label>
                <input type="numbers" name="quantity" id="quantity" required><br>
                <button type="submit" class="btn btn-primary">Submit Order</button>
            </div>
            </form>
        </div>
    </jsp:body>
</t:layout>