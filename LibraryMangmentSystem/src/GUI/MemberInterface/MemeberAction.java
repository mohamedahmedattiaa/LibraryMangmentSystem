package GUI.MemberInterface;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

import Classes.Catalog;
import Classes.Loan;
//
public class MemeberAction extends JFrame {
    private DefaultTableModel tableModel;
    private JTable searchResultsTable;
    private CardLayout card;
    private JPanel cards;

    private static final Color BROWN_COLOR = new Color(121, 85, 72);
    private static final Color LIGHT_BROWN_COLOR = new Color(141, 110, 99);
    private static final Font LABEL_FONT = new Font("Caveat", Font.BOLD, 18);
    private static final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 16);

    public MemeberAction() {
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

        JLabel memberIdLabel = createStyledLabel("Member ID:");
        JTextField memberIdField = createStyledTextField();

        JButton borrowButton = createStyledButton("Borrow");
        JButton clearButton = createStyledButton("Clear");

        borrowButton.addActionListener(e -> {
            String bookId = bookIdField.getText().trim();
            String memberId = memberIdField.getText().trim();

            if (bookId.isEmpty() || memberId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Loan.borrowBook(memberId, bookId);
                JOptionPane.showMessageDialog(this, "Book borrowed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                bookIdField.setText("");
                memberIdField.setText("");
            } catch (IOException | ParseException ex) {
                JOptionPane.showMessageDialog(this, "Error while borrowing book. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            bookIdField.setText("");
            memberIdField.setText("");
        });

        gbc.gridx = 0; gbc.gridy = 0; borrowPanel.add(bookIdLabel, gbc);
        gbc.gridx = 1; borrowPanel.add(bookIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; borrowPanel.add(memberIdLabel, gbc);
        gbc.gridx = 1; borrowPanel.add(memberIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; borrowPanel.add(borrowButton, gbc);
        gbc.gridy = 3; borrowPanel.add(clearButton, gbc);

        return borrowPanel;
    }

    public JPanel createReturnPanel() {
        JPanel returnPanel = new JPanel(new GridBagLayout());
        returnPanel.setBackground(BROWN_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel bookIdLabel = createStyledLabel("Book ID:");
        JTextField bookIdField = createStyledTextField();

        JLabel memberIdLabel = createStyledLabel("Member ID:");
        JTextField memberIdField = createStyledTextField();

        JButton returnButton = createStyledButton("Return");
        JButton clearButton = createStyledButton("Clear");

        returnButton.addActionListener(e -> {
            String bookId = bookIdField.getText().trim();
            String memberId = memberIdField.getText().trim();

            if (bookId.isEmpty() || memberId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Loan.returnBook(memberId, bookId);
                JOptionPane.showMessageDialog(this, "Book returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                bookIdField.setText("");
                memberIdField.setText("");
            } catch (IOException | ParseException ex) {
                JOptionPane.showMessageDialog(this, "Error while returning book. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            bookIdField.setText("");
            memberIdField.setText("");
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
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(BROWN_COLOR);

        JTextField searchField = createStyledTextField();
        JButton searchButton = createStyledButton("Search");
        JButton clearButton = createStyledButton("Clear");

        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Search query cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            tableModel.setRowCount(0); // Clear previous results
//            Catalog.FindBookByTitleOrAuthor(query).forEach(book -> {
//                tableModel.addRow(new Object[]{book.getBookTitle(), book.getAuthor(), book.getGenere()});
//            });
        });

        clearButton.addActionListener(e -> searchField.setText(""));

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(BROWN_COLOR);
        inputPanel.add(searchField);
        inputPanel.add(searchButton);
        inputPanel.add(clearButton);

        tableModel = new DefaultTableModel(new String[]{"Title", "Author", "Genre"}, 0);
        searchResultsTable = new JTable(tableModel);

        searchPanel.add(inputPanel, BorderLayout.NORTH);
        searchPanel.add(new JScrollPane(searchResultsTable), BorderLayout.CENTER);

        return searchPanel;
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