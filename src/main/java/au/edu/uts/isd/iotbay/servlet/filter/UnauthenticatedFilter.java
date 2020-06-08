package au.edu.uts.isd.iotbay.servlet.filter;

import au.edu.uts.isd.iotbay.Constants;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;
import lombok.SneakyThrows;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * A {@code Filter} that ensures a session is unauthenticated.
 */
public class UnauthenticatedFilter implements Filter {

    private static final String REDIRECTION_PATH = Constants.path(true);

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        final HttpSession session = req.getSession();
        final boolean authenticated = AuthenticationUtil.isAuthenticated(session);

        if (authenticated) {
            res.sendRedirect(REDIRECTION_PATH);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override public void init(FilterConfig filterConfig) {}

    @Override public void destroy() {}
}
