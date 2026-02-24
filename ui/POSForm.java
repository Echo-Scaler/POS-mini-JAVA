package ui;

import javax.swing.*;

import service.POSService;
import dao.ProductDAO;
import model.CartItem;
import model.Product;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class POSForm extends JFrame {

    private POSService posService = new POSService();
    private ProductDAO productDAO = new ProductDAO();

    // UI Components
    private JPanel headerPanel, sidebarPanel;
    private JTextField searchField;
    private JPanel productGrid;
    private DefaultListModel<String> cartListModel;
    private JList<String> cartList;
    private JLabel subtotalLabel, taxLabel, totalLabel;

    public POSForm() {
        setupUI();
        loadProducts("");
    }

    private void setupUI() {
        setTitle("Elite POS System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // 1. Header
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(51, 65, 85)); // Slate 700
        headerPanel.setPreferredSize(new Dimension(0, 70));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("ELITE POS");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(300, 35));
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                loadProducts(searchField.getText());
            }
        });
        JPanel searchContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchContainer.setOpaque(false);
        searchContainer.add(new JLabel("Search: "));
        searchContainer.add(searchField);
        headerPanel.add(searchContainer, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // 2. Main Content (Product Grid)
        productGrid = new JPanel(new GridLayout(0, 3, 15, 15));
        productGrid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        productGrid.setBackground(new Color(241, 245, 249)); // Slate 100

        JScrollPane scrollPane = new JScrollPane(productGrid);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Sidebar (Cart)
        sidebarPanel = new JPanel(new BorderLayout());
        sidebarPanel.setPreferredSize(new Dimension(350, 0));
        sidebarPanel.setBackground(Color.WHITE);
        sidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(226, 232, 240)));

        JLabel cartHeader = new JLabel("YOUR CART");
        cartHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        cartHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        sidebarPanel.add(cartHeader, BorderLayout.NORTH);

        cartListModel = new DefaultListModel<>();
        cartList = new JList<>(cartListModel);
        cartList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sidebarPanel.add(new JScrollPane(cartList), BorderLayout.CENTER);

        // Summary Panel
        JPanel summaryPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        summaryPanel.setBackground(Color.WHITE);

        subtotalLabel = new JLabel("Subtotal: $0.00");
        taxLabel = new JLabel("Tax (7%): $0.00");
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        totalLabel.setForeground(new Color(99, 102, 241)); // Indigo 500

        JButton payButton = new JButton("COMPLETE PAYMENT");
        payButton.setBackground(new Color(16, 185, 129)); // Emerald 500
        payButton.setForeground(Color.WHITE);
        payButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        payButton.setFocusPainted(false);
        payButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        payButton.addActionListener(e -> checkout());

        summaryPanel.add(subtotalLabel);
        summaryPanel.add(taxLabel);
        summaryPanel.add(totalLabel);
        summaryPanel.add(payButton);

        sidebarPanel.add(summaryPanel, BorderLayout.SOUTH);

        add(sidebarPanel, BorderLayout.EAST);

        setVisible(true);
    }

    private void loadProducts(String query) {
        productGrid.removeAll();
        List<Product> products;
        if (query.isEmpty()) {
            products = productDAO.getAllProducts();
        } else {
            products = productDAO.searchProductsByName(query);
        }

        for (Product p : products) {
            JPanel card = createProductCard(p);
            productGrid.add(card);
        }
        productGrid.revalidate();
        productGrid.repaint();
    }

    private JPanel createProductCard(Product p) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel nameLabel = new JLabel(p.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel priceLabel = new JLabel(String.format("$%.2f", p.getPrice()));
        priceLabel.setForeground(new Color(100, 116, 139));

        JButton addButton = new JButton("Add to Cart");
        addButton.setBackground(new Color(99, 102, 241)); // Indigo 500
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> {
            posService.addToCart(p, 1);
            updateCartUI();
        });

        card.add(nameLabel, BorderLayout.NORTH);
        card.add(priceLabel, BorderLayout.CENTER);
        card.add(addButton, BorderLayout.SOUTH);

        return card;
    }

    private void updateCartUI() {
        cartListModel.clear();
        for (CartItem item : posService.getCart()) {
            cartListModel.addElement(String.format("%s x %d - $%.2f",
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getSubtotal()));
        }

        subtotalLabel.setText(String.format("Subtotal: $%.2f", posService.calculateSubtotal()));
        taxLabel.setText(String.format("Tax (7%%): $%.2f", posService.calculateTax()));
        totalLabel.setText(String.format("Total: $%.2f", posService.calculateTotal()));
    }

    private void checkout() {
        if (posService.getCart().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!");
            return;
        }
        JOptionPane.showMessageDialog(this, "Payment Successful! Total: " + totalLabel.getText());
        posService.clearCart();
        updateCartUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(POSForm::new);
    }
}