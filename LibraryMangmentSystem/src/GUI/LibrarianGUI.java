package GUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import Classes.*;
import GUI.LibrarianInterface.ManageBook;
import GUI.LibrarianInterface.ManageMember;
import GUI.LibrarianInterface.ReportGUI;
import GUI.Login;
import com.sun.net.httpserver.Authenticator;

public class LibrarianGUI extends JFrame {
    private JFrame frame;
    private JButton reportButton;
    private DefaultTableModel tableModel;
    private CardLayout card;
    private JPanel mainPanel;;
    ManageBook manageBook =new ManageBook();
    ManageMember manage = new ManageMember();
    ReportGUI report = new ReportGUI();
    public LibrarianGUI() {
        setTitle("Librarian Panel");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        card = new CardLayout();
        mainPanel = new JPanel(card);

        mainPanel.add(createBookCatalogPanel(), "Book Catalog");
        mainPanel.add(createCombinedManageBookTapped(), "Manage Books");
        mainPanel.add(createReportsPanel(), "Reports");
        mainPanel.add(createCombinedManageMemberTabbed(), "Manage Members");

        add(mainPanel, BorderLayout.CENTER);

        JPanel navPanel = createNavPanel();
        add(navPanel, BorderLayout.WEST);

        setLocationRelativeTo(null);
        setVisible(true);
        frame = new JFrame("Librarian Dashboard");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Report Button
        reportButton = new JButton("Open Reports");
        reportButton.setBounds(50, 50, 300, 30);
        frame.add(reportButton);

        // Action Listener for Report Button
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReportGUI(); // Open the Report GUI
            }
        });
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
        ManageBook manageBook =new ManageBook();
        tabbedPane.addTab("Book Catalog", manageBook.createViewBooksPanel());
        BookCatalogPanel.add(tabbedPane, BorderLayout.CENTER);
        return BookCatalogPanel;
    }

    private JPanel createCombinedManageBookTapped(){
        JPanel MangeBook = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Book", manageBook.createAddBook());
        tabbedPane.addTab("Remove Book", manageBook.createRemoveBookPanel());
        tabbedPane.addTab("Update book", manageBook.createUpdateBook());
        tabbedPane.addTab("Search book", manageBook.createSearchPanel());
        MangeBook.add(tabbedPane, BorderLayout.CENTER);
        return MangeBook;
    }
    private JPanel createCombinedManageMemberTabbed() {
        JPanel manageMember = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Member", manage.createAddMemberPanel());
        tabbedPane.addTab("Remove Member", manage.createRemoveMemberPanel());
        tabbedPane.addTab("Update Member", manage.createUpdateMemberPanel());
        tabbedPane.addTab("Search Member", manage.createSearchMemberPanel());
        tabbedPane.addTab("Display Members", manage.createDisplayMembersPanel());
        manageMember.add(tabbedPane, BorderLayout.CENTER);
        return manageMember;
    }
    private void styleNavButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(121, 85, 72));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,  10));

        button.setPreferredSize(new Dimension(150, 50));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(141, 110, 99));
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(121, 85, 72));
            }
        });
    }
    private void switchPage(String pageName) {
        card.show(mainPanel, pageName);
    }
    public JPanel createReportsPanel() {
        JPanel reportsPanel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Reports", report.createReportPanel());
        reportsPanel.add(tabbedPane, BorderLayout.CENTER);
        return reportsPanel;
    }

    // Create the Active Loans Panel
    private JPanel createActiveLoansPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Loan ID", "Book Title", "Return Date", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable reportTable = new JTable(tableModel);
        reportTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(reportTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button to load active loans data
        JButton viewReportsButton = new JButton("View Active Loans");
        viewReportsButton.addActionListener(e -> {
            try {
                tableModel.setRowCount(0);
                Report report = new Report();
                Catalog catalog = new Catalog();
                report.displayActiveLoans(catalog);  // Update table with active loans data
            } catch (Exception ex) {
                tableModel.setRowCount(0);
                tableModel.addRow(new Object[]{"Error generating report: " + ex.getMessage()});
            }
        });
        panel.add(viewReportsButton, BorderLayout.SOUTH);
        return panel;
    }

    // Create the Overdue Books Panel
    private JPanel createOverdueBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Loan ID", "Book Title", "Due Date", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable reportTable = new JTable(tableModel);
        reportTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(reportTable);
        panel.add(scrollPane, BorderLayout.CENTER);

// Button to load overdue books data
        JButton viewReportsButton = new JButton("View Overdue Books");
        viewReportsButton.addActionListener(e -> {
            try {
                tableModel.setRowCount(0);
                Report report = new Report();
                Catalog catalog = new Catalog();
                report.displayOverdueBooks(catalog);  // Update table with overdue books data
            } catch (Exception ex) {
                tableModel.setRowCount(0);
                tableModel.addRow(new Object[]{"Error generating report: " + ex.getMessage()});
            }
        });
        panel.add(viewReportsButton, BorderLayout.SOUTH);
        return panel;
    }

    // Create the Pending Loans Panel
    private JPanel createPendingLoansPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Loan ID", "Book Title", "Loan Date", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable reportTable = new JTable(tableModel);
        reportTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(reportTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button to load pending loans data
        JButton viewReportsButton = new JButton("View Pending Loans");
        viewReportsButton.addActionListener(e -> {
            try {
                tableModel.setRowCount(0);
                Report report = new Report();
                Catalog catalog = new Catalog();
                report.displayPendingLoans(catalog);  // Update table with pending loans data
            } catch (Exception ex) {
                tableModel.setRowCount(0);
                tableModel.addRow(new Object[]{"Error generating report: " + ex.getMessage()});
            }
        });
        panel.add(viewReportsButton, BorderLayout.SOUTH);
        return panel;
    }

    // Create the Popular Genre Panel
    private JPanel createPopularGenrePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Genre", "Count"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable reportTable = new JTable(tableModel);
        reportTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(reportTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button to load popular genre data
        JButton viewReportsButton = new JButton("View Popular Genre");
        viewReportsButton.addActionListener(e -> {
            try {
                tableModel.setRowCount(0);
                Report report = new Report();
                Catalog catalog = new Catalog();
                report.displayPopularGenre(catalog);  // Update table with popular genre data
            } catch (Exception ex) {
                tableModel.setRowCount(0);
                tableModel.addRow(new Object[]{"Error generating report: " + ex.getMessage()});
            }
        });
        panel.add(viewReportsButton, BorderLayout.SOUTH);
        return panel;
    }
    private void logout() {
        dispose();
        new Login(); // Assuming Login class exists
    } //

    public static void main(String[] args) {
        new LibrarianGUI();
    }
} //