package GUI;
//
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import Classes.Book;
import Classes.Catalog;
import Classes.Node;
import Classes.*;

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

        mainPanel.add(createReportsPanel(), "View Catalog");
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


    //button
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

        button.setPreferredSize(new Dimension(150, 50)); //مقاس الزر

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(141, 110, 99));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(121, 85, 72));
            }
        });
    }

    ///
    private void switchPage(String pageName) {
        cardLayout.show(mainPanel, pageName);
    }

    private JPanel createReportsPanel() {
        JPanel reportsPanel = new JPanel(new BorderLayout());
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false); // لضمان عدم تعديل النص
        JScrollPane scrollPane = new JScrollPane(reportArea);
        reportsPanel.add(scrollPane, BorderLayout.CENTER);

        // زر عرض التقارير
        JButton viewReportsButton = new JButton("View Reports");
        viewReportsButton.addActionListener(e -> {
            // إنشاء كائن من Report
            Report report = new Report();
            Catalog catalog = new Catalog(); // تأكد من تمرير الكتالوج الصحيح
            try {
                // توليد التقرير العام وعرضه في TextArea
                StringBuilder reportContent = new StringBuilder();
                reportContent.append("=== General Library Report ===\n\n");

                // استدعاء الميثودز
                reportContent.append("Active Loans:\n");
                report.displayActiveLoans(catalog);

                reportContent.append("\nOverdue Books:\n");
                report.displayOverdueBooks(catalog);

                reportContent.append("\nPending Loans:\n");
                report.displayPendingLoans(catalog);

                reportContent.append("\nPopular Genres:\n");
                report.displayPopularGenre(catalog);

                // تحديث النص في reportArea
                reportArea.setText(reportContent.toString());
            } catch (Exception ex) {
                reportArea.setText("Error generating report: " + ex.getMessage());
            }
        });

        reportsPanel.add(viewReportsButton, BorderLayout.SOUTH);
        return reportsPanel;
    }

    private JPanel createAddBookPanel() {
        JPanel addBookPanel = new JPanel(new GridBagLayout());
        addBookPanel.setBackground(new Color(121, 85, 72)); // لون الباكجراوند بني

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // المسافة بين المكونات

        // تحميل الفونت من المسار المحدد
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("D:/clion/Project Data/LibraryMangmentSystem/src/GUI/Caveat.ttf")).deriveFont(16f); // حجم 16
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont); // تسجيل الفونت مع البيئة الرسومية
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();  // في حالة حدوث خطأ
        }

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Caveat", Font.BOLD, 24)); // تكبير الخط
        titleLabel.setForeground(Color.WHITE); // اللون الأبيض للكتابة
        gbc.gridx = 0;
        gbc.gridy = 0;
        addBookPanel.add(titleLabel, gbc);

        JTextField titleField = new JTextField(20);
        titleField.setFont(new Font("Caveat", Font.PLAIN, 16)); // تكبير الخط للـ TextField
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
        authorField.setFont(new Font("Caveat", Font.PLAIN, 16)); // تطبيق الفونت
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
        genreField.setFont(new Font("Caveat", Font.PLAIN, 16)); // تطبيق الفونت
        gbc.gridx = 1;
        gbc.gridy = 2;
        addBookPanel.add(genreField, gbc);

        // إضافة أيقونة للكتاب
        ImageIcon bookIcon = new ImageIcon("https://example.com/book_icon.png"); // استخدم URL مباشر
        JLabel iconLabel = new JLabel(bookIcon);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 3; // يكون الأيقونة تكمل الثلاث صفوف
        addBookPanel.add(iconLabel, gbc);


        JButton addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(100, 40)); // Adjust button size
        addButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Adjust font size
        addButton.setBackground(new Color(141, 110, 99)); // اللون البني المناسب
        addButton.setForeground(Color.WHITE); // اللون الأبيض للنص
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

// Clear Button
        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 40)); // Adjust button size
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Adjust font size
        clearButton.setBackground(new Color(141, 110, 99)); // نفس اللون الذي كان مستخدم في Add
        clearButton.setForeground(Color.WHITE); // اللون الأبيض للنص
        clearButton.addActionListener(e -> {
            titleField.setText("");
            authorField.setText("");
            genreField.setText("");
        });

// Add buttons to panel, place them in a new row, centered horizontally
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // جعل كل زر يأخذ عمود واحد فقط
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // ضبط المسافة بين الأزرار
        buttonPanel.setBackground(new Color(121, 85, 72)); // نفس اللون البني السابق للباكجراوند
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);
        addBookPanel.add(buttonPanel, gbc);

        return addBookPanel;
    }
    private JPanel createRemoveBookPanel() {
        JPanel removeBookPanel = new JPanel(new GridBagLayout());
        removeBookPanel.setBackground(new Color(121, 85, 72)); // لون الخلفية بني

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // مسافة بين المكونات

        // Label for Book ID
        JLabel bookIdLabel = new JLabel("Enter Book ID:");
        bookIdLabel.setFont(new Font("Caveat", Font.BOLD, 24)); // تكبير الخط
        bookIdLabel.setForeground(Color.WHITE); // اللون الأبيض للنص
        gbc.gridx = 0;
        gbc.gridy = 0;
        removeBookPanel.add(bookIdLabel, gbc);

        // TextField for Book ID
        JTextField bookIdField = new JTextField(20);
        bookIdField.setFont(new Font("Caveat", Font.PLAIN, 16)); // تكبير الخط للـ TextField
        gbc.gridx = 1;
        gbc.gridy = 0;
        removeBookPanel.add(bookIdField, gbc);

        // Remove Button
        JButton removeButton = new JButton("Remove");
        removeButton.setPreferredSize(new Dimension(100, 40)); // ضبط حجم الزر
        removeButton.setFont(new Font("Arial", Font.PLAIN, 16)); // حجم الخط
        removeButton.setBackground(new Color(141, 110, 99)); // لون الزر بني مناسب
        removeButton.setForeground(Color.WHITE); // النص باللون الأبيض
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

        // Clear Button
        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 40)); // ضبط حجم الزر
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16)); // حجم الخط
        clearButton.setBackground(new Color(141, 110, 99)); // نفس لون زر Remove
        clearButton.setForeground(Color.WHITE); // النص باللون الأبيض
        clearButton.addActionListener(e -> bookIdField.setText(""));

        // Panel for buttons (Remove and Clear)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // مسافة بين الأزرار
        buttonPanel.setBackground(new Color(121, 85, 72)); // لون الخلفية نفس الباكجراوند
        buttonPanel.add(removeButton);
        buttonPanel.add(clearButton);

        // Adding components to the main panel
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        removeBookPanel.add(buttonPanel, gbc);

        return removeBookPanel;
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
        clearButton.addActionListener(e -> searchField.setText(""));

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
