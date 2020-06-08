<%@ page import="au.edu.uts.isd.iotbay.Constants" %>
<%@ page import="static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty" %>
<%@ page import="au.edu.uts.isd.iotbay.model.payment.PaymentMethod" %>
<%@ page import="au.edu.uts.isd.iotbay.model.user.User" %>
<%@ page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil" %>
<%@ page import="au.edu.uts.isd.iotbay.util.CollectionUtil" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
    final User user = AuthenticationUtil.user(session);
    final String id = request.getParameter("id");

    final PaymentMethod method;

    if (isNullOrEmpty(id)) {
        method = null;
    } else {
        method = CollectionUtil.findById(user.getPayments(), id);
    }

    request.setAttribute("method", method);
    request.setAttribute("endpoint", Constants.path(true, "profile", "payment"));
    request.setAttribute("back", Constants.path(true, "profile", "payment"));
%>
<t:layout>
    <jsp:body>
        <div class="container">
            <c:if test="${method == null}">
                <h1 class="jumbotron-heading" style="text-align: center">Unable to find selected payment method details.</h1>
            </c:if>
            <c:if test="${method != null}">
                <form action="${endpoint}?action=payment&type=update" method="POST">
                    <h1 class="jumbotron-heading" style="text-align: center">Edit Payment Method</h1>
                    <input type="hidden" name="id" value="${method.id}"/>
                    <input type="hidden" name="impl" value="${method.type()}"/>
                    <c:if test="${method.type().name().equalsIgnoreCase('PAYPAL')}">
                        <div class="form-group">
                            <label for="token">Token</label>
                            <input type="text" class="form-control" id="token" name="token" placeholder="Enter PayPal Token" value="${method.token}" required>
                        </div>
                    </c:if>
                    <c:if test="${method.type().name().equalsIgnoreCase('CREDIT_CARD')}">
                        <div class="form-group">
                            <label for="number">Card Number</label>
                            <input type="text" class="form-control" id="number" name="number" placeholder="Enter Credit Card Number" value="${method.number}" required>
                        </div>
                        <div class="form-group">
                            <label for="holder">Card Holder</label>
                            <input type="text" class="form-control" id="holder" name="holder" placeholder="Enter Credit Card Holder" value="${method.holder}" required>
                        </div>
                        <div class="form-group">
                            <label for="cvv">Card Verification Value</label>
                            <input type="text" class="form-control" id="cvv" name="cvv" placeholder="Enter Credit Card Verification Value" value="${method.cvv}" required>
                        </div>
                        <div class="form-group">
                            <label for="expiration">Card Expiration</label>
                            <input type="date" class="form-control" id="expiration" name="expiration" placeholder="Enter Credit Card Expiration Date" value="${method.expiration}" required>
                        </div>
                    </c:if>
                    <button type="submit" class="btn btn-primary">Submit</button>
                    <a href="${endpoint}" type="button" class="btn btn-primary">Cancel</a>
                </form>
            </c:if>
        </div>
    </jsp:body>
</t:layout>
