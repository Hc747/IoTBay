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
        <div class="container">
            <form action="?action=shipment" method="POST">
                <div class="form-group">
                    <label>
                        Name
                    </label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Enter Name" required>
                </div>
            <div class="form-group">
                <label>
                    Shipment method
                </label>
                <select name="method" class="form-control">
                    <option value="free">Free (10 ~ 14 days)</option>
                    <option value="standard">Standard (5 ~ 7 days)</option>
                    <option value="express">Express (2 ~ 5 days)</option>
                </select>
            </div>
                <div class="form-group">
                    <label>
                        Address
                    </label>
                    <input type="text" class="form-control" id="address" name="address" placeholder="Enter Address" required>
                </div>
                <div class="form-group">
                    <label>
                        Post code
                    </label>
                    <input type="text" class="form-control" id="postcode" name="postcode" placeholder="Enter postcode" required>
                </div>
            <div class="form-group">
                <label>
                    Date
                </label>
                <input type="date" class="form-control" id="date" name="date" required>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </jsp:body>
</t:layout>