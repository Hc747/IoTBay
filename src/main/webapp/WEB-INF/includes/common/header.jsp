<%@page import="au.edu.uts.isd.iotbay.Constants"%>
<%@page import="au.edu.uts.isd.iotbay.cart.ShoppingCart"%>
<%@ page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil" %>
<%@ page import="au.edu.uts.isd.iotbay.util.ShoppingCartUtil" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.*" %>
<%
    final boolean authenticated = AuthenticationUtil.isAuthenticated(session);
    final String template = authenticated ? "authenticated" : "unauthenticated";
    final String header = String.format("../../../WEB-INF/includes/common/header/%s.jsp", template);
    final ShoppingCart cart = ShoppingCartUtil.get(session);
%>
<header>
    <div class="navbar navbar-dark bg-dark box-shadow">
        <div class="container d-flex justify-content-between">
            <%
                final Map<String, String> paths = new LinkedHashMap<>();

                paths.put(Constants.APPLICATION_NAME, Constants.path(false));

                final String current = request.getServletPath().replace(Constants.path(false), "");
                final String[] segments = current.split("/");

                for (int index = 1; index < segments.length - 1; index++) {
                    final String segment = segments[index];
                    final String[] components = new ArrayList<>(Arrays.asList(segments).subList(1, index + 1)).toArray(new String[0]);
                    final String path = Constants.path(false, components);
                    paths.put(segment, path);
                }
            %>
            <div>
                <%
                    final Iterator<Map.Entry<String, String>> entries = paths.entrySet().iterator();

                    while (entries.hasNext()) {
                        final Map.Entry<String, String> entry = entries.next();
                        final String title = entry.getKey();
                        final String url = entry.getValue();
                        final boolean end = !entries.hasNext();
                %>
                    <a href="<%= url %>" class="navbar-brand align-items-center"><strong><%= title %></strong></a><span class="text-white"><%= end ? "" : "&gt;"%></span>
                <%
                    }
                %>
            </div>
            <div class="text-white">Cart: <%= cart.totalItems() %>x Item(s) - $<%= new DecimalFormat("###,##0.00").format(cart.totalCost()) %></div>
            <div class="text-white">
                <jsp:include page="<%= header %>"/>
            </div>
        </div>
    </div>

</header>