<%@page import="java.net.URLEncoder"%>
<%@page import="au.edu.uts.wsd.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <jsp:include page="WEB-INF/includes/common/handle_action.jsp"/>

    <jsp:include page="WEB-INF/includes/common/head.jsp"/>

    <body>

        <jsp:include page="WEB-INF/includes/common/header.jsp"/>

        <main role="main">

            <jsp:include page="WEB-INF/includes/common/render_action.jsp"/>

            <jsp:include page="WEB-INF/includes/common/info.jsp"/>

            <jsp:include page="WEB-INF/includes/render/render_options.jsp"/>

            <jsp:include page="WEB-INF/includes/render/renderer.jsp"/>

        </main>

        <jsp:include page="WEB-INF/includes/common/footer.jsp"/>

        <jsp:include page="WEB-INF/includes/common/scripts.jsp"/>

    </body>
</html>
