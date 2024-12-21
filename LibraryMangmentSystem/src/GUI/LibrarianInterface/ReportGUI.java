package GUI.LibrarianInterface;
import Classes.Catalog;
import Classes.Report;
import Classes.Loan;
import Classes.PendingRequestsQueue;
import Classes.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.Date;

public class ReportGUI extends JFrame {
    private JTextField memberIdField;
    private JButton generateMemberReportButton;
    private JTable memberReportTable, generalReportTable;
    private DefaultTableModel memberTableModel, generalTableModel;
    private JLabel statusLabel;

    public ReportGUI(Catalog catalog) {
        // Set up main frame
        setTitle("Library Management System - Reports");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Member Report Tab
        JPanel memberReportPanel = createMemberReportPanel(catalog);
        tabbedPane.addTab("Member Report", memberReportPanel);

        // General Report Tab
        JPanel generalReportPanel = createGeneralReportPanel(catalog);
        tabbedPane.addTab("General Report", generalReportPanel);

        // Add Tabbed Pane to Frame
        add(tabbedPane);
    }

    public JPanel createMemberReportPanel(Catalog catalog) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        JLabel titleLabel = new JLabel("Generate Member Report", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 51, 102)); // Navy Blue
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Member ID Field
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel memberIdLabel = new JLabel("Enter Member ID:");
        memberIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(memberIdLabel, gbc);

        gbc.gridx = 1;
        memberIdField = new JTextField(20);
        panel.add(memberIdField, gbc);

        // Report Type ComboBox
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel reportTypeLabel = new JLabel("Select Report Type:");
        reportTypeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(reportTypeLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> reportTypeComboBox = new JComboBox<>(new String[]{"Active Loans", "Pending Loans", "Overdue Books"});
        reportTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(reportTypeComboBox, gbc);

        // Generate Report Button
        gbc.gridy = 3;
        gbc.gridx = 0;
        generateMemberReportButton = new JButton("Generate Report");
        generateMemberReportButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateMemberReportButton.setBackground(new Color(102, 178, 255)); // Light Blue
        generateMemberReportButton.setForeground(Color.WHITE);
        panel.add(generateMemberReportButton, gbc);

        // Status Label
        gbc.gridx = 1;
        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setForeground(Color.RED);
        panel.add(statusLabel, gbc);

        // Member Report Table
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        memberTableModel = new DefaultTableModel();
        memberTableModel.addColumn("Report Type");
        memberTableModel.addColumn("Details");

        memberReportTable = new JTable(memberTableModel);
        JScrollPane memberTableScroll = new JScrollPane(memberReportTable);
        panel.add(memberTableScroll, gbc);

// Add Action Listener for Button
        generateMemberReportButton.addActionListener(e -> {
            String memberId = memberIdField.getText().trim();
            String reportType = (String) reportTypeComboBox.getSelectedItem();

            if (memberId.isEmpty()) {
                statusLabel.setText("Please enter a valid Member ID.");
                return;
            }

            if (reportType == null) {
                statusLabel.setText("Please select a valid report type.");
                return;
            }
            statusLabel.setText("");
            memberTableModel.setRowCount(0);
            JTable memberReport = createMemberReportTable(catalog, memberId, reportType);
            memberTableModel = (DefaultTableModel) memberReport.getModel();
            memberReportTable.setModel(memberTableModel);
        });

        return panel;
    }


//    public void displayMemberReport(String memberId, Catalog catalog) {
//        // Add data to the table for Active Loans, Pending Loans, Overdue Books
//        // For Active Loans
//        Report.displayActiveLoansForMember(memberId);
//
//        // For Pending Loans
//        Report.displayPendingLoansForMember(memberId);
//
//        // Add rows to the table with respective results
//        memberTableModel.addRow(new Object[]{"Active Loans", "Details here"});
//        memberTableModel.addRow(new Object[]{"Pending Loans", "Details here"});
//    }

    public JPanel createGeneralReportPanel(Catalog catalog) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        JLabel titleLabel = new JLabel("General Library Report", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 51, 102)); // Navy Blue
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Tabbed Pane for General Reports
        JTabbedPane tabbedPane = new JTabbedPane();

        // Active Loans Tab
        JPanel activeLoansPanel = new JPanel(new BorderLayout());
        JTable activeLoansTable = createReportTable(catalog, "Active Loans");
        activeLoansPanel.add(new JScrollPane(activeLoansTable), BorderLayout.CENTER);
        tabbedPane.addTab("Active Loans", activeLoansPanel);

        // Overdue Books Tab
        JPanel overdueBooksPanel = new JPanel(new BorderLayout());
        JTable overdueBooksTable = createReportTable(catalog, "Overdue Books");
        overdueBooksPanel.add(new JScrollPane(overdueBooksTable), BorderLayout.CENTER);
        tabbedPane.addTab("Overdue Books", overdueBooksPanel);

        // Pending Loans Tab
        JPanel pendingLoansPanel = new JPanel(new BorderLayout());
        JTable pendingLoansTable = createReportTable(catalog, "Pending Loans");
        pendingLoansPanel.add(new JScrollPane(pendingLoansTable), BorderLayout.CENTER);
        tabbedPane.addTab("Pending Loans", pendingLoansPanel);

        // Popular Genre Tab
        JPanel popularGenrePanel = new JPanel(new BorderLayout());
        JTable popularGenreTable = createReportTable(catalog, "Popular Genre");
        popularGenrePanel.add(new JScrollPane(popularGenreTable), BorderLayout.CENTER);
        tabbedPane.addTab("Popular Genre", popularGenrePanel);

        // Add Tabbed Pane to the Panel
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(tabbedPane, gbc);

        return panel;
    }

    private JTable createReportTable(Catalog catalog, String reportType) {
        // Create the table model
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Report Type");
        tableModel.addColumn("Details");

        switch (reportType) {
            case "Active Loans":
                for (Loan loan : Loan.activeLoans) {
                    Book book = catalog.searchBook(loan.getBookId());
                    if (book != null) {
                        tableModel.addRow(new Object[]{
                                reportType,
                                "Loan ID: " + loan.getLoanID() + " | Book Title: " + book.getBookTitle() +
                                        " | Member: " + loan.getMemberId() + " | Return Date: " + loan.getReturnDate()
                        });
                    }
                }
                break;
            case "Overdue Books":
                Date currentDate = new Date();
                for (Loan loan : Loan.activeLoans) {
                    if (loan.getReturnDate().before(currentDate)) {
                        Book book = catalog.searchBook(loan.getBookId());
                        if (book != null) {
                            tableModel.addRow(new Object[]{
                                    reportType,
                                    "Loan ID: " + loan.getLoanID() + " | Book Title: " + book.getBookTitle() +
                                            " | Member: " + loan.getMemberId() + " | Return Date: " + loan.getReturnDate()
                            });
                        }
                    }
                }
                break;
            case "Pending Loans":
                for (Loan loan : PendingRequestsQueue.request) {
                    Book book = catalog.searchBook(loan.getBookId());
                    if (book != null) {
                        tableModel.addRow(new Object[]{
                                reportType,
                                "Loan ID: " + loan.getLoanID() + " | Book Title: " + book.getBookTitle() +
                                        " | Requested on: " + loan.getIssueDate()
                        });
                    }
                }
                break;
            case "Popular Genre":
                String popularGenreOutput = Report.displayPopularGenre(catalog);
                tableModel.addRow(new Object[]{
                        reportType,
                        popularGenreOutput
                });
                break;
        }
        return new JTable(tableModel);
    }

    private JTable createMemberReportTable(Catalog catalog, String memberId, String reportType) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Report Type");
        tableModel.addColumn("Details");

        switch (reportType) {
            case "Active Loans":
                boolean foundActiveLoans = false;
                for (Loan loan : Loan.activeLoans) {
                    if (loan.getMemberId().equals(memberId)) {
                        Book book = catalog.searchBook(loan.getBookId());
                        if (book != null) {
                            tableModel.addRow(new Object[]{
                                    reportType,
                                    "Loan ID: " + loan.getLoanID() + " | Book Title: " + book.getBookTitle() +
                                            " | Return Date: " + loan.getReturnDate()
                            });
                            foundActiveLoans = true;
                        }
                    }
                }
                if (!foundActiveLoans) {
                    tableModel.addRow(new Object[]{
                            reportType,
                            "No active loans for this member."
                    });
                }
                break;

            case "Pending Loans":
                boolean foundPendingLoans = false;
                for (Loan loan : PendingRequestsQueue.request) {
                    if (loan.getMemberId().equals(memberId)) {
                        tableModel.addRow(new Object[]{
                                reportType,
                                "Loan ID: " + loan.getLoanID() + " | Book ID: " + loan.getBookId() +
                                        " | Requested on: " + loan.getIssueDate()
                        });
                        foundPendingLoans = true;
                    }
                }
                if (!foundPendingLoans) {
                    tableModel.addRow(new Object[]{
                            reportType,
                            "No pending requests for this member."
                    });
                }
                break;

            case "Overdue Books":
                boolean foundOverdueBooks = false;
                Date currentDate = new Date();
                for (Loan loan : Loan.activeLoans) {
                    if (loan.getReturnDate().before(currentDate) && loan.getMemberId().equals(memberId)) {
                        Book book = catalog.searchBook(loan.getBookId());
                        if (book != null) {
                            tableModel.addRow(new Object[]{
                                    reportType,
                                    "Loan ID: " + loan.getLoanID() + " | Book Title: " + book.getBookTitle() +
                                            " | Return Date: " + loan.getReturnDate()
                            });
                            foundOverdueBooks = true;
                        }
                    }
                }
                if (!foundOverdueBooks) {
                    tableModel.addRow(new Object[]{
                            reportType,
                            "No overdue books for this member."
                    });
                }
                break;
        }

        return new JTable(tableModel);
    }



    public static void main(String[] args) {
        Catalog catalog = new Catalog();
        SwingUtilities.invokeLater(() -> {
            ReportGUI gui = new ReportGUI(catalog);
            gui.setVisible(true);
        });
    }
}