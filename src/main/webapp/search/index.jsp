<%--
  Created by IntelliJ IDEA.
  User: matt
  Date: 9/6/20
  Time: 3:22 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
    <jsp:body>
        <div class="container">
            <div class="container p-3 my-3 bg-dark text-white">
                <h1 style="text-align: center">Search Products</h1>
                <p>Product search is exact (not case).</p>
            </div>
           <form action="results/" method="post">
               <div class="form-group">
                   <label for="search">
                       Search Product Name:
                   </label>
                   <input class="form-control" id="search" name="search"type="text" placeholder="Product Name" required>
               </div>
               <div>
                   <button type="submit" class="btn btn-light">Search</button>
                   <a type="button" href="${home}" class="btn btn-light">Cancel</a>
               </div>
           </form>
        </div>
    </jsp:body>
</t:layout>
