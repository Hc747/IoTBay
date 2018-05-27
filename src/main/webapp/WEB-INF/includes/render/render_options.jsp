<%@page import="au.edu.uts.wsd.Constants"%>
<div class="container text-center">
    <p>
        <a href="<%= Constants.BASE_URL%>?renderer=POJO" class="btn btn-sm btn-outline-primary">
            Render using Java Code
        </a>
    </p>
    <p>
        <a href="<%= Constants.BASE_URL%>?renderer=XSLT" class="btn btn-sm btn-outline-secondary">
            Render using XSLT
        </a>
    </p>
</div>