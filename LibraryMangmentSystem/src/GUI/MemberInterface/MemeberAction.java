package GUI.MemberInterface;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import Classes.*;
//
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

    public JPanel createBorrowPanel() {
        JPanel borrowPanel = new JPanel(new GridBagLayout());
        borrowPanel.setBackground(BROWN_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel bookIdLabel = createStyledLabel("Book ID:");
        JTextField bookIdField = createStyledTextField();

        // Set the Member ID field as read-only and pre-fill it with the passed memberId
        JLabel memberIdLabel = createStyledLabel("Member ID:");
        JTextField memberIdField = createStyledTextField();
        memberIdField.setText(memberId);  // Pre-fill Member ID field with the passed memberId
        memberIdField.setEditable(false);  // Make the field read-only

        JButton borrowButton = createStyledButton("Borrow");
        JButton clearButton = createStyledButton("Clear");

        borrowButton.addActionListener(e -> {
            String bookId = bookIdField.getText().trim();

            if (bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                System.out.println("Attempting to borrow book with ID: " + bookId);
                Loan.borrowBook(memberId, bookId);  // Use the memberId passed during initialization
                JOptionPane.showMessageDialog(this, "Book borrowed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                bookIdField.setText("");
            } catch (IOException | ParseException ex) {
                ex.printStackTrace();  // Print the stack trace to the console for debugging
                JOptionPane.showMessageDialog(this, "Error while borrowing book. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            bookIdField.setText("");
        });

        gbc.gridx = 0; gbc.gridy = 0; borrowPanel.add(bookIdLabel, gbc);
        gbc.gridx = 1; borrowPanel.add(bookIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; borrowPanel.add(memberIdLabel, gbc);
        gbc.gridx = 1; borrowPanel.add(memberIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; borrowPanel.add(borrowButton, gbc);
        gbc.gridy = 3; borrowPanel.add(clearButton, gbc);

        return borrowPanel;
    }

    // Similar changes for Return Panel:
    public JPanel createReturnPanel() {
        JPanel returnPanel = new JPanel(new GridBagLayout());
        returnPanel.setBackground(BROWN_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel bookIdLabel = createStyledLabel("Book ID:");
        JTextField bookIdField = createStyledTextField();

        // Set the Member ID field as read-only and pre-fill it with the passed memberId
        JLabel memberIdLabel = createStyledLabel("Member ID:");
        JTextField memberIdField = createStyledTextField();
        memberIdField.setText(memberId);  // Pre-fill Member ID field with the passed memberId
        memberIdField.setEditable(false);  // Make the field read-only

        JButton returnButton = createStyledButton("Return");
        JButton clearButton = createStyledButton("Clear");

        returnButton.addActionListener(e -> {
            String bookId = bookIdField.getText().trim();

            if (bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Loan.returnBook(memberId, bookId);  // Use the memberId passed during initialization
                JOptionPane.showMessageDialog(this, "Book returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                bookIdField.setText("");
            } catch (IOException | ParseException ex) {
                JOptionPane.showMessageDialog(this, "Error while returning book. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            bookIdField.setText("");
        });

        gbc.gridx = 0; gbc.gridy = 0; returnPanel.add(bookIdLabel, gbc);
        gbc.gridx = 1; returnPanel.add(bookIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; returnPanel.add(memberIdLabel, gbc);
        gbc.gridx = 1; returnPanel.add(memberIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; returnPanel.add(returnButton, gbc);
        gbc.gridy = 3; returnPanel.add(clearButton, gbc);

        return returnPanel;
    }


    public JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        searchPanel.setBackground(Color.WHITE);  // White background for the search panel

        // Search Field and Labels
        JLabel searchLabel = new JLabel("Search Book by Title or Author:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 16));
        searchLabel.setForeground(new Color(0, 51, 102));  // Navy Blue for the label
        JTextField searchField = createStyledTextField();
        searchField.setPreferredSize(new Dimension(250, 40)); // Increase height of the search field

        JButton searchButton = createStyledButton("Search");
        JButton clearButton = createStyledButton("Clear");

        // Sorting ComboBox
        String[] sortingOptions = {"Title", "Author"};
        JComboBox<String> sortComboBox = new JComboBox<>(sortingOptions);
        sortComboBox.setSelectedIndex(0);  // Default to sorting by Title

// Setting up layout for Search Panel
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Take more horizontal space for the label
        searchPanel.add(searchLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        searchPanel.add(searchField, gbc);  // Adding the search field

        gbc.gridx = 2;
        gbc.gridwidth = 1;
        searchPanel.add(searchButton, gbc);

        gbc.gridx = 3;
        searchPanel.add(clearButton, gbc);

        gbc.gridx = 4;
        searchPanel.add(new JLabel("Sort By: "), gbc);

        gbc.gridx = 5;
        searchPanel.add(sortComboBox, gbc);

        // Panel for the Table (Below search panel)
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Book ID", "Title", "Author", "Genre", "Availability"}, 0);
        JTable searchResultsTable = new JTable(tableModel);
        searchResultsTable.setFillsViewportHeight(true);  // Fill the viewport with the table

        // Set the table's cell renderer to handle text wrapping
        searchResultsTable.setDefaultRenderer(Object.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JTextArea textArea = new JTextArea(value.toString());
                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);
                textArea.setOpaque(false);
                textArea.setEditable(false);
                textArea.setFont(table.getFont());
                textArea.setPreferredSize(new Dimension(table.getColumnModel().getColumn(column).getWidth(), 0)); // Adjust height based on content
                return textArea;
            }
        });

        // Set row height based on the content (dynamic resizing)
        searchResultsTable.setRowHeight(25);  // Default height for rows, adjust if needed

        searchResultsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Resize columns as per content

        JScrollPane scrollPane = new JScrollPane(searchResultsTable);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Action Listeners for buttons
        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Search query cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            tableModel.setRowCount(0); // Clear previous results

            String sortedBy = (String) sortComboBox.getSelectedItem();
            Node foundBooks = Catalog.FindBookByTitleOrAuthor(query, sortedBy);
            if (foundBooks != null) {
                Node temp = foundBooks;
                while (temp != null) {
                    Book book = temp.getBook();
                    tableModel.addRow(new Object[]{book.getBookID(), book.getBookTitle(), book.getAuthor(), book.getGenere(), book.getAvailablityStatus()});

                    // Set title color to Navy Blue if the book is found
                    JLabel titleLabel = new JLabel(book.getBookTitle());
                    titleLabel.setForeground(new Color(0, 51, 102));  // Navy Blue color for title
                    temp = temp.getNext();
                }
            } else {
                JOptionPane.showMessageDialog(this, "No books found.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> searchField.setText(""));

        // Main layout for search and table
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(searchPanel, BorderLayout.NORTH);  // Add search panel at the top
        mainPanel.add(tablePanel, BorderLayout.CENTER);  // Add table panel below search panel

// Load books from the file and display them
        loadBooksFromFile(tableModel);

        return mainPanel;
    }


    private void loadBooksFromFile(DefaultTableModel tableModel) {
        try (BufferedReader reader = new BufferedReader(new FileReader("catalog.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse the line to create a Book object
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String bookId = parts[0];
                    String title = parts[1];
                    String author = parts[2];
                    String genre = parts[3];
                    boolean availabilityStatus = Boolean.parseBoolean(parts[4]);

                    // Add the book to the table model
                    tableModel.addRow(new Object[]{bookId, title, author, genre, availabilityStatus});
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading book file: " + e.getMessage());
        }
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBackground(LIGHT_BROWN_COLOR);
        textField.setForeground(Color.WHITE);
        return textField;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(LIGHT_BROWN_COLOR);
        button.setForeground(Color.WHITE);
        return button;
    }
}

