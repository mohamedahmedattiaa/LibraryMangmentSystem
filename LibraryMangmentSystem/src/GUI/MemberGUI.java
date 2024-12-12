package GUI;

import Classes.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MemberGUI extends JFrame {
    private Catalog catalog;
    private Member member;
    private JTextArea displayArea; // Declare displayArea
    private final Color NAVIGATION_PANEL_COLOR = new Color(92, 64, 51);
    private final Color MAIN_BACKGROUND_COLOR = new Color(121, 85, 72);
    private final Color BUTTON_HOVER_COLOR = new Color(141, 110, 99);
    private final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 16);

    public MemberGUI() {
        // ... (Existing code for initializing catalog and books)

        setTitle("Library Member Interface");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(5, 1, 10, 10));
        navPanel.setPreferredSize(new Dimension(220, getHeight()));
        navPanel.setBackground(NAVIGATION_PANEL_COLOR); // Use the defined color

        // ... add navigation buttons (same as before)

        add(navPanel, BorderLayout.WEST);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);
        addNavButton(navPanel, "Borrow Book", e -> borrowBook());
        addNavButton(navPanel, "Return Book", e -> returnBook());
        addNavButton(navPanel, "View Pending Requests", e -> PendingRequestsQueue.display()); //Make sure this method is correct
        addNavButton(navPanel, "View Catalog", e -> displayCatalog());
        addNavButton(navPanel, "Logout", e -> logout());


        add(navPanel, BorderLayout.WEST);
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Consistent font
        JScrollPane scrollPane1 = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane1, BorderLayout.CENTER);
        RedirectSystemOut.redirectToTextArea(displayArea);  // Correct method call
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addNavButton(JPanel panel, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        button.setFont(BUTTON_FONT); //Directly use font
        button.setBackground(MAIN_BACKGROUND_COLOR); //Directly use color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR); //Directly use color
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(MAIN_BACKGROUND_COLOR); //Directly use color

            }
        });
        panel.add(button);
    }

    private void styleNavButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(68, 68, 68));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 100, 100));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(68, 68, 68));
            }
        });
    }private void displayCatalog() {
        displayArea.setText("Library Catalog:\n");
        catalog.displayCatalog();
    }

    private void borrowBook() {
        String bookId = JOptionPane.showInputDialog(this, "Enter Book ID to borrow:");
        String memberId = JOptionPane.showInputDialog(this, "Enter your Member ID:");

        try {
            Loan.borrowBook(memberId, bookId, catalog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void returnBook() {
        String bookId = JOptionPane.showInputDialog(this, "Enter Book ID to return:");
        String memberId = JOptionPane.showInputDialog(this, "Enter your Member ID:");

        try {
            Loan.returnBook(memberId, bookId, catalog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logout() {
        dispose();
        new Login();
    }

    public static void main(String[] args) {
        new MemberGUI();
    }
}