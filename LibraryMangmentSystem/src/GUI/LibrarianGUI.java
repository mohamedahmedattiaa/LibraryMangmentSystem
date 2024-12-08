package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import Classes.Book;
import Classes.Catalog;
import Classes.Node;
import javax.swing.ImageIcon;

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
        addBookPanel.setLayout(new BorderLayout());

        // Create the "Add Book" button
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(e -> {

            JDialog addBookDialog = new JDialog(this, "Add New Book", true);
            addBookDialog.setSize(400, 250);
            addBookDialog.setLayout(new GridLayout(4, 2, 10, 10));
            addBookDialog.setLocationRelativeTo(this);

            JTextField bookTitleField = new JTextField();
            JTextField bookAuthorField = new JTextField();
            JTextField bookGenreField = new JTextField();

            addBookDialog.add(new JLabel("Title:"));
            addBookDialog.add(bookTitleField);
            addBookDialog.add(new JLabel("Author:"));
            addBookDialog.add(bookAuthorField);
            addBookDialog.add(new JLabel("Genre:"));
            addBookDialog.add(bookGenreField);

            // Add "Add" button in dialog
            JButton confirmButton = new JButton("Add Book");
            JButton cancelButton = new JButton("Cancel");
            confirmButton.addActionListener(event -> {
                String title = bookTitleField.getText();
                String author = bookAuthorField.getText();
                String genre = bookGenreField.getText();

                if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Add book to catalog
                    Book newBook = new Book(title, author, genre);
                    Catalog.addBook(newBook);
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Book added successfully!");
                }
                addBookDialog.dispose(); // Close the dialog after adding the book
            });

            // Add button to dialog and set the dialog visible
            addBookDialog.add(confirmButton);
            addBookDialog.add(cancelButton);
            addBookDialog.setVisible(true);
        });

        addBookPanel.add(addButton, BorderLayout.CENTER);
        return addBookPanel;
    }


    private JPanel createRemoveBookPanel() {
        JPanel removeBookPanel = new JPanel(new BorderLayout());

        // Create the "Remove Book" button
        JButton removeButton = new JButton("Remove Book");
        removeButton.addActionListener(e -> {
            // Open the "Remove Book" dialog
            JDialog removeBookDialog = new JDialog(this, "Remove Book", true);
            removeBookDialog.setSize(400, 150);
            removeBookDialog.setLayout(new GridLayout(3, 1, 10, 10));
            removeBookDialog.setLocationRelativeTo(this);

            // Create a text field for Book ID
            JTextField bookIdField = new JTextField();

            // Create "Remove" button in dialog
            JButton confirmRemoveButton = new JButton("Remove Book");
            confirmRemoveButton.addActionListener(event -> {
                String bookId = bookIdField.getText();

                if (bookId.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a Book ID!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    boolean removed = Catalog.removeBook(bookId);
                    if (removed) {
                        refreshTable();
                        JOptionPane.showMessageDialog(this, "Book removed successfully!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Book not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                removeBookDialog.dispose(); // Close the dialog after removing the book
            });

            // Add components to the dialog
            removeBookDialog.add(new JLabel("Enter Book ID:"));
            removeBookDialog.add(bookIdField);
            removeBookDialog.add(confirmRemoveButton);

            // Show the dialog
            removeBookDialog.setVisible(true);
        });

        removeBookPanel.add(removeButton, BorderLayout.CENTER);
        return removeBookPanel;
    }


    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout());

        // Create the "Search Book" button
        JButton searchButton = new JButton("Search Book");
        searchButton.addActionListener(e -> {
            // Open the "Search Book" dialog
            JDialog searchBookDialog = new JDialog(this, "Search Book", true);
            searchBookDialog.setSize(400, 150);
            searchBookDialog.setLayout(new GridLayout(3, 1, 10, 10));
            searchBookDialog.setLocationRelativeTo(this);

            // Create a text field for search query
            JTextField searchField = new JTextField();

            JButton confirmSearchButton = new JButton("Search Book");
            confirmSearchButton.addActionListener(event -> {
                String searchQuery = searchField.getText();

                if (searchQuery.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a search query!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String results = Catalog.FindBookByTitle(searchQuery);
                    JOptionPane.showMessageDialog(this, results.isEmpty() ? "No books found!" : results);
                }
                searchBookDialog.dispose(); // Close the dialog after searching
            });

            // Add components to the dialog
            searchBookDialog.add(new JLabel("Enter Book Title or Author:"));
            searchBookDialog.add(searchField);
            searchBookDialog.add(confirmSearchButton);

            // Show the dialog
            searchBookDialog.setVisible(true);
        });

        searchPanel.add(searchButton, BorderLayout.CENTER);
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
