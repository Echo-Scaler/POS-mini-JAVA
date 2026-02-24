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
    private JComboBox<String> categoryCombo;
    private JPanel productGrid;
    private DefaultListModel<String> cartListModel;
    private JList<String> cartList;
    private JLabel subtotalLabel, taxLabel, totalLabel;

    public POSForm() {
        setupUI();
        refreshAllContent();
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
        headerPanel.setPreferredSize(new Dimension(0, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("ELITE POS");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Search and Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        filterPanel.setOpaque(false);

        JLabel catLabel = new JLabel("Category:");
        catLabel.setForeground(Color.WHITE);
        filterPanel.add(catLabel);

        categoryCombo = new JComboBox<>();
        categoryCombo.setPreferredSize(new Dimension(150, 35));
        categoryCombo.addActionListener(e -> performSearch());
        filterPanel.add(categoryCombo);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(Color.WHITE);
        filterPanel.add(searchLabel);

        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(250, 35));
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                performSearch();
            }
        });
        filterPanel.add(searchField);

        headerPanel.add(filterPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // 2. Main Content (Product Grid)
        productGrid = new JPanel(new GridLayout(0, 4, 15, 15));
        productGrid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        productGrid.setBackground(new Color(241, 245, 249)); // Slate 100

        JScrollPane scrollPane = new JScrollPane(productGrid);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Sidebar (Cart)
        sidebarPanel = new JPanel(new BorderLayout());
        sidebarPanel.setPreferredSize(new Dimension(380, 0));
        sidebarPanel.setBackground(Color.WHITE);
        sidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(226, 232, 240)));

        JLabel cartHeader = new JLabel("SHOPPING CART");
        cartHeader.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cartHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        sidebarPanel.add(cartHeader, BorderLayout.NORTH);

        cartListModel = new DefaultListModel<>();
        cartList = new JList<>(cartListModel);
        cartList.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        cartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sidebarPanel.add(new JScrollPane(cartList), BorderLayout.CENTER);

        // Summary Panel
        JPanel summaryPanel = new JPanel(new GridLayout(4, 1, 8, 8));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        summaryPanel.setBackground(Color.WHITE);

        subtotalLabel = new JLabel("Subtotal: $0.00");
        taxLabel = new JLabel("Tax (7%): $0.00");
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        totalLabel.setForeground(new Color(99, 102, 241)); // Indigo 500

        JButton payButton = new JButton("COMPLETE PAYMENT");
        payButton.setBackground(new Color(16, 185, 129)); // Emerald 500
        payButton.setForeground(Color.WHITE);
        payButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        payButton.setFocusPainted(false);
        payButton.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        payButton.addActionListener(e -> checkout());

        summaryPanel.add(subtotalLabel);
        summaryPanel.add(taxLabel);
        summaryPanel.add(totalLabel);
        summaryPanel.add(payButton);

        sidebarPanel.add(summaryPanel, BorderLayout.SOUTH);

        add(sidebarPanel, BorderLayout.EAST);

        setVisible(true);
    }

    private void refreshAllContent() {
        // Load categories
        List<String> categories = productDAO.getAllCategories();
        categoryCombo.removeAllItems();
        for (String cat : categories) {
            categoryCombo.addItem(cat);
        }

        loadProducts("", "All");
    }

    private void performSearch() {
        String query = searchField.getText();
        String category = (String) categoryCombo.getSelectedItem();
        if (category == null)
            category = "All";
        loadProducts(query, category);
    }

    private void loadProducts(String query, String category) {
        productGrid.removeAll();
        List<Product> products = productDAO.searchProducts(query, category);

        if (products != null) {
            for (Product p : products) {
                JPanel card = createProductCard(p);
                productGrid.add(card);
            }
        }
        productGrid.revalidate();
        productGrid.repaint();
    }

    private JPanel createProductCard(Product p) {
        JPanel card = new JPanel(new BorderLayout(0, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel nameLabel = new JLabel("<html><body style='width: 150px'>" + p.getName() + "</body></html>");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel catLabel = new JLabel(p.getCategory());
        catLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        catLabel.setForeground(new Color(148, 163, 184)); // Slate 400

        JLabel priceLabel = new JLabel(String.format("$%.2f", p.getPrice()));
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        priceLabel.setForeground(new Color(100, 116, 139));

        JButton addButton = new JButton("Add to Cart");
        addButton.setBackground(new Color(99, 102, 241)); // Indigo 500
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> {
            posService.addToCart(p, 1);
            updateCartUI();
        });

        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 2, 2));
        infoPanel.setOpaque(false);
        infoPanel.add(nameLabel);
        infoPanel.add(catLabel);
        infoPanel.add(priceLabel);

        card.add(infoPanel, BorderLayout.CENTER);
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