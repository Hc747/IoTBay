package au.edu.uts.isd.iotbay.model.invoice;


import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;

public abstract class Invoice {
    protected int id;
    protected double amount;
    protected String details;

    public enum Type {
        GUEST,
        USER;

        public static Type findByType(String name) {return valueOf(name); }
        }
    }
