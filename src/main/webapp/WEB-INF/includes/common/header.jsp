<%@page import="java.util.ArrayDeque"%>
<%@page import="java.util.Deque"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="au.edu.uts.wsd.Constants"%>
<%@page import="au.edu.uts.wsd.Person"%>
<%
    final Person person = (Person) session.getAttribute(Person.KEY);
%>
<header>
    <div class="collapse bg-dark" id="navbarHeader">
        <div class="container">
            <div class="row">

                <div class="col-sm-8 col-md-7 py-4">
                    <h4 class="text-white">
                        About
                    </h4>
                    <p class="text-muted">
                        This project uses a Bootstrap template, available <a href="https://getbootstrap.com/docs/4.1/examples/album/">here</a>.
                    </p>
                    <p class="text-muted">
                        This project demonstrates the following:
                    <ul class="text-white">
                        <li>
                            Displaying data dynamically via JSP
                        </li>
                        <li>
                            Displaying data dynamically via JSP and XSLT
                        </li>
                        <li>
                            Client-sided data validation (basic)
                        </li>
                        <li>
                            Server-sided data validation (basic)
                        </li>
                        <li>
                            A basic ReST API
                        </li>
                        <li>
                            A basic SOAP API
                        </li>
                    </ul>
                    </p> 
                </div>

                <div class="col-sm-4 offset-md-1 py-4">
                    <h4 class="text-white">
                        Actions
                    </h4>
                    <ul class="list-unstyled">

                        <%
                            if (person == null) {
                        %>

                        <li>
                            <a href="http://localhost:8080/demo_project/login/" class="text-white">
                                Login
                            </a>
                        </li>
                        <li>
                            <a href="http://localhost:8080/demo_project/register/" class="text-white">
                                Register
                            </a>
                        </li>

                        <% } else { %>

                        <li>
                            <a href="http://localhost:8080/demo_project/logout/" class="text-white">
                                Logout
                            </a>
                        </li>

                        <% }%>

                    </ul>
                </div>

            </div>
        </div>
    </div>

    <div class="navbar navbar-dark bg-dark box-shadow">
        <div class="container d-flex justify-content-between">

            <a href="<%= Constants.BASE_URL%>" class="navbar-brand d-flex align-items-center">
                <strong>

                    <%

                        //see information here about changing the Glassfish JDK version: https://stackoverflow.com/questions/10444959/how-do-i-specify-the-jdk-for-a-glassfish-domain
                        //remember to make sure you're modifying the same Glassfish installation that Netbeans uses
                        final StringBuilder location = new StringBuilder();

                        location.append(Constants.APPLICATION_NAME);

                        final String current = request.getServletPath().replace(Constants.BASE_URL, "");

                        if (current.contains("/")) {

                            final Deque<String> paths = new ArrayDeque<>(Arrays.asList(current.split("/")));

                            paths.removeFirst(); //get rid of the empty context path component
                            paths.removeLast(); //get rid of the filename (index.jsp etc)

                            paths.forEach(path -> location.append(" > ").append(path));

                        }

                    %>

                    <%= location %>
                </strong>
            </a>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarHeader" aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>
    </div>

</header>