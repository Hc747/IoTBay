package au.edu.uts.isd.iotbay.model.product;

public class Product {
    private int product_id;
    private String product_name;
    private String product_description;
    private int product_quantity;
    private double product_price;

    public Product (int product_id) {
        this.product_id = product_id;
    }

    public Product(int product_id, String product_name, String product_description, int product_quantity, double product_price) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }
}
