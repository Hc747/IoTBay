<%@tag description="layout" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <jsp:include page="/WEB-INF/includes/common/handle_action.jsp"/>
    <jsp:include page="/WEB-INF/includes/common/head.jsp"/>
    <body>
        <jsp:include page="/WEB-INF/includes/common/header.jsp"/>
        <main role="main">
            <jsp:include page="/WEB-INF/includes/common/render_action.jsp"/>
            <jsp:doBody/>
        </main>
        <jsp:include page="/WEB-INF/includes/common/footer.jsp"/>
        <jsp:include page="/WEB-INF/includes/common/scripts.jsp"/>
    </body>
</html>