package service;

import model.CartItem;
import model.Product;
import java.util.ArrayList;
import java.util.List;

public class POSService {

    private List<CartItem> cart = new ArrayList<>();
    private static final double TAX_RATE = 0.07; // 7% tax

    public void addToCart(Product product, int quantity) {
        for (CartItem item : cart) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cart.add(new CartItem(product, quantity));
    }

    public double calculateSubtotal() {
        double subtotal = 0;
        for (CartItem item : cart) {
            subtotal += item.getSubtotal();
        }
        return subtotal;
    }

    public double calculateTax() {
        return calculateSubtotal() * TAX_RATE;
    }

    public double calculateTotal() {
        return calculateSubtotal() + calculateTax();
    }

    public List<CartItem> getCart() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
    }

    public void removeFromCart(int productId) {
        cart.removeIf(item -> item.getProduct().getId() == productId);
    }
}