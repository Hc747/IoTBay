package au.edu.uts.isd.iotbay.cart;

import au.edu.uts.isd.iotbay.model.order.OrderProduct;
import au.edu.uts.isd.iotbay.model.product.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable {

    //Shopping cart keeps a list of OrderProducts
    private final List<OrderProduct> products = new ArrayList<>();

    //Order products has a product object and it's quantity
    //set takes in a product object and a int quantity
    public void set(Product product, int quantity) {
        final boolean delete = quantity <= 0;

        //if the product quantity is less than zero, delete the product from the list
        if (delete) {
            delete(product);
        } else {
            final OrderProduct current = find(product);

            if (current == null) {
                products.add(new OrderProduct(product, quantity));
            } else {
                current.setQuantity(quantity);
            }
        }
    }

    public List<OrderProduct> products() {
        return new ArrayList<>(products);
    }

    //totalCost returns an integer the total cost
    public double totalCost() {
        //Initialise the total cost
        double total = 0.0D;
        //for each product added to the cart, get the quantity and price of the product and add it to the total
        for (OrderProduct p : products) {
            //Add the total cost of the product into the total variable
            total += (p.getProduct().getPrice() * p.getQuantity());
        }
        return total;
    }

    //Calculate the total item in the cart
    public int totalItems() {
        //Initialise as 0 as there are no items in the cart
        int total = 0;
        //for each product added to the cart, get the quantity of the product and add it to the total
        for (OrderProduct p : products) {
            //Add the quantity of the product to the current total
            total += p.getQuantity();
        }
        //return the total
        return total;
    }

    //add increments the product by 1 in the shopping cart
    public void add(Product product) {
        set(product, quantity(product) + 1);
    }
    //remove decrements the product by 1 in the shopping cart
    public void remove(Product product) {
        set(product, quantity(product) - 1);
    }
    //delete removes the current product in the cart
    public void delete(Product product) {
        final OrderProduct current = find(product);
        //remove only when there is a product in the cart
        if (current != null) {
            //remove the selected product
            products.remove(current);
        }
    }

    public boolean contains(Product product) {
        return quantity(product) > 0;
    }

    public int quantity(Product product) {
        final OrderProduct current = find(product);
        return current == null ? 0 : current.getQuantity();
    }

    public OrderProduct find(Product product) {
        for (OrderProduct p : products) {
            if (p.getProduct().getId().equals(product.getId())) {
                return p;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void clear() {
        products.clear();
    }
}
