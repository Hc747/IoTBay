package au.edu.uts.isd.iotbay.cart;

import au.edu.uts.isd.iotbay.model.order.OrderProduct;
import au.edu.uts.isd.iotbay.model.product.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable {

    private final List<OrderProduct> products = new ArrayList<>();

    public void set(Product product, int quantity) {
        final boolean delete = quantity <= 0;

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

    public double totalCost() {
        double total = 0.0D;
        for (OrderProduct p : products) {
            total += (p.getProduct().getPrice() * p.getQuantity());
        }
        return total;
    }

    public int totalItems() {
        int total = 0;
        for (OrderProduct p : products) {
            total += p.getQuantity();
        }
        return total;
    }

    public void add(Product product) {
        set(product, quantity(product) + 1);
    }

    public void remove(Product product) {
        set(product, quantity(product) - 1);
    }

    public void delete(Product product) {
        final OrderProduct current = find(product);
        if (current != null) {
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
