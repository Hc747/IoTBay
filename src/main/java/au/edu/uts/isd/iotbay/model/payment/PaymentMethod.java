package au.edu.uts.isd.iotbay.model.payment;

import lombok.Data;

@Data
public abstract class PaymentMethod {

    protected Integer id;

    protected PaymentMethod(Integer id) {
        this.id = id;
    }

    public abstract Type type();

    public abstract String details();

    String format(String title, String content) {
        return String.format("<li class=\"list-group-item\">%s: %s</li>", title, content);
    }

    public String inline() {
        final Type type = type();
        final StringBuilder content = new StringBuilder("<ul class=\"list-group list-group-horizontal\">");

        switch (type) {
            case PAYPAL:
                final PaypalPaymentMethod paypal = (PaypalPaymentMethod) this;
                content.append(format("Token", paypal.getToken()));
                break;
            case CREDIT_CARD:
                final CreditCardPaymentMethod card = (CreditCardPaymentMethod) this;
                content.append(format("Number", card.getNumber())).append(format("Holder", card.getHolder())).append(format("CVV", card.getCvv())).append(format("Expiration", card.getExpiration().toString()));
                break;
        }

        content.append("</ul>");
        return content.toString();
    }

    public enum Type {
        PAYPAL,
        CREDIT_CARD
        ;

        public static Type findByName(String name) {
            try {
                return valueOf(name);
            } catch (Exception e) {
                return null;
            }
        }
    }

}
