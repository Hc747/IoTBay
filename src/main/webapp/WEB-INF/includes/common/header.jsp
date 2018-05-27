<%@page import="java.util.ArrayDeque"%>
<%@page import="java.util.Deque"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="au.edu.uts.wsd.Constants"%>
<%@page import="au.edu.uts.wsd.model.Person"%>
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
                            Rendering data dynamically via JSP and Java Code
                        </li>
                        <li>
                            Rendering data dynamically via JSP and XSLT
                        </li>
                        <li>
                            Basic Client-sided data validation
                        </li>
                        <li>
                            Basic Server-sided data validation
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
                            <a href="<%= Constants.BASE_URL %>login/" class="text-white">
                                Login
                            </a>
                        </li>
                        <li>
                            <a href="<%= Constants.BASE_URL %>register/" class="text-white">
                                Register
                            </a>
                        </li>

                        <% } else { %>

                        <li>
                            <a href="<%= Constants.BASE_URL %>logout/" class="text-white">
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

                        //to use features of Java 7 / 8, you'll need to modify your glassfish installations
                        //JDK version.
                        //To do so, go to the location of your glassfish installation and navigate to the following file.
                        //glassfish > domains > %domain_name% > config > default-web.xml
                        //Navigate to the JSP servlet and add the following parameters.
                        
                        // <init-param>
                        //  <param-name>compilerSourceVM</param-name>
                        //  <param-value>1.8</param-value>
                        // </init-param>
                        // 
                        // <init-param>
                        //  <param-name>compilerTargetVM</param-name>
                        //  <param-value>1.8</param-value>
                        // </init-param>
                        
                        final StringBuilder location = new StringBuilder();

                        location.append(Constants.APPLICATION_NAME);

                        final String current = request.getServletPath().replace(Constants.BASE_URL, "");

                        if (current.contains("/")) {

                            final Deque<String> paths = new ArrayDeque<>(Arrays.asList(current.split("/")));

                            paths.removeFirst(); //get rid of the empty context path component
                            paths.removeLast(); //get rid of the filename (index.jsp etc)

                            //equivalent to an 'enhanced for loop'
                            paths.forEach(path -> location.append(" > ").append(path));

                        }

                    %>
                    <%= location %>
                </strong>
            </a>
            <div class="text-white">
                <strong>
                    <%
                        String greeting = String.format("Welcome, %s!", person == null ? "Guest" : person.getName());
                    %>
                    <%= greeting %>
                </strong> 
                &nbsp;
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarHeader" aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
            </div>
        </div>
    </div>

</header>