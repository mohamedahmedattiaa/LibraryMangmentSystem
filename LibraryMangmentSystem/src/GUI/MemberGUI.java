package GUI;
import Classes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MemberGUI extends JFrame {
    private Catalog catalog ;
    private dataBaseMembers member;
    private JTextArea displayArea;

    public MemberGUI() {
        catalog = new Catalog();// Initialize the catalog
        catalog.addBook(new Book("B001", "The Great Gatsby", "Fiction"));
        catalog.addBook(new Book("B002", "1984", "Dystopian"));
        catalog.addBook(new Book("B003", "To Kill a Mockingbird", "Dystopian"));
        setTitle("Library Member Interface");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Navigation Panel
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(5, 1, 10, 10));
        navPanel.setPreferredSize(new Dimension(200, getHeight()));
        navPanel.setBackground(new Color(54, 54, 54));

        JButton viewCatalogButton = new JButton("View Catalog");
        viewCatalogButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayCatalog();
            }
        });
        styleNavButton(viewCatalogButton);
        navPanel.add(viewCatalogButton);

        JButton borrowBookButton = new JButton("Borrow Book");
        borrowBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                borrowBook();
            }
        });
        styleNavButton(borrowBookButton);
        navPanel.add(borrowBookButton);

        JButton returnBookButton = new JButton("Return Book");
        returnBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });
        styleNavButton(returnBookButton);
        navPanel.add(returnBookButton);

        JButton viewPendingRequestsButton = new JButton("View Pending Requests");
        viewPendingRequestsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PendingRequestsQueue.display();
            }
        });
        styleNavButton(viewPendingRequestsButton);
        navPanel.add(viewPendingRequestsButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login();
            }
        });
        styleNavButton(logoutButton);
        navPanel.add(logoutButton);

        // Add Navigation Panel to frame
        add(navPanel, BorderLayout.WEST);

        // Display Area (main content area)
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to display the catalog of books
    private void displayCatalog() {
        displayArea.setText("Library Catalog:\n");
        catalog.displayCatalog();
    }

    // Method to borrow a book
    private void borrowBook() {
        String bookId = JOptionPane.showInputDialog(this, "Enter Book ID to borrow:");
        String memberId = JOptionPane.showInputDialog(this, "Enter your Member ID:");

        try {
            Loan.borrowBook(memberId, bookId, catalog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to return a borrowed book
    private void returnBook() {
        String bookId = JOptionPane.showInputDialog(this, "Enter Book ID to return:");
        String memberId = JOptionPane.showInputDialog(this, "Enter your Member ID:");

        try {
            Loan.returnBook(memberId, bookId, catalog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to style navigation buttons
    private void styleNavButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(85, 85, 85));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public static void main(String[] args) {
        new MemberGUI();  // Launch the member interface
    }
}
