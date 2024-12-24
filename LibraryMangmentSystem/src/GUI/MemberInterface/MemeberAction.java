package GUI.MemberInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import Classes.*;
public class MemeberAction extends JFrame {
    private DefaultTableModel tableModel;
    private JTable searchResultsTable;
    private CardLayout card;
    private JPanel cards;
    private String memberId;
    private static final Color BROWN_COLOR = new Color(121, 85, 72);
    private static final Color LIGHT_BROWN_COLOR = new Color(141, 110, 99);
    private static final Font LABEL_FONT = new Font("Caveat", Font.BOLD, 18);
    private static final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 16);

    public MemeberAction(String memberId) {
        this.memberId = memberId;
        setTitle("Library Member Interface");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        card = new CardLayout();
        cards = new JPanel(card);

        // Add panels to CardLayout
        cards.add(createBorrowPanel(), "Borrow Book");
        cards.add(createReturnPanel(), "Return Book");
        cards.add(createSearchPanel(), "Search Book");

        setLayout(new BorderLayout());
        add(cards, BorderLayout.CENTER);
        add(createNavigationPanel(), BorderLayout.NORTH);
    }

    private JPanel createNavigationPanel() {
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(BROWN_COLOR);

        JButton borrowButton = createStyledButton("Borrow Book");
        JButton returnButton = createStyledButton("Return Book");
        JButton searchButton = createStyledButton("Search Book");

        borrowButton.addActionListener(e -> card.show(cards, "Borrow Book"));
        returnButton.addActionListener(e -> card.show(cards, "Return Book"));
        searchButton.addActionListener(e -> card.show(cards, "Search Book"));

        navigationPanel.add(borrowButton);
        navigationPanel.add(returnButton);
        navigationPanel.add(searchButton);

        return navigationPanel;
    }

    // Borrow Book Panel
    public JPanel createBorrowPanel() {
        JPanel borrowPanel = new JPanel(new GridBagLayout());
        borrowPanel.setBackground(BROWN_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel bookIdLabel = createStyledLabel("Book ID:");
        JTextField bookIdField = createStyledTextField();

        JLabel memberIdLabel = createStyledLabel("Member ID:");
        JTextField memberIdField = createStyledTextField();
        memberIdField.setText(memberId);
        memberIdField.setEditable(false);

        JButton borrowButton = createStyledButton("Borrow");
        JButton clearButton = createStyledButton("Clear");

        borrowButton.addActionListener(e -> {
            String bookId = bookIdField.getText().trim();
            if (bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Loan.borrowBook(memberId, bookId);
                JOptionPane.showMessageDialog(this, "Book borrowed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                bookIdField.setText("");
                refreshTableData();  // Refresh the table after borrowing a book
            } catch (IOException | ParseException ex) {
                JOptionPane.showMessageDialog(this, "Error while borrowing book. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> bookIdField.setText(""));

        gbc.gridx = 0; gbc.gridy = 0; borrowPanel.add(bookIdLabel, gbc);
        gbc.gridx = 1; borrowPanel.add(bookIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; borrowPanel.add(memberIdLabel, gbc);
        gbc.gridx = 1; borrowPanel.add(memberIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; borrowPanel.add(borrowButton, gbc);
        gbc.gridy = 3; borrowPanel.add(clearButton, gbc);

        return borrowPanel;
    }

    // Return Book Panel
    public JPanel createReturnPanel() {
        JPanel returnPanel = new JPanel(new GridBagLayout());
        returnPanel.setBackground(BROWN_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel bookIdLabel = createStyledLabel("Book ID:");
        JTextField bookIdField = createStyledTextField();

        JLabel memberIdLabel = createStyledLabel("Member ID:");
        JTextField memberIdField = createStyledTextField();
        memberIdField.setText(memberId);
        memberIdField.setEditable(false);

        JButton returnButton = createStyledButton("Return");
        JButton clearButton = createStyledButton("Clear");

        returnButton.addActionListener(e -> {
            String bookId = bookIdField.getText().trim();
            if (bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Loan.returnBook(memberId, bookId);
                JOptionPane.showMessageDialog(this, "Book returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                bookIdField.setText("");
                refreshTableData();
            } catch (IOException | ParseException ex) {
                JOptionPane.showMessageDialog(this, "Error while returning book. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> bookIdField.setText(""));

        gbc.gridx = 0; gbc.gridy = 0; returnPanel.add(bookIdLabel, gbc);
        gbc.gridx = 1; returnPanel.add(bookIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; returnPanel.add(memberIdLabel, gbc);
        gbc.gridx = 1; returnPanel.add(memberIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; returnPanel.add(returnButton, gbc);
        gbc.gridy = 3; returnPanel.add(clearButton, gbc);

        return returnPanel;
    }

    // Search Book Panel
    public JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        searchPanel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Search Book by Title or Author:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 16));
        searchLabel.setForeground(new Color(0, 51, 102));
        JTextField searchField = createStyledTextField();
        searchField.setPreferredSize(new Dimension(250, 40));

        JButton searchButton = createStyledButton("Search");
        JButton clearButton = createStyledButton("Clear");
        JButton sortButton = createStyledButton("Sort");
        JButton refreshButton = createStyledButton("Refresh");

        String[] sortingOptions = {"Title", "Author"};
        JComboBox<String> sortComboBox = new JComboBox<>(sortingOptions);
        sortComboBox.setSelectedIndex(0);

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        searchPanel.add(searchLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        searchPanel.add(searchField, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 1;
        searchPanel.add(searchButton, gbc);

        gbc.gridx = 3;
        searchPanel.add(clearButton, gbc);

        gbc.gridx = 4;
        searchPanel.add(new JLabel("Sort By: "), gbc);

        gbc.gridx = 5;
        searchPanel.add(sortComboBox, gbc);

        gbc.gridx = 6;
        searchPanel.add(sortButton, gbc);

        gbc.gridx = 7;
        searchPanel.add(refreshButton, gbc);

        // Table Setup
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Book ID", "Title", "Author", "Genre", "Availability"}, 0);
        JTable searchResultsTable = new JTable(tableModel);
        searchResultsTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(searchResultsTable);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Action Listener for Search
        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            tableModel.setRowCount(0);

            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Search query cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                loadBooksFromFile(tableModel);
                return;
            }

            String sortedBy = (String) sortComboBox.getSelectedItem();
            Node foundBooks = Catalog.FindBookByTitleOrAuthor(query, sortedBy);
            if (foundBooks != null) {
                Node temp = foundBooks;
                while (temp != null) {
                    Book book = temp.getBook();
                    if (book != null) {
                        tableModel.addRow(new Object[]{book.getBookID(), book.getBookTitle(), book.getAuthor(), book.getGenere(), book.getAvailablityStatus()});
                    }
                    temp = temp.getNext();
                }

                if (tableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "No books found matching the search criteria.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No books found.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

            refreshTableData();
        });

        clearButton.addActionListener(e -> {
            searchField.setText("");
            tableModel.setRowCount(0);
            loadBooksFromFile(tableModel);
        });

        sortButton.addActionListener(e -> {
            String sortedBy = (String) sortComboBox.getSelectedItem();
            tableModel.setRowCount(0);  // Clear table before sorting
            // Perform sorting and refresh table with sorted data
            Catalog.Sorting(sortedBy);
            Node sortedBooks = Catalog.bookList.getHead();
            if (sortedBooks != null) {
                Node temp = sortedBooks;
                while (temp != null) {
                    Book book = temp.getBook();
                    if (book != null) {
                        tableModel.addRow(new Object[]{book.getBookID(), book.getBookTitle(), book.getAuthor(), book.getGenere(), book.getAvailablityStatus()});
                    }
                    temp = temp.getNext();
                }
            }
        });

        // Action Listener for Refresh
        refreshButton.addActionListener(e -> {
            tableModel.setRowCount(0);  // Clear the table
            loadBooksFromFile(tableModel);  // Reload the books from the file or data source
        });

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 8; searchPanel.add(tablePanel, gbc);

        return searchPanel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(LIGHT_BROWN_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 30));
        return textField;
    }

    // Method to refresh the table data
    private void refreshTableData() {
        DefaultTableModel tableModel = (DefaultTableModel) searchResultsTable.getModel();
        tableModel.setRowCount(0);  // Clear existing rows

        // Add updated data
        loadBooksFromFile(tableModel);  // Reload the books from the file or data source
    }

    // This method loads book data into the table
    private void loadBooksFromFile(DefaultTableModel tableModel) {
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] bookData = line.split(",");
                tableModel.addRow(bookData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
