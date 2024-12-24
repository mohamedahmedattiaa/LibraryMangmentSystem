package GUI.MemberInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import Classes.Loan;
import Classes.Catalog;
import Classes.Book;
import Classes.PendingRequestsQueue;

public class Dashboard extends JPanel {

    private Catalog catalog;  // Catalog reference to pass to the report panel
    private String memberId;  // Member ID to be passed to the report panel

    // Constructor to initialize Dashboard with Catalog and Member ID
    public Dashboard(Catalog catalog, String memberId) {
        this.catalog = catalog;   // Initialize catalog
        this.memberId = memberId; // Initialize memberId

        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel dashboardPanel = createMemberReportPanel();
        tabbedPane.addTab("Dashboard", dashboardPanel);
        add(tabbedPane, BorderLayout.CENTER);
    }

    public JPanel createMemberReportPanel() {
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

        // Member ID Field (now automatically populated)
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel memberIdLabel = new JLabel("Member ID:");
        memberIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(memberIdLabel, gbc);

        gbc.gridx = 1;
        JTextField memberIdField = new JTextField(20);
        memberIdField.setText(memberId);  // Automatically populate member ID
        memberIdField.setEditable(false);  // Make the field non-editable
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
        JButton generateMemberReportButton = new JButton("Generate Report");
        generateMemberReportButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateMemberReportButton.setBackground(new Color(102, 178, 255)); // Light Blue
        generateMemberReportButton.setForeground(Color.WHITE);
        panel.add(generateMemberReportButton, gbc);

        // Status Label
        gbc.gridx = 1;
        JLabel statusLabel = new JLabel("");
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

        DefaultTableModel memberTableModel = new DefaultTableModel();
        JTable memberReportTable = new JTable(memberTableModel);
        JScrollPane memberTableScroll = new JScrollPane(memberReportTable);
        panel.add(memberTableScroll, gbc);

        // Add Action Listener for Button
        generateMemberReportButton.addActionListener(e -> {
            String reportType = (String) reportTypeComboBox.getSelectedItem();

            if (reportType == null) {
                statusLabel.setText("Please select a valid report type.");
                return;
            }
            statusLabel.setText("");
            memberTableModel.setRowCount(0);
            updateMemberReportTable(reportType, memberTableModel);
        });

        return panel;
    }

    public void updateMemberReportTable(String reportType, DefaultTableModel tableModel) {
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

            case "Overdue Books":
                tableModel.setColumnIdentifiers(new String[]{"Loan ID", "Book Title", "Return Date"});
                Date currentDate = new Date();
                for (Loan loan : Loan.activeLoans) {
                    if (loan.getReturnDate() != null && loan.getReturnDate().before(currentDate) && loan.getMemberId().equals(memberId)) {
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
        }
    }

}
