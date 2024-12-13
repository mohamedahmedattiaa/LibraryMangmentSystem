package GUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import Classes.*;

import static Classes.Loan.activeLoans;
import static Classes.Loan.returnedLoans;

public class MemberGUI extends JFrame {
    private DefaultTableModel tableModel;
    private JTable bookTable;
    private CardLayout card;
    private JPanel mainPanel;

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
        styleNavButton(button);
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

        // Create a JTextArea to display borrow messages
        JTextArea borrowMessageArea = new JTextArea(5, 20);
        borrowMessageArea.setEditable(false); // Make it read-only
        borrowPanel.add(new JScrollPane(borrowMessageArea), BorderLayout.SOUTH);

        // Add components to borrow book
        JPanel borrowInputPanel = new JPanel(new GridBagLayout());
        borrowInputPanel.setBackground(new Color(121, 85, 72)); // Matching background color

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

        borrowPanel.add(borrowInputPanel, BorderLayout.CENTER);

        // Handle borrow button click
        borrowButton.addActionListener(e -> {
            String memberID = memberIDField.getText();
            String bookID = bookIDField.getText();
            Catalog catalog = new Catalog();  // Initialize catalog, or pass it if needed

            try {
                borrowBook(memberID, bookID, catalog, borrowMessageArea);
            } catch (IOException ex) {
                borrowMessageArea.append("Error occurred while borrowing the book.\n");
            }
        });

        return borrowPanel;
    }

    private JPanel createReturnPanel() {
        JPanel returnPanel = new JPanel(new BorderLayout());

        // Create a JTextArea to display return messages
        JTextArea returnMessageArea = new JTextArea(5, 20);
        returnMessageArea.setEditable(false); // Make it read-only
        returnPanel.add(new JScrollPane(returnMessageArea), BorderLayout.SOUTH);

        // Add components to return book
        JPanel returnInputPanel = new JPanel(new GridBagLayout());
        returnInputPanel.setBackground(new Color(121, 85, 72)); // Matching background color

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

        returnPanel.add(returnInputPanel, BorderLayout.CENTER);

        // Handle return button click
        returnButton.addActionListener(e -> {
            String memberID = memberIDField.getText();
            String bookID = bookIDField.getText();
            Catalog catalog = new Catalog();  // Initialize catalog, or pass it if needed

            try {
                returnBook(memberID, bookID, catalog, returnMessageArea);
            } catch (IOException ex) {
                returnMessageArea.append("Error occurred while returning the book.\n");
            }
        });

        return returnPanel;
    }


    public static void borrowBook(String memberID, String bookId, Catalog catalog, JTextArea messageArea) throws IOException {
        // Search for the member in the catalog
        Member member = Member.SearchMember(memberID);
        if (member == null) {
            messageArea.append("No member found with ID: " + memberID + "\n");
            return;
        }

        // Search for the book in the catalog
        Book book = Catalog.searchBook(bookId);
        if (book != null && book.getAvailablityStatus()) {
            // If the book is available, set it to unavailable and create a loan
            book.setAvailablityStatus(false);
            Loan loan = new Loan(bookId, member.getmemberId());
            activeLoans.add(loan);

            // Update the JTextArea with the success message
            messageArea.append("Loan successfully created: " + loan.getLoanID() + " for book " + book.getBookTitle() +
                    " by member " + member.getName() + ". Your return date is: " + loan.getReturnDate() + ".\n");
        } else if (book != null && !book.getAvailablityStatus()) {
            // If the book is not available, add the loan request to the queue
            messageArea.append("Book " + bookId + " is not available. Adding request for member " + member.getName() + ".\n");
            Loan loan = new Loan(bookId, member.getmemberId());
            PendingRequestsQueue.enqueue(loan); // Add the loan request to the queue
        } else {
            // If the book is not found in the catalog
            messageArea.append("Book with ID " + bookId + " not found in catalog.\n");
        }
    }


    public static void returnBook(String memberId, String bookId, Catalog catalog, JTextArea messageArea) throws IOException, IOException {
        Book book = Catalog.searchBook(bookId);
        Member member = Member.SearchMember(memberId);
        Date returnDate = new Date();

        if (book != null) {
            if (!book.getAvailablityStatus()) {
                Loan currentLoan = new Loan(bookId, member.getmemberId());
                if (returnDate.after(currentLoan.getReturnDate())) {  // checking if the return date has passed
                    messageArea.append("You passed the return date.\n");
                }
                book.setAvailablityStatus(true);
                activeLoans.remove(currentLoan);
                returnedLoans.add(currentLoan);
                messageArea.append("Book " + book.getBookTitle() + " returned by member " + member.getName() + ".\n");
                messageArea.append("Return Date: " + returnDate + "\n");

                if (!PendingRequestsQueue.isEmpty()) {
                    Loan nextLoan = PendingRequestsQueue.dequeue();
                    Member nextMember = Member.SearchMember(nextLoan.getMemberId());
                    messageArea.append("Processing next request for book: " + bookId + " for member: " + nextMember.getName() + "\n");
                    borrowBook(nextMember.getmemberId(), bookId, catalog, messageArea); // edited
                }
            } else {
                messageArea.append("Book " + book.getBookTitle() + " is already available.\n");
            }
        } else {
            messageArea.append("Book with ID " + bookId + " not found in catalog.\n");
        }
    }

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

            // هنا هتكون الميثود للبحث عن الكتب (على سبيل المثال اسم الكتاب أو المؤلف)
            Catalog.searchBookByTitle(query);       });

        // Clear Button
        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 40)); // ضبط حجم الزر
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16)); // حجم الخط
        clearButton.setBackground(new Color(141, 110, 99)); // نفس لون زر Search
        clearButton.setForeground(Color.WHITE); // النص باللون الأبيض
        clearButton.addActionListener(e -> searchField.setText("")); // مسح النص داخل مربع البحث

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

    private JPanel createSearchBookPanel() {
        JPanel searchBookPanel = new JPanel(new GridBagLayout());
        searchBookPanel.setBackground(new Color(121, 85, 72)); // Color for background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Insets for spacing between components

        JLabel searchLabel = new JLabel("Search by Title or Author:");
        searchLabel.setFont(new Font("Caveat", Font.BOLD, 24));
        searchLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchBookPanel.add(searchLabel, gbc);

        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Caveat", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 0;
        searchBookPanel.add(searchField, gbc);

        JLabel resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setForeground(Color.GREEN); // Green color for success message
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        searchBookPanel.add(resultLabel, gbc);

        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 40));
        searchButton.setFont(new Font("Arial", Font.PLAIN, 16));
        searchButton.setBackground(new Color(141, 110, 99));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(e -> {
            String query = searchField.getText();

            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a search term!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Node result = Catalog.FindBookByTitleOrAuthor(query, "title"); // Search by title or author
            if (result == null) {
                JOptionPane.showMessageDialog(this, "No books found!", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder resultText = new StringBuilder();
            Node current = result;
            while (current != null) {
                resultText.append(current.getBook().getBookTitle())
                        .append(" by ")
                        .append(current.getBook().getAuthor())
                        .append("\n");
                current = current.getNext();
            }
            resultLabel.setText("Search Results:");
            JOptionPane.showMessageDialog(this, resultText.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 40));
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
        clearButton.setBackground(new Color(141, 110, 99));
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(e -> {
            searchField.setText("");
            resultLabel.setText("");
        });

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(121, 85, 72));
        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);
        searchBookPanel.add(buttonPanel, gbc);

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
