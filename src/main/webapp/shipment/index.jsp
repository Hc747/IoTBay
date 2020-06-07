<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 7/06/2020
  Time: 8:58 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="au.edu.uts.isd.iotbay.action.Action" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
<html>
<header>
<style>
    button
    {
        margin-top: 10px;
        margin-left: 600px;
        font-family: "Arial";
        color: white;
        font-size: 20px;
        background-color: dodgerblue;
        border-radius: 8px;
        border: white;
        padding: 5px 5px;
    }
    .back
    {
        background: url(shipment-truck.png);
        background-repeat: no-repeat;
        background-size: 40%;
    }
</style>
</header>
    <body class="back">
            <table style="margin-top: 50px">
                <tr><td><a href="create"><button>Create</button></a></td></tr>
                <tr><td><a href="edit"><button>Edit</button></a></td></tr>
                <tr><td><a href="delete"><button>Delete</button></a></td></tr>
                <tr><td><a href="read"><button>Read</button></a></td></tr>
            </table>
    </body>
</html>
</t:layout>
