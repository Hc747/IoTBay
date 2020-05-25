package au.edu.uts.isd.iotbay.servlet.filter;

import au.edu.uts.isd.iotbay.Constants;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    public static final String AUTHENTICATION_PATH = Constants.path(true, "login");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        final HttpSession session = req.getSession();
        final boolean authenticated = AuthenticationUtil.isAuthenticated(session);

        if (authenticated) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(AUTHENTICATION_PATH); //TODO: append redirect URL?
        }

//        if (requiresAuthentication(req) && !authenticated) {
//            res.sendRedirect(AUTHENTICATION_PATH); //TODO: redirect URL?
//            return;
//        }
//
//        chain.doFilter(request, response);
    }

    private boolean requiresAuthentication(HttpServletRequest request) {
        final String path = request.getServletPath();
        return path.startsWith("/profile/");
    }

    @Override public void init(FilterConfig filterConfig) {}

    @Override public void destroy() {}
}
