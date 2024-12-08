package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import Classes.Book;
import Classes.Catalog;
import Classes.linkedlist;
import GUI.Login;

public class LibrarianGUI extends JFrame {
    private JTextArea displayArea;
    public LibrarianGUI() {
        setTitle("Librarian Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(5, 1, 10, 10));
        navPanel.setPreferredSize(new Dimension(220, getHeight()));
        navPanel.setBackground(new Color(92, 64, 51));
        addNavButton(navPanel, "Add Book", e -> showAddBookPanel());
        addNavButton(navPanel, "Remove Book", e -> showRemoveBookPanel());
        addNavButton(navPanel, "Search Book", e -> showSearchPanel());
        addNavButton(navPanel, "View Catalog", e -> showCatalogPanel());
        addNavButton(navPanel, "Logout", e -> logout());
        add(navPanel, BorderLayout.WEST);

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/GUI/color.jpg"));
        imageLabel.setIcon(backgroundIcon);
        add(imageLabel, BorderLayout.CENTER);
        getContentPane().setBackground(new Color(240, 240, 240));
        setLocationRelativeTo(null);
        setVisible(true);
        imageLabel.setPreferredSize(new Dimension(800, 600));


//
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
        displayArea.append("Text to display here\n");
        displayArea.setText("");




        displayArea = new JTextArea();
        displayArea.setEditable(false);
        //JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        displayArea.setFont(new Font("Arial", Font.PLAIN, 14));
        add(scrollPane, BorderLayout.CENTER);
        getContentPane().setBackground(new Color(70, 16, 0));
        setLocationRelativeTo(null);
        setVisible(true);
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
            // 150 red, 75 green, and 0 blue.
            //92, 64, 51
            //rgb(70, 16, 0)
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(121, 85, 72));
            }
        });
    }

    private void showAddBookPanel() {
        JPanel addBookPanel = new JPanel();
        addBookPanel.setLayout(new GridLayout(3, 2, 10, 10));
        addBookPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField bookTitleField = new JTextField();
        JTextField bookAuthorField = new JTextField();
        JTextField bookGenereField = new JTextField();

        addBookPanel.add(new JLabel("Title:"));
        addBookPanel.add(bookTitleField);
        addBookPanel.add(new JLabel("Author:"));
        addBookPanel.add(bookAuthorField);
        addBookPanel.add(new JLabel("Genere:"));
        addBookPanel.add(bookGenereField);

        int result = JOptionPane.showConfirmDialog(this, addBookPanel, "Add Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String title = bookTitleField.getText();
            String author = bookAuthorField.getText();
            String genere = bookGenereField.getText();

            if (title.isEmpty() || author.isEmpty() || genere.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Book newBook = new Book(title, author, genere);
               // Catalog.bookList.addBook(newBook);
                Catalog.addBook(newBook);
                JOptionPane.showMessageDialog(this, "Book added successfully!");
            }
        }
    }

    private void showRemoveBookPanel() {
        String bookID = JOptionPane.showInputDialog(this, "Enter Book ID to Remove:");
        if (bookID != null) {
            boolean removed = Catalog.removeBook(bookID);
            if (removed) {
                JOptionPane.showMessageDialog(this, "Book removed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Book not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showSearchPanel() {
        String searchQuery = JOptionPane.showInputDialog(this, "Enter Book Title or Author to Search:");
        if (searchQuery != null) {
            String results = Catalog.FindBookByTitle(searchQuery);
            displayArea.setText(results);
        }
    }

    private void showCatalogPanel() {
        StringBuilder catalogDisplay = new StringBuilder("Library Catalog:\n");
        Catalog.displayCatalog();
        displayArea.setText(catalogDisplay.toString());
    }

    private void logout() {
        dispose();
        new Login();
    }

    public static void main(String[] args) {
        new LibrarianGUI();
    }
}
