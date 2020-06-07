<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 7/06/2020
  Time: 3:40 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
    <jsp:body>
<div class ="container">
    <form action="?action=shipment" method="POST">
        <label>
    Please enter shipment id and estimated date
        </label>
    <input type="text" class="form-control" id="sid" name="sid" placeholder="Enter ID" required>
    <input type="date" class="form-control" id="date" name="date" required>
    </form>
</div>
    </jsp:body>
</t:layout>
