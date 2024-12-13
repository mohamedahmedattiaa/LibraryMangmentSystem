package GUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import Classes.*;
import GUI.Login;
//
public class LibrarianGUI extends JFrame {
    private DefaultTableModel tableModel;
    private JTable bookTable;
    private CardLayout card;
    private JPanel mainPanel;
    private AbstractButton booksTable;
    private Component sortBooksPanel;

    public LibrarianGUI() {
        setTitle("Librarian Panel");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Title", "Author", "Genre"}, 0);

        bookTable = new JTable(tableModel);

        card = new CardLayout();
        mainPanel = new JPanel(card);

        mainPanel.add(createBookCatalogPanel(), "Book Catalog");
        mainPanel.add(createCombinedManageBookTapped(), "Manage Books");
        mainPanel.add(createReportsTapedPanel(), "Reports");
        mainPanel.add(createSearchBookTapped(), "Manage Members");

        add(mainPanel, BorderLayout.CENTER);

        JPanel navPanel = createNavPanel();
        add(navPanel, BorderLayout.WEST);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createNavPanel() {
        JPanel NavPanel = new JPanel();
        NavPanel.setLayout(new GridLayout(5, 1, 10, 10));
        NavPanel.setPreferredSize(new Dimension(200, getHeight()));
        NavPanel.setBackground(new Color(92, 64, 51));

        addNavButton(NavPanel, "Manage Books", e -> switchPage("Manage Books"));
        addNavButton(NavPanel,"Manage Members" , e -> switchPage("Manage Members"));
        addNavButton(NavPanel, "Book Catalog", e -> switchPage("Book Catalog"));
        addNavButton(NavPanel, "Reports", e -> switchPage("Reports"));
        addNavButton(NavPanel, "Logout", e -> logout());

        return NavPanel;
    }
    //button
    private void addNavButton(JPanel panel, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        styleNavButton(button);
        panel.add(button);
    }

    private JPanel createBookCatalogPanel() {
        JPanel BookCatalogPanel = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Book Catalog", createViewBooksPanel());
        BookCatalogPanel.add(tabbedPane, BorderLayout.CENTER);
        return BookCatalogPanel;
    }

    private JPanel createReportsTapedPanel() {
        JPanel ReportTaped = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Reports", createReportsPanel());
        ReportTaped.add(tabbedPane, BorderLayout.CENTER);
        return ReportTaped;
    }

    private JPanel createCombinedManageBookTapped(){
        JPanel MangeBook = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Book", createAddBook());
        tabbedPane.addTab("Remove Book", createRemoveBookPanel());
        tabbedPane.addTab("Update book", createUpdateBook());
        tabbedPane.addTab("Search book", createSearchPanel());
        MangeBook.add(tabbedPane, BorderLayout.CENTER);
        return MangeBook;
    }
    //
    private JPanel createSearchBookTapped(){
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Search Book", createSearchPanel());
        searchPanel.add(tabbedPane, BorderLayout.CENTER);
        return searchPanel;
    }

    private void styleNavButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(121, 85, 72));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,  10));

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
    //
    private void switchPage(String pageName) {
        card.show(mainPanel, pageName);
    }
    //
    private JPanel createReportsPanel() {
        JPanel reportsPanel = new JPanel(new BorderLayout());

        // Initialize JTable with an empty table model
        String[] columns = {"Loan ID", "Book Title", "Return Date", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable reportTable = new JTable(tableModel);
        reportTable.setFillsViewportHeight(true);

        // Add the table to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(reportTable);
        reportsPanel.add(scrollPane, BorderLayout.CENTER);

        // Button to view the general reports
        JButton viewReportsButton = new JButton("View Reports");
        viewReportsButton.addActionListener(e -> {
            // Create Report object
            Report report = new Report();
            Catalog catalog = new Catalog();
            try {
                tableModel.setRowCount(0);
                report.displayActiveLoans (catalog);
                report.displayOverdueBooks(catalog);
                report.displayPendingLoans(catalog);
                report.displayPopularGenre(catalog);
            } catch (Exception ex) {
                tableModel.setRowCount(0); // Clear table data on error
                tableModel.addRow(new Object[]{"Error generating report: " + ex.getMessage()});
            }
        });
        reportsPanel.add(viewReportsButton, BorderLayout.SOUTH);
        return reportsPanel;
    }

    //
    private JPanel createAddBook() {
        JPanel addBookPanel = new JPanel(new GridBagLayout());
        addBookPanel.setBackground(new Color(121, 85, 72)); // Color for background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Insets for spacing between components

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Caveat", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        addBookPanel.add(titleLabel, gbc);

        JTextField titleField = new JTextField(20);
        titleField.setFont(new Font("Caveat", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 0;
        addBookPanel.add(titleField, gbc);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setFont(new Font("Caveat", Font.BOLD, 24));
        authorLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        addBookPanel.add(authorLabel, gbc);

        JTextField authorField = new JTextField(20);
        authorField.setFont(new Font("Caveat", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        addBookPanel.add(authorField, gbc);

        JLabel genreLabel = new JLabel("Genre:");
        genreLabel.setFont(new Font("Caveat", Font.BOLD, 24));
        genreLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        addBookPanel.add(genreLabel, gbc);

        JTextField genreField = new JTextField(20);
        genreField.setFont(new Font("Caveat", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        addBookPanel.add(genreField, gbc);

        JLabel successMessageLabel = new JLabel("");
        successMessageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        successMessageLabel.setForeground(Color.GREEN); // Green color for success message
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        addBookPanel.add(successMessageLabel, gbc);

        JButton addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(100, 40));
        addButton.setFont(new Font("Arial", Font.PLAIN, 16));
        addButton.setBackground(new Color(141, 110, 99));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String genre = genreField.getText();

            if (title.isEmpty() ||  author.isEmpty()  ||  genre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }else if(Catalog.searchBookByTitle(title)) {
                JOptionPane.showMessageDialog(this, "The Book is already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Book newBook = new Book(title, author, genre);
            Catalog.addBook(newBook);
            refreshCatalog();

            // Update the success message label
            successMessageLabel.setText("Book is added successfully!");
        });


        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 40));
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
        clearButton.setBackground(new Color(141, 110, 99));
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(e -> {
            titleField.setText("");
            authorField.setText("");
            genreField.setText("");
            successMessageLabel.setText("");
        });

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(121, 85, 72));
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);
        addBookPanel.add(buttonPanel, gbc);

        return addBookPanel;
    }


    private JPanel createUpdateBook() {
        JPanel updateBookPanel = new JPanel(new GridBagLayout());
        updateBookPanel.setBackground(new Color(121, 85, 72)); // Background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Caveat", Font.BOLD, 24)); // Font size for title label
        titleLabel.setForeground(Color.WHITE); // Text color
        gbc.gridx = 0;
        gbc.gridy = 0;
        updateBookPanel.add(titleLabel, gbc);

        JTextField titleField = new JTextField(20);
        titleField.setFont(new Font("Caveat", Font.PLAIN, 16)); // Font for title input field
        gbc.gridx = 1;
        gbc.gridy = 0;
        updateBookPanel.add(titleField, gbc);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setFont(new Font("Caveat", Font.BOLD, 24));
        authorLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        updateBookPanel.add(authorLabel, gbc);

        JTextField authorField = new JTextField(20);
        authorField.setFont(new Font("Caveat", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        updateBookPanel.add(authorField, gbc);

        JLabel genreLabel = new JLabel("Genre:");
        genreLabel.setFont(new Font("Caveat", Font.BOLD, 24));
        genreLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        updateBookPanel.add(genreLabel, gbc);

        JTextField genreField = new JTextField(20);
        genreField.setFont(new Font("Caveat", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        updateBookPanel.add(genreField, gbc);

// Add book icon
        JLabel successMessageLabel = new JLabel("");
        successMessageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        successMessageLabel.setForeground(Color.GREEN); // Green color for success message
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(10, 10, 30, 10);
        updateBookPanel.add(successMessageLabel, gbc);

        JButton updateButton = new JButton("Update");
        updateButton.setPreferredSize(new Dimension(100, 40));
        updateButton.setFont(new Font("Arial", Font.PLAIN, 16));
        updateButton.setBackground(new Color(141, 110, 99)); // Brown color for button
        updateButton.setForeground(Color.WHITE); // White text for button
        updateButton.addActionListener(e -> {
            // Prompt the user to enter the book ID
            String bookId = JOptionPane.showInputDialog(this, "Enter the Book ID to update:");

            // If no ID is entered, exit the function
            if (bookId == null && bookId.trim().isEmpty()) {
                return; // No ID entered, do nothing
            }

            // Check if the book exists and is available
            String checkAvailabilityMessage = Catalog.checkBookAvailability(bookId); // Call a method to check availability
            if (!checkAvailabilityMessage.equals("Book exists and is available!")) {
                JOptionPane.showMessageDialog(this, checkAvailabilityMessage, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Prompt the user for availability status (true or false)
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Do you want to mark the book as available?", "Availability Status", JOptionPane.YES_NO_OPTION);
            boolean availabilityStatus = (confirm == JOptionPane.YES_OPTION);

            // Validate input for title, author, and genre
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String genre = genreField.getText().trim();

            if (title.isEmpty()  ||  author.isEmpty()  || genre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return; // If any field is empty, do not proceed
            }

            // Call the update method from Catalog class
            String updateMessage = Catalog.updateBook(bookId, availabilityStatus, title, author, genre);

            // Display appropriate message based on result
            if (updateMessage.contains("not found")) {
                JOptionPane.showMessageDialog(this, updateMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, updateMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
                successMessageLabel.setText("The Book is Updated successfully!");
                refreshCatalog(); // Refresh the table to reflect the updated details
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 40));
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
        clearButton.setBackground(new Color(141, 110, 99));
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(e -> {
            titleField.setText("");
            authorField.setText("");
            genreField.setText("");
            successMessageLabel.setText("");
        });

        // Add buttons to panel, centered horizontally
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(121, 85, 72)); // Background color
        buttonPanel.add(updateButton);
        buttonPanel.add(clearButton);
        updateBookPanel.add(buttonPanel, gbc);

        return updateBookPanel;
    } //


    private JPanel createRemoveBookPanel() {
        JPanel RemoveBookPanel = new JPanel(new GridBagLayout());
        RemoveBookPanel.setBackground(new Color(121, 85, 72));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel bookIdLabel = new JLabel("Enter Book ID:");
        bookIdLabel.setFont(new Font("Caveat", Font.BOLD, 24));
        bookIdLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        RemoveBookPanel.add(bookIdLabel, gbc);

        JTextField bookIdField = new JTextField(20);
        bookIdField.setFont(new Font("Caveat", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 0;
        RemoveBookPanel.add(bookIdField, gbc);

        JLabel successMessageLabel = new JLabel("");
        successMessageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        successMessageLabel.setForeground(Color.GREEN); // Green color for success message
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        RemoveBookPanel.add(successMessageLabel, gbc);

        JButton removeButton = new JButton("Remove");
        removeButton.setPreferredSize(new Dimension(100, 40));
        removeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        removeButton.setBackground(new Color(141, 110, 99));
        removeButton.setForeground(Color.WHITE);
        removeButton.addActionListener(e -> {
            String bookId = bookIdField.getText();
            if (bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Book ID is required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this book?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                if (Catalog.removeBook(bookId)) {
                    refreshCatalog();
                    JOptionPane.showMessageDialog(this, "Book removed successfully!");
                    successMessageLabel.setText("The Book is Removed successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Book not found or could not be removed!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 40));
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
        clearButton.setBackground(new Color(141, 110, 99));
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(e -> {
            bookIdField.setText("");
            successMessageLabel.setText("");
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(121, 85, 72));
        buttonPanel.add(removeButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        RemoveBookPanel.add(buttonPanel, gbc);

        return RemoveBookPanel;
    }  //



    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(new Color(121, 85, 72)); // لون الخلفية بني

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // مسافة بين المكونات

        // Label for Search
        JLabel searchLabel = new JLabel("Search by Title or Author:");
        searchLabel.setFont(new Font("Caveat", Font.BOLD, 24)); // تكبير الخط
        searchLabel.setForeground(Color.WHITE); // اللون الأبيض للنص
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        searchPanel.add(searchLabel, gbc);

        // TextField for Search
        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Caveat", Font.PLAIN, 16)); // تكبير الخط للـ TextField
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        searchPanel.add(searchField, gbc);

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 40)); // ضبط حجم الزر
        searchButton.setFont(new Font("Arial", Font.PLAIN, 16)); // حجم الخط
        searchButton.setBackground(new Color(141, 110, 99)); // لون الزر بني مناسب
        searchButton.setForeground(Color.WHITE); // النص باللون الأبيض
        searchButton.addActionListener(e -> {
            String query = searchField.getText();

            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Search query cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Here you can call the method to search for books by title or author
            Node result = Catalog.FindBookByTitleOrAuthor(query, "Title"); // Assuming method exists
            if (result == null) {
                JOptionPane.showMessageDialog(this, "No books found!", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder resultText = new StringBuilder();
                Node current = result;
                while (current != null) {
                    resultText.append(current.getBook().getBookTitle())
                            .append(" by ")
                            .append(current.getBook().getAuthor())
                            .append("\n");
                    current = current.getNext();
                }
                JOptionPane.showMessageDialog(this, resultText.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Clear Button
        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 40)); // ضبط حجم الزر
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16)); // حجم الخط
        clearButton.setBackground(new Color(141, 110, 99)); // نفس لون زر Search
        clearButton.setForeground(Color.WHITE); // النص باللون الأبيض
        clearButton.addActionListener(e -> searchField.setText("")); // Clear the search field

        // Panel for buttons (Search and Clear)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // مسافة بين الأزرار
        buttonPanel.setBackground(new Color(121, 85, 72)); // لون الخلفية نفس الباكجراوند
        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);

        // Adding components to the main panel
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        searchPanel.add(buttonPanel, gbc);

        return searchPanel;
    }


    private void refreshCatalog() {
        tableModel.setRowCount(0);
        Node current = Catalog.bookList.getHead();
        while (current != null) {
            Book book = current.getBook();
            tableModel.addRow(new Object[]{book.getBookTitle(), book.getAuthor(), book.getGenere()});
            current = current.getNext();
        }
    } //
    private JPanel createViewBooksPanel() {
        JPanel viewBooksPanel = new JPanel(new BorderLayout());

        // Create "View Books" button
        JButton viewBooksButton = new JButton("Refresh Books");

        // Table for displaying books
        String[] columnNames = {"Book ID", "Title", "Author", "Availability"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable booksTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(booksTable);

        // Create the combo box with sorting options
        String[] sortOptions = {"Sort by Author", "Sort by Title"};
        JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);

        // Create the sorting button
        JButton sortButton = new JButton("Sort");

        // Action for the "Sort" button
        sortButton.addActionListener(e -> {
            String selectedOption = (String) sortComboBox.getSelectedItem();

            // Call the sorting method based on the selected option
            if ("Sort by Author".equals(selectedOption)) {
                Catalog.bookList.sorting("Author");  // Sort books by Author
            } else if ("Sort by Title".equals(selectedOption)) {
                Catalog.bookList.sorting("Title");   // Sort books by Title
            }

            // Refresh the books table after sorting
            refreshBooksTable(tableModel, booksTable);
        });

        // Action for "View Books" button
        viewBooksButton.addActionListener(e -> {
            try {
                // Clear the table before adding new data
                tableModel.setRowCount(0);

                // Traverse through the catalog and populate the table
                Node temp = Catalog.bookList.getHead(); // Access the linked list head
                while (temp != null) {
                    Book book = temp.getBook();
                    Object[] row = {
                            book.getBookID(),
                            book.getBookTitle(),
                            book.getAuthor(),
                            book.getAvailablityStatus() ? "Available" : "Not Available"
                    };
                    tableModel.addRow(row); // Add book data to the table
                    temp = temp.getNext(); // Move to the next node
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(viewBooksPanel, "Error displaying books: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add table, combo box, and buttons to the panel
        JPanel topPanel = new JPanel();
        topPanel.add(sortComboBox);
        topPanel.add(sortButton);

        viewBooksPanel.add(topPanel, BorderLayout.NORTH);
        viewBooksPanel.add(scrollPane, BorderLayout.CENTER);
        viewBooksPanel.add(viewBooksButton, BorderLayout.SOUTH);

        return viewBooksPanel;
    }



    // Method to refresh the books table (after sorting)
    private void refreshBooksTable(DefaultTableModel tableModel, JTable booksTable) {
        try {
            tableModel.setRowCount(0); // Clear the existing table data

            Node temp = Catalog.bookList.getHead(); // Access the linked list head
            while (temp != null) {
                Book book = temp.getBook();
                Object[] row = {
                        book.getBookID(),
                        book.getBookTitle(),
                        book.getAuthor(),
                        book.getAvailablityStatus() ? "Available" : "Not Available"
                };
                tableModel.addRow(row); // Add book data to the table
                temp = temp.getNext(); // Move to the next node
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(booksTable, "Error displaying books: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to refresh the books table (after sorting)
    private void refreshBooksTable() {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) booksTable.getModel();
            tableModel.setRowCount(0); // Clear the existing table data

            Node temp = Catalog.bookList.getHead(); // Access the linked list head
            while (temp != null) {
                Book book = temp.getBook();
                Object[] row = {
                        book.getBookID(),
                        book.getBookTitle(),
                        book.getAuthor(),
                        book.getAvailablityStatus() ? "Available" : "Not Available"
                };
                tableModel.addRow(row); // Add book data to the table
                temp = temp.getNext(); // Move to the next node
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(sortBooksPanel, "Error displaying books: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logout() {
        dispose();
        new Login(); // Assuming Login class exists
    } //

    public static void main(String[] args) {
        new LibrarianGUI();
    }
} //