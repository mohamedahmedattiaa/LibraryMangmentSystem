package GUI.LibrarianInterface;

import Classes.*;
import GUI.MemberInterface.Notification;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class ReportGUI extends JFrame {

    private Catalog catalog; // Reference to the catalog for the library system

    // Constructor to initialize ReportGUI with catalog
    public ReportGUI(Catalog catalog) {
        this.catalog = catalog; // Initialize catalog

        setTitle("Library Report System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the tabbed pane for different reports
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add General Reports tab
        JPanel generalReportPanel = createGeneralReportPanel();
        tabbedPane.addTab("General Reports", generalReportPanel);

        // Add Member Report tab
        JPanel memberReportPanel = createMemberReportPanel();
        tabbedPane.addTab("Member Report", memberReportPanel);

        // Add the tabbedPane to the frame
        add(tabbedPane, BorderLayout.CENTER);
    }

    // Method to create the General Report Panel
    public JPanel createGeneralReportPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Generate General Report", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 51, 102)); // Navy Blue
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel reportTypeLabel = new JLabel("Select Report Type:");
        reportTypeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(reportTypeLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> reportTypeComboBox = new JComboBox<>(new String[]{"All Loans",",Active Loans", "Pending Loans", "Overdue Books", "Returning Loans", "Popular Genre"});
        reportTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(reportTypeComboBox, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JButton generateGeneralReportButton = new JButton("Generate Report");
        generateGeneralReportButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateGeneralReportButton.setBackground(new Color(102, 178, 255)); // Light Blue
        generateGeneralReportButton.setForeground(Color.WHITE);
        panel.add(generateGeneralReportButton, gbc);

        gbc.gridx = 1;
        JLabel statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setForeground(Color.RED);
        panel.add(statusLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        DefaultTableModel generalTableModel = new DefaultTableModel();
        JTable generalReportTable = new JTable(generalTableModel);
        JScrollPane generalTableScroll = new JScrollPane(generalReportTable);
        panel.add(generalTableScroll, gbc);

        generateGeneralReportButton.addActionListener(e -> {
            String reportType = (String) reportTypeComboBox.getSelectedItem();
            updateGeneralReportTable(reportType, generalTableModel);
        });

        return panel;
    }

    private void updateGeneralReportTable(String reportType, DefaultTableModel tableModel) {
        tableModel.setRowCount(0);

        switch (reportType) {
            case "All Loans":
                tableModel.setColumnIdentifiers(new String[]{"Loan ID", "Member ID", "Book Title", "Loan Type", "Date"});
                LinkedList<Loan> allLoans = new LinkedList<>();
                allLoans.addAll(Loan.activeLoans);
                allLoans.addAll(PendingRequestsQueue.request);
                allLoans.sort((loan1, loan2) -> {
                    Date date1 = Loan.activeLoans.contains(loan1) ? loan1.getIssueDate() : loan1.getReturnDate();
                    Date date2 = Loan.activeLoans.contains(loan2) ? loan2.getIssueDate() : loan2.getReturnDate();
                    return date1 != null && date2 != null ? date1.compareTo(date2) : 0;
                });
                for (Loan loan : allLoans) {
                    String loanType = Loan.activeLoans.contains(loan) ? "Active" : "Pending";
                    Book book = catalog.searchBook(loan.getBookId());
                    String bookTitle = (book != null) ? book.getBookTitle() : "Unknown";
                    Date loanDate = Loan.activeLoans.contains(loan) ? loan.getIssueDate() : loan.getReturnDate();
                    tableModel.addRow(new Object[]{
                            loan.getLoanID(),
                            loan.getMemberId(),
                            bookTitle,
                            loanType,
                            loanDate != null ? loanDate : "No Date"
                    });
                }
                break;

            case "Active Loans":
                tableModel.setColumnIdentifiers(new String[]{"Loan ID", "Book Title", "Return Date", "Return Book"});
                for (Loan loan : Loan.activeLoans) {
                    Book book = catalog.searchBook(loan.getBookId());
                    if (book != null) {
                        JButton returnButton = new JButton("Return");
                        returnButton.addActionListener(e -> {
                            Notification.returnLoan(loan);
                            updateGeneralReportTable(reportType, tableModel);
                            Notification.showMessage(this, "Loan Returned: " + loan.getLoanID(), "Notification", JOptionPane.INFORMATION_MESSAGE);
                        });

                        tableModel.addRow(new Object[]{
                                loan.getLoanID(),
                                book.getBookTitle(),
                                loan.getReturnDate() != null ? loan.getReturnDate() : "No Return Date",
                                returnButton
                        });
                    }
                }
                break;

            case "Pending Loans":
                tableModel.setColumnIdentifiers(new String[]{"Loan ID", "Book ID", "Requested On"});
                for (Loan loan : PendingRequestsQueue.request) {
                    tableModel.addRow(new Object[]{
                            loan.getLoanID(),
                            loan.getBookId(),
                            loan.getIssueDate()
                    });
                }
                break;

            case "Overdue Books":
                tableModel.setColumnIdentifiers(new String[]{"Loan ID", "Book Title", "Return Date"});
                Date currentDate = new Date();
                for (Loan loan : Loan.activeLoans) {
                    if (loan.getReturnDate() != null && loan.getReturnDate().before(currentDate)) {
                        Book book = catalog.searchBook(loan.getBookId());
                        if (book != null) {
                            tableModel.addRow(new Object[]{
                                    loan.getLoanID(),
                                    book.getBookTitle(),
                                    loan.getReturnDate()
                            });
                        }
                    }
                }
                break;

            case "Returning Loans":
                tableModel.setColumnIdentifiers(new String[]{"Loan ID", "Book Title", "Return Date", "Return Book"});
                for (Loan loan : Loan.activeLoans) {
                    Book book = catalog.searchBook(loan.getBookId());
                    if (book != null && loan.getReturnDate() == null) {
                        JButton returnButton = new JButton("Return");
                        returnButton.addActionListener(e -> {
                            Notification.returnLoan(loan);
                            updateGeneralReportTable(reportType, tableModel);
                            Notification.showMessage(this, "Loan Returned: " + loan.getLoanID(), "Notification", JOptionPane.INFORMATION_MESSAGE);
                        });

                        tableModel.addRow(new Object[]{
                                loan.getLoanID(),
                                book.getBookTitle(),
                                "No Return Date",
                                returnButton
                        });
                    }
                }
                break;
            case "Popular Genre":
                String popularGenre = Report.displayPopularGenre(catalog);
                tableModel.setColumnIdentifiers(new String[]{"Most Popular Genre"});
                tableModel.addRow(new Object[]{popularGenre});
                break;
        }
        Notification.checkForDueBooks(Loan.activeLoans);
    }

    public JPanel createMemberReportPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Generate Member Report", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 51, 102)); // Navy Blue
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel reportTypeLabel = new JLabel("Select Report Type:");
        reportTypeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(reportTypeLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> reportTypeComboBox = new JComboBox<>(new String[]{"Active Loans", "Overdue Loans", "Pending Loans", "Returning Loans"});
        reportTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(reportTypeComboBox, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel memberIdLabel = new JLabel("Search by Member ID:");
        memberIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(memberIdLabel, gbc);

        gbc.gridx = 1;
        JTextField memberIdField = new JTextField(20);
        memberIdField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(memberIdField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        JButton generateMemberReportButton = new JButton("Generate Report");
        generateMemberReportButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateMemberReportButton.setBackground(new Color(102, 178, 255)); // Light Blue
        generateMemberReportButton.setForeground(Color.WHITE);
        panel.add(generateMemberReportButton, gbc);

        gbc.gridx = 1;
        JLabel statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setForeground(Color.RED);
        panel.add(statusLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        DefaultTableModel memberTableModel = new DefaultTableModel();
        JTable memberReportTable = new JTable(memberTableModel);
        JScrollPane memberTableScroll = new JScrollPane(memberReportTable);
        panel.add(memberTableScroll, gbc);

        generateMemberReportButton.addActionListener(e -> {
            String reportType = (String) reportTypeComboBox.getSelectedItem();
            String memberIdText = memberIdField.getText().trim();
            updateMemberReportTable(reportType, memberTableModel, memberIdText);
            Notification.showMessage(this, "Report Generated for Member ID: " + memberIdText, "Notification", JOptionPane.INFORMATION_MESSAGE);
        });

        return panel;
    }

    private void updateMemberReportTable(String reportType, DefaultTableModel tableModel, String memberId) {
        tableModel.setRowCount(0);

        switch (reportType) {
            case "Active Loans":
                tableModel.setColumnIdentifiers(new String[]{"Loan ID", "Book Title", "Return Date"});
                for (Loan loan : Loan.activeLoans) {
                    if (loan.getMemberId().equals(memberId)) {
                        Book book = catalog.searchBook(loan.getBookId());
                        if (book != null) {
                            tableModel.addRow(new Object[]{
                                    loan.getLoanID(),
                                    book.getBookTitle(),
                                    loan.getReturnDate() != null ? loan.getReturnDate() : "No Return Date"
                            });
                        }
                    }
                }
                break;

            case "Overdue Loans":
                tableModel.setColumnIdentifiers(new String[]{"Loan ID", "Book Title", "Return Date"});
                Date currentDate = new Date();
                for (Loan loan : Loan.activeLoans) {
                    if (loan.getMemberId().equals(memberId) && loan.getReturnDate() != null && loan.getReturnDate().before(currentDate)) {
                        Book book = catalog.searchBook(loan.getBookId());
                        if (book != null) {
                            tableModel.addRow(new Object[]{
                                    loan.getLoanID(),
                                    book.getBookTitle(),
                                    loan.getReturnDate()
                            });
                        }
                    }
                }
                break;

            case "Pending Loans":
                tableModel.setColumnIdentifiers(new String[]{"Loan ID", "Book ID", "Requested On"});
                for (Loan loan : PendingRequestsQueue.request) {
                    if (loan.getMemberId().equals(memberId)) {
                        tableModel.addRow(new Object[]{
                                loan.getLoanID(),
                                loan.getBookId(),
                                loan.getIssueDate()
                        });
                    }
                }
                break;

            case "Returning Loans":
                tableModel.setColumnIdentifiers(new String[]{"Loan ID", "Book Title", "Return Date"});
                for (Loan loan : Loan.activeLoans) {
                    if (loan.getMemberId().equals(memberId) && loan.getReturnDate() == null) {
                        Book book = catalog.searchBook(loan.getBookId());
                        if (book != null) {
                            tableModel.addRow(new Object[]{
                                    loan.getLoanID(),
                                    book.getBookTitle(),
                                    "No Return Date"
                            });
                        }
                    }
                }
                break;
        }
    }
public static void main(String[] args) {
        // Create an instance of Catalog
        Catalog catalog = new Catalog();
        ReportGUI reportGUI = new ReportGUI(catalog);
        reportGUI.setVisible(true);
    }
}
