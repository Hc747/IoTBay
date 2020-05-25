package au.edu.uts.isd.iotbay.servlet.filter;

import au.edu.uts.isd.iotbay.Constants;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;
import lombok.SneakyThrows;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AuthenticatedFilter implements Filter {

    private static final String AUTHENTICATION_PATH = Constants.path(true, "login");
    private static final String AUTHENTICATION_REDIRECT_PARAM = "?redirect_url=%s";

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        final HttpSession session = req.getSession();
        final boolean authenticated = AuthenticationUtil.isAuthenticated(session);

        if (authenticated) {
            chain.doFilter(request, response);
        } else {
            final StringBuffer requested = req.getRequestURL();
            final String encoded = encode(requested.toString());
            final String location = String.format(AUTHENTICATION_PATH + AUTHENTICATION_REDIRECT_PARAM, encoded);

            res.sendRedirect(location);
        }
    }

    private static String encode(String url) {
        try {
            return URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    @Override public void init(FilterConfig filterConfig) {}

    @Override public void destroy() {}
}
