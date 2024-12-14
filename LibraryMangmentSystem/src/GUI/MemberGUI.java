package GUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import Classes.*;
import GUI.Login;

import static Classes.Loan.*;

public class MemberGUI extends JFrame {
    private DefaultTableModel tableModel;
    private JTable bookTable;
    private CardLayout card;
    private JPanel mainPanel;
    private static final Color BROWN_COLOR = new Color(121, 85, 72); // Define your desired brown color
    private static final Color LIGHT_BROWN_COLOR = new Color(141, 110, 99); // Lighter brown for buttons on hover
    private static final Color WHITE_COLOR = Color.WHITE; // White for text


    public MemberGUI() {
        setTitle("Member Panel");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Title", "Author", "Genre"}, 0);

        bookTable = new JTable(tableModel);

        card = new CardLayout();
        mainPanel = new JPanel(card);

        mainPanel.add(createMembersActionPanel(), "Members Action");
        mainPanel.add(createManageBookPanel(), "Manage Books");
        mainPanel.add(createRequestPanel(), "Requests");

        add(mainPanel, BorderLayout.CENTER);

        JPanel navPanel = createNavPanel();
        add(navPanel, BorderLayout.WEST);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createNavPanel() {
        JPanel NavPanel = new JPanel();
        NavPanel.setLayout(new GridLayout(4, 1, 10, 10));
        NavPanel.setPreferredSize(new Dimension(200, getHeight()));
        NavPanel.setBackground(new Color(92, 64, 51));

        addNavButton(NavPanel, "Members Action", e -> switchPage("Members Action"));
        addNavButton(NavPanel, "Manage Books", e -> switchPage("Manage Books"));
        addNavButton(NavPanel, "Requests", e -> switchPage("Requests"));
        addNavButton(NavPanel, "Logout", e -> logout());

        return NavPanel;
    }

    private void addNavButton(JPanel panel, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(LIGHT_BROWN_COLOR); // Using the defined color
        button.setForeground(WHITE_COLOR); // White text color
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setPreferredSize(new Dimension(150, 50));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(141, 110, 99)); // Light brown on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(LIGHT_BROWN_COLOR); // Default light brown
            }
        });

        panel.add(button);
    }


    private JPanel createMembersActionPanel() {
        JPanel membersActionPanel = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Borrow", createBorrowPanel());
        tabbedPane.addTab("Return", createReturnPanel());
        tabbedPane.addTab("Search", createSearchPanel());

        membersActionPanel.add(tabbedPane, BorderLayout.CENTER);
        return membersActionPanel;
    }

    private JPanel createManageBookPanel() {
        JPanel ManageBookPanel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Request Book", createBorrowPanel());
        ManageBookPanel.add(tabbedPane, BorderLayout.CENTER);
        return ManageBookPanel;
    }

    private JPanel createRequestPanel() {
        JPanel requestPanel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel requestDetailsPanel = new JPanel();
        requestDetailsPanel.add(new JLabel("Details of the request"));
        tabbedPane.addTab("Request Details", requestDetailsPanel);
        requestPanel.add(tabbedPane, BorderLayout.CENTER);
        return requestPanel;
    }

    private void styleNavButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(121, 85, 72));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setPreferredSize(new Dimension(150, 50));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(141, 110, 99));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(121, 85, 72));
            }
        });
    }

    private void switchPage(String pageName) {
        card.show(mainPanel, pageName);
    }

    private JPanel createBorrowPanel() {
        JPanel borrowPanel = new JPanel(new BorderLayout());
        borrowPanel.setBackground(new Color(121, 85, 72)); // Matching brown background

        // Add components to borrow book
        JPanel borrowInputPanel = new JPanel(new GridBagLayout());
        borrowInputPanel.setBackground(new Color(121, 85, 72)); // Matching brown background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Insets for spacing between components

        JLabel memberIDLabel = new JLabel("Member ID:");
        memberIDLabel.setFont(new Font("Caveat", Font.BOLD, 24));
        memberIDLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        borrowInputPanel.add(memberIDLabel, gbc);

        JTextField memberIDField = new JTextField(20);
        memberIDField.setFont(new Font("Caveat", Font.PLAIN, 16));
        memberIDField.setBackground(new Color(141, 110, 99)); // Light brown background
        memberIDField.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 0;
        borrowInputPanel.add(memberIDField, gbc);

        JLabel bookIDLabel = new JLabel("Book ID:");
        bookIDLabel.setFont(new Font("Caveat", Font.BOLD, 24));
        bookIDLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        borrowInputPanel.add(bookIDLabel, gbc);

        JTextField bookIDField = new JTextField(20);
        bookIDField.setFont(new Font("Caveat", Font.PLAIN, 16));
        bookIDField.setBackground(new Color(141, 110, 99)); // Light brown background
        bookIDField.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 1;
        borrowInputPanel.add(bookIDField, gbc);

        JButton borrowButton = new JButton("Borrow Book");
        borrowButton.setPreferredSize(new Dimension(150, 40));
        borrowButton.setFont(new Font("Arial", Font.PLAIN, 16));
        borrowButton.setBackground(new Color(141, 110, 99));
        borrowButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        borrowInputPanel.add(borrowButton, gbc);

        // Clear Button
        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(150, 40));
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
        clearButton.setBackground(new Color(141, 110, 99));
        clearButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        borrowInputPanel.add(clearButton, gbc);

        borrowPanel.add(borrowInputPanel, BorderLayout.CENTER);

// Handle borrow button click
        borrowButton.addActionListener(e -> {
            String memberID = memberIDField.getText();
            String bookID = bookIDField.getText();

            try {
                borrowBook(memberID, bookID);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error occurred while borrowing the book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Handle clear button click
        clearButton.addActionListener(e -> {
            memberIDField.setText("");
            bookIDField.setText("");
        });

        return borrowPanel;
    }

    private JPanel createReturnPanel() {
        JPanel returnPanel = new JPanel(new BorderLayout());
        returnPanel.setBackground(new Color(121, 85, 72)); // Matching brown background

        // Add components to return book
        JPanel returnInputPanel = new JPanel(new GridBagLayout());
        returnInputPanel.setBackground(new Color(121, 85, 72)); // Matching brown background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Insets for spacing between components

        JLabel memberIDLabel = new JLabel("Member ID:");
        memberIDLabel.setFont(new Font("Caveat", Font.BOLD, 24));
        memberIDLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        returnInputPanel.add(memberIDLabel, gbc);

        JTextField memberIDField = new JTextField(20);
        memberIDField.setFont(new Font("Caveat", Font.PLAIN, 16));
        memberIDField.setBackground(new Color(141, 110, 99)); // Light brown background
        memberIDField.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 0;
        returnInputPanel.add(memberIDField, gbc);

        JLabel bookIDLabel = new JLabel("Book ID:");
        bookIDLabel.setFont(new Font("Caveat", Font.BOLD, 24));
        bookIDLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        returnInputPanel.add(bookIDLabel, gbc);

        JTextField bookIDField = new JTextField(20);
        bookIDField.setFont(new Font("Caveat", Font.PLAIN, 16));
        bookIDField.setBackground(new Color(141, 110, 99)); // Light brown background
        bookIDField.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 1;
        returnInputPanel.add(bookIDField, gbc);

        JButton returnButton = new JButton("Return Book");
        returnButton.setPreferredSize(new Dimension(150, 40));
        returnButton.setFont(new Font("Arial", Font.PLAIN, 16));
        returnButton.setBackground(new Color(141, 110, 99));
        returnButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        returnInputPanel.add(returnButton, gbc);

        // Clear Button
        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(150, 40));
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
        clearButton.setBackground(new Color(141, 110, 99));
        clearButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        returnInputPanel.add(clearButton, gbc);

        returnPanel.add(returnInputPanel, BorderLayout.CENTER);

        // Handle return button click
        returnButton.addActionListener(e -> {
            String memberID = memberIDField.getText();
            String bookID = bookIDField.getText();
            try {
                Loan.returnBook(memberID, bookID);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error occurred while returning the book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

// Handle clear button click
        clearButton.addActionListener(e -> {
            memberIDField.setText("");
            bookIDField.setText("");
        });

        return returnPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(new Color(121, 85, 72)); // Brown color background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Spacing between components

        // Label for Search
        JLabel searchLabel = new JLabel("Search by Title or Author:");
        searchLabel.setFont(new Font("Caveat", Font.BOLD, 24));
        searchLabel.setForeground(Color.WHITE); // White text color
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        searchPanel.add(searchLabel, gbc);

        // TextField for Search
        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Caveat", Font.PLAIN, 16)); // Font size for text field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        searchPanel.add(searchField, gbc);

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 40));
        searchButton.setFont(new Font("Arial", Font.PLAIN, 16));
        searchButton.setBackground(new Color(141, 110, 99)); // Brown color for the button
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(e -> {
            String query = searchField.getText();

            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Search query cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Call the method to search books by title or author
            Catalog.searchBookByTitle(query);
        });

        // Clear Button
        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 40));
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
        clearButton.setBackground(new Color(141, 110, 99));
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(e -> searchField.setText(""));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        searchPanel.add(searchButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        searchPanel.add(clearButton, gbc);

        return searchPanel;
    }

    private JPanel createSearchBookPanel() {
        // Similar layout for search book panel
        JPanel searchBookPanel = new JPanel(new BorderLayout());
        searchBookPanel.setBackground(new Color(121, 85, 72));

        JTextArea searchResultsArea = new JTextArea(10, 30);
        searchResultsArea.setEditable(false);
        searchResultsArea.setBackground(new Color(121, 85, 72));
        searchResultsArea.setForeground(Color.WHITE);
        searchBookPanel.add(new JScrollPane(searchResultsArea), BorderLayout.CENTER);

        return searchBookPanel;
    }


    private void logout() {
        dispose();
        new Login();
    }
    public static void main(String[] args) {
        new MemberGUI();
    }//
}