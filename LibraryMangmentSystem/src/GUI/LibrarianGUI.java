package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import Classes.Book;
import Classes.Catalog;
import Classes.Node;

public class LibrarianGUI extends JFrame {
    private DefaultTableModel tableModel;
    private JTable bookTable;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public LibrarianGUI() {
        setTitle("Librarian Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Title", "Author", "Genre"}, 0);
        bookTable = new JTable(tableModel);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createCatalogPanel(), "View Catalog");
        mainPanel.add(createAddBookPanel(), "Add Book");
        mainPanel.add(createRemoveBookPanel(), "Remove Book");
        mainPanel.add(createSearchPanel(), "Search Book");

        add(mainPanel, BorderLayout.CENTER);

        JPanel navPanel = createNavPanel();
        add(navPanel, BorderLayout.WEST);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createNavPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(5, 1, 10, 10));
        navPanel.setPreferredSize(new Dimension(200, getHeight()));
        navPanel.setBackground(new Color(92, 64, 51));

        addNavButton(navPanel, "Add Book", e -> switchPage("Add Book"));
        addNavButton(navPanel, "Remove Book", e -> switchPage("Remove Book"));
        addNavButton(navPanel, "Search Book", e -> switchPage("Search Book"));
        addNavButton(navPanel, "View Catalog", e -> switchPage("View Catalog"));
        addNavButton(navPanel, "Logout", e -> logout());

        return navPanel;
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
        JPanel addBookPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();

        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField();

        JLabel genreLabel = new JLabel("Genre:");
        JTextField genreField = new JTextField();

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String genre = genreField.getText();

            if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Book newBook = new Book(title, author, genre);
            Catalog.addBook(newBook); // Assuming Catalog.addBook exists
            refreshTable();
            JOptionPane.showMessageDialog(this, "Book added successfully!");
        });

        addBookPanel.add(titleLabel);
        addBookPanel.add(titleField);
        addBookPanel.add(authorLabel);
        addBookPanel.add(authorField);
        addBookPanel.add(genreLabel);
        addBookPanel.add(genreField);
        addBookPanel.add(addButton);

        return addBookPanel;
    }

    private JPanel createRemoveBookPanel() {
        JPanel removeBookPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        JLabel bookIdLabel = new JLabel("Enter Book ID:");
        JTextField bookIdField = new JTextField();

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            String bookId = bookIdField.getText();

            if (bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Book ID is required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = Catalog.removeBook(bookId); // Assuming Catalog.removeBook exists

            if (success) {
                refreshTable();
                JOptionPane.showMessageDialog(this, "Book removed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Book not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        removeBookPanel.add(bookIdLabel);
        removeBookPanel.add(bookIdField);
        removeBookPanel.add(removeButton);

        return removeBookPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout());

        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(e -> {
            String query = searchField.getText();

            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Search query cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

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

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

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
        new Login(); // Assuming Login class exists
    }

    public static void main(String[] args) {
        new LibrarianGUI();
    }
}
