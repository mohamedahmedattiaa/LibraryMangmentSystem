package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import Classes.Book;
import Classes.Catalog;
import Classes.Node;
import javax.swing.ImageIcon;
import Classes.linkedlist;
public class LibrarianGUI extends JFrame {
    private DefaultTableModel tableModel;
    private JTable bookTable;
    private CardLayout cardLayout; // For switching pages
    private JPanel mainPanel; // Container for all pages

    public LibrarianGUI() {
        setTitle("Librarian Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize table model and JTable
        tableModel = new DefaultTableModel(new String[]{"Title", "Author", "Genre"}, 0);
        bookTable = new JTable(tableModel);

        // CardLayout for the main content area
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout); // Correctly set up with CardLayout

        // Add separate panels to the CardLayout
        mainPanel.add(createCatalogPanel(), "View Catalog");
        mainPanel.add(createAddBookPanel(), "Add Book");
        mainPanel.add(createRemoveBookPanel(), "Remove Book");
        mainPanel.add(createSearchPanel(), "Search Book");

        // Ensure the background image is compatible with CardLayout.
        JPanel backgroundPanel = new JPanel(new BorderLayout());
        JLabel background = createBackgroundImage();
        backgroundPanel.add(background, BorderLayout.CENTER);

        // Add to mainPanel inside CardLayout
        backgroundPanel.add(mainPanel, BorderLayout.NORTH);

        add(backgroundPanel, BorderLayout.CENTER);

        // Navigation panel
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(5, 1, 10, 10));
        navPanel.setPreferredSize(new Dimension(220, getHeight()));
        navPanel.setBackground(new Color(92, 64, 51));

        addNavButton(navPanel, "Add Book", e -> switchPage("Add Book"));
        addNavButton(navPanel, "Remove Book", e -> switchPage("Remove Book"));
        addNavButton(navPanel, "Search Book", e -> switchPage("Search Book"));
        addNavButton(navPanel, "View Catalog", e -> switchPage("View Catalog"));
        addNavButton(navPanel, "Logout", e -> logout());

        add(navPanel, BorderLayout.WEST);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JLabel createBackgroundImage() {
        // Load the background image
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/GUI/color.jpg")); // Ensure the path is correct
        JLabel background = new JLabel(backgroundIcon);
        background.setLayout(new BorderLayout());
        return background;
    }

    private void addNavButton(JPanel panel, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        styleNavButton(button);
        panel.add(button);
    }

    private void styleNavButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(121, 85, 72));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
        System.out.println("Switching to page: " + pageName); // Debugging line
        cardLayout.show(mainPanel, pageName);
    }

    private JPanel createCatalogPanel() {
        JPanel catalogPanel = new JPanel(new BorderLayout());
        JScrollPane tableScrollPane = new JScrollPane(bookTable);
        catalogPanel.add(tableScrollPane, BorderLayout.CENTER);
        return catalogPanel;
    }
    private JPanel createAddBookPanel() {
        JPanel addBookPanel = new JPanel();
        addBookPanel.setLayout(new GridBagLayout()); // استخدام GridBagLayout
        addBookPanel.setBackground(new Color(240, 240, 240)); // لون خلفية فاتح

        // إعداد GridBagConstraints لتوسيط العناصر
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // مساحة بين العناصر
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // إنشاء الحقول
        JTextField bookTitleField = new JTextField();
        JTextField bookAuthorField = new JTextField();
        JTextField bookGenreField = new JTextField();

        // ضبط حجم الحقول
        Dimension textFieldSize = new Dimension(200, 30); // حجم الحقول
        bookTitleField.setPreferredSize(textFieldSize);
        bookAuthorField.setPreferredSize(textFieldSize);
        bookGenreField.setPreferredSize(textFieldSize);

        // تصميم الحقول
        styleTextField(bookTitleField);
        styleTextField(bookAuthorField);
        styleTextField(bookGenreField);

        // تصميم النصوص
        JLabel titleLabel = createStyledLabel("Title:");
        JLabel authorLabel = createStyledLabel("Author:");
        JLabel genreLabel = createStyledLabel("Genre:");

        // إضافة العناصر إلى اللوحة
        gbc.gridx = 0; // العمود الأول
        gbc.gridy = 0; // الصف الأول
        addBookPanel.add(titleLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        addBookPanel.add(bookTitleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        addBookPanel.add(authorLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        addBookPanel.add(bookAuthorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        addBookPanel.add(genreLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        addBookPanel.add(bookGenreField, gbc);

        // إضافة الأزرار
        JButton confirmButton = new JButton("Add Book");
        JButton cancelButton = new JButton("Cancel");

        styleButton(confirmButton);
        styleButton(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(); // لوحة خاصة بالأزرار
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        addBookPanel.add(buttonPanel, gbc);

        return addBookPanel;
    }

    // تصميم النصوص
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Serif", Font.BOLD, 18)); // نوع الخط وحجمه
        label.setForeground(new Color(90, 90, 90)); // لون رمادي غامق
        label.setHorizontalAlignment(JLabel.CENTER); // توسيط النص
        return label;
    }

    // تصميم الحقول
    private void styleTextField(JTextField textField) {
        textField.setBackground(new Color(245, 245, 245)); // لون خلفية الحقول
        textField.setForeground(Color.DARK_GRAY); // لون النص داخل الحقول
        textField.setCaretColor(Color.BLACK); // لون مؤشر الكتابة
        textField.setFont(new Font("Arial", Font.PLAIN, 16)); // حجم الخط
        textField.setHorizontalAlignment(JTextField.CENTER); // توسيط النص داخل الحقل
        textField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1)); // حدود خفيفة
    }

    // تصميم الأزرار
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 35)); // حجم الأزرار
        button.setBackground(new Color(100, 149, 237)); // لون أزرق فاتح
        button.setForeground(Color.WHITE); // لون النص أبيض
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2)); // حدود زرقاء

        // تأثير Hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(65, 105, 225));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }
        });
    }




    private JPanel createRemoveBookPanel() {
        JPanel removeBookPanel = new JPanel();
        removeBookPanel.setLayout(new BorderLayout(10, 10));

        // Create a panel for the input section
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout()); // Center components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Add "Enter Book ID" label
        JLabel bookIdLabel = new JLabel("Enter Book ID:");
        inputPanel.add(bookIdLabel, gbc);

        // Add the text field for Book ID
        gbc.gridy++;
        JTextField bookIdField = new JTextField(15); // Smaller text area
        inputPanel.add(bookIdField, gbc);

        // Add the input panel to the center of the main panel
        removeBookPanel.add(inputPanel, BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Create the "Remove" button
        JButton removeButton = new JButton("Remove");
        JLabel statusLabel = new JLabel(""); // Label to show status message

        removeButton.addActionListener(e -> {
            String bookId = bookIdField.getText();

            if (bookId.isEmpty()) {
                statusLabel.setText("Please enter a Book ID!");
                statusLabel.setForeground(Color.RED);
            } else {
                // Assuming Catalog.removeBook returns the book name or null if not found
                boolean bookName = Catalog.removeBook(bookId);

                if (bookName) {
                    refreshTable();
                    statusLabel.setText("Book removed successfully: " + bookName);
                    statusLabel.setForeground(Color.GREEN);
                    bookIdField.setText(""); // Clear the text field after successful removal
                } else {
                    statusLabel.setText("Book not found!");
                    statusLabel.setForeground(Color.RED);
                }
            }
        });

        // Create the "Cancel" button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            bookIdField.setText(""); // Clear the text field
            statusLabel.setText(""); // Clear the status message
        });

        // Add buttons to the button panel
        buttonPanel.add(removeButton);
        buttonPanel.add(cancelButton);

        // Add the button panel to the bottom of the main panel
        removeBookPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the status label below the buttons
        removeBookPanel.add(statusLabel, BorderLayout.NORTH);

        return removeBookPanel;
    }
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));

        // Create a panel for the search field and button with icon
        JPanel searchFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // Create the "Search Book" field
        JTextField searchField = new JTextField(20);

        // Create the search icon
        ImageIcon searchIcon = new ImageIcon("path/to/search_icon.png"); // Update this with the correct path to your icon
        JLabel searchIconLabel = new JLabel(searchIcon);

        // Add the icon to the left of the search field
        searchFieldPanel.add(searchIconLabel);
        searchFieldPanel.add(searchField);

        // Create the search button
        JButton searchButton = new JButton("Search Book");
        searchButton.addActionListener(e -> {
            String searchQuery = searchField.getText().trim();

            if (searchQuery.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a search query!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Find books based on title or author, and return sorted results
                String sortedBy = "Title"; // You can switch to "Author" based on your requirement
                Node sortedBooks = Catalog.FindBookByTitleOrAuthor(searchQuery, sortedBy);

                if (sortedBooks == null) {
                    JOptionPane.showMessageDialog(this, "No books found!", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    StringBuilder result = new StringBuilder();
                    Node current = sortedBooks;

                    while (current != null) {
                        result.append(current.getBook().getBookTitle())
                                .append(" by ")
                                .append(current.getBook().getAuthor())
                                .append("\n");
                        current = current.getNext();
                    }

                    // Show the results in a message dialog
                    JOptionPane.showMessageDialog(this, result.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Add the components to the search panel
        searchPanel.add(searchFieldPanel, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.SOUTH);

        return searchPanel;
    }



    private void refreshTable() {
        tableModel.setRowCount(0);
        Node current = Catalog.bookList.getHead();
        while (current != null) {
            Book book = current.getBook();
            tableModel.addRow(new Object[]{book.getBookTitle(), book.getAuthor(), book.getGenere()});
            current = current.getNext();
        }
    }

    private void logout() {
        dispose();
        new Login();
    }

    public static void main(String[] args) {
        new LibrarianGUI();
    }
}
