package au.edu.uts.isd.iotbay.servlet.filter;

import au.edu.uts.isd.iotbay.model.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthenticatedStaffFilter extends AuthenticatedFilter {

    @Override
    protected boolean isAuthenticated(HttpServletRequest request, HttpSession session, User user) {
        if (!super.isAuthenticated(request, session, user)) {
            return false;
        }

        if (user == null) {
            return false;
        }

        return user.getRole().isStaff();
    }
}
