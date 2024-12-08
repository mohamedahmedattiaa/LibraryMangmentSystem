package GUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import Classes.Book;
import Classes.Catalog;
import Classes.Node;
import java.io.File;
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
        cardLayout.show(mainPanel, pageName);
    }

    private JPanel createCatalogPanel() {
        JPanel catalogPanel = new JPanel(new BorderLayout());
        JScrollPane tableScrollPane = new JScrollPane(bookTable);
        catalogPanel.add(tableScrollPane, BorderLayout.CENTER);
        return catalogPanel;
    }

    private JPanel createAddBookPanel() {
        JPanel addBookPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        addBookPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField bookTitleField = new JTextField();
        JTextField bookAuthorField = new JTextField();
        JTextField bookGenreField = new JTextField();

        addBookPanel.add(new JLabel("Title:"));
        addBookPanel.add(bookTitleField);
        addBookPanel.add(new JLabel("Author:"));
        addBookPanel.add(bookAuthorField);
        addBookPanel.add(new JLabel("Genre:"));
        addBookPanel.add(bookGenreField);

        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(e -> {
            String title = bookTitleField.getText();
            String author = bookAuthorField.getText();
            String genre = bookGenreField.getText();

            if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Book newBook = new Book(title, author, genre);
                Catalog.addBook(newBook);
                refreshTable();
                JOptionPane.showMessageDialog(this, "Book added successfully!");
            }
        });

        addBookPanel.add(addButton);

        return addBookPanel;
    }

    private JPanel createRemoveBookPanel() {
        JPanel removeBookPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        removeBookPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField bookIdField = new JTextField();
        removeBookPanel.add(new JLabel("Enter Book ID:"));
        removeBookPanel.add(bookIdField);

        JButton removeButton = new JButton("Remove Book");
        removeButton.addActionListener(e -> {
            String bookID = bookIdField.getText();
            if (bookID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Book ID!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean removed = Catalog.removeBook(bookID);
                if (removed) {
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Book removed successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Book not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removeBookPanel.add(removeButton);

        return removeBookPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField searchField = new JTextField();
        searchPanel.add(new JLabel("Enter Book Title or Author:"));
        searchPanel.add(searchField);

        JButton searchButton = new JButton("Search Book");
        searchButton.addActionListener(e -> {
            String searchQuery = searchField.getText();
            if (searchQuery.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a search query!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String results = Catalog.FindBookByTitle(searchQuery);
                JOptionPane.showMessageDialog(this, results.isEmpty() ? "No books found!" : results);
            }
        });

        searchPanel.add(searchButton);

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