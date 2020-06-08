<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 7/06/2020
  Time: 5:52 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="au.edu.uts.isd.iotbay.action.Action" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
    <jsp:body>
        <div class ="container">
            <form action="?action=shipment&type=delete" method="POST">
                <label>
                    Please enter shipment id
                </label>
                <input type="text" class="form-control" id="id" name="id" placeholder="Enter ID" required>
                <button type="submit" class="btn btn-primary">Delete</button>
            </form>
        </div>
    </jsp:body>
</t:layout>
