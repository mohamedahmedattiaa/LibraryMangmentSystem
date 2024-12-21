package GUI.LibrarianInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class ReportGUI {
    private JPanel mainPanel;
    private JPanel dashboardPanel;
    private JButton generalReportButton;
    private JButton memberReportButton;
    private JLabel titleLabel;
    private CardLayout cardLayout;
    private JTabbedPane tabbedPane;

    // إنشاء جدول للـ General Report و Member Report
    private JTable generalReportTable;
    private JTable memberReportTable;

    public ReportGUI() {
        mainPanel = new JPanel(new GridBagLayout());
        dashboardPanel = new JPanel(new GridBagLayout());

        generalReportButton = new JButton("Generate General Report");
        memberReportButton = new JButton("Generate Member Report");
        titleLabel = new JLabel("Generate Reports");

        dashboardPanel.setBackground(new Color(92, 64, 51));
        mainPanel.setBackground(new Color(92, 64, 51));

        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(139, 69, 19));
        tabbedPane.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        generalReportButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        generalReportButton.setBackground(new Color(139, 69, 19));
        generalReportButton.setForeground(Color.WHITE);
        generalReportButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(generalReportButton, gbc);

        memberReportButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        memberReportButton.setBackground(new Color(139, 69, 19));
        memberReportButton.setForeground(Color.WHITE);
        memberReportButton.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(memberReportButton, gbc);

        JPanel generalReportPanel = createGeneralReportPanel();
        JPanel memberReportPanel = createMemberReportPanel();

        tabbedPane.addTab("General Report Dashboard", generalReportPanel);
        tabbedPane.addTab("Member Report Dashboard", memberReportPanel);

        addActionListeners();
    }

    // إنشاء الـ General Report Panel مع جدول
    public JPanel createGeneralReportPanel() {
        JPanel generalReportPanel = new JPanel(new BorderLayout());
        generalReportPanel.setBackground(new Color(92, 64, 51));

        JLabel titleLabel = new JLabel("General Report Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // إنشاء الجدول للـ General Report
        String[] columnNames = {"Book ID", "Book Title", "Genre", "Available Copies"};
        generalReportTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(generalReportTable);

        // إضافة الجدول داخل الـ Panel
        generalReportPanel.add(titleLabel, BorderLayout.NORTH);
        generalReportPanel.add(scrollPane, BorderLayout.CENTER);

        return generalReportPanel;
    }

    // إنشاء الـ Member Report Panel مع جدول
    public JPanel createMemberReportPanel() {
        JPanel memberReportPanel = new JPanel(new BorderLayout());
        memberReportPanel.setBackground(new Color(92, 64, 51));

        JLabel titleLabel = new JLabel("Member Report Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // إنشاء الجدول للـ Member Report
        String[] columnNames = {"Loan ID", "Book ID", "Book Title", "Issue Date", "Return Date"};
        memberReportTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(memberReportTable);

        // إضافة الجدول داخل الـ Panel
        memberReportPanel.add(titleLabel, BorderLayout.NORTH);
        memberReportPanel.add(scrollPane, BorderLayout.CENTER);

        return memberReportPanel;
    }

    private void addActionListeners() {
        generalReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainPanel, "Switching to General Report Dashboard.");
                tabbedPane.setSelectedIndex(0);
                loadGeneralReportData(); // تحميل البيانات للـ General Report من الملف
            }
        });

        memberReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String memberID = JOptionPane.showInputDialog(mainPanel, "Enter Member ID:");
                if (memberID != null && !memberID.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Switching to Member Report Dashboard for ID: " + memberID);
                    tabbedPane.setSelectedIndex(1);
                    loadMemberReportData(memberID); // تحميل البيانات للـ Member Report من الملف بناءً على ID العضو
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Member ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // تحميل البيانات من ملف الـ General Report
    private void loadGeneralReportData() {
        // تحديد مسار الملف
        String filePath = "general_report.txt";
        ArrayList<String[]> data = readDataFromFile(filePath);

        // تحديث الجدول بالبيانات
        String[] columnNames = {"Book ID", "Book Title", "Genre", "Available Copies"};
        DefaultTableModel model = new DefaultTableModel(data.toArray(new Object[0][]), columnNames);
        generalReportTable.setModel(model);
    }

    // تحميل البيانات من ملف الـ Member Report بناءً على Member ID
    private void loadMemberReportData(String memberID) {
        // تحديد مسار الملف
        String filePath = "member_report_" + memberID + ".txt"; // افترض أن اسم الملف يحتوي على ID العضو
        ArrayList<String[]> data = readDataFromFile(filePath);

        // تحديث الجدول بالبيانات
        String[] columnNames = {"Loan ID", "Book ID", "Book Title", "Issue Date", "Return Date"};
        DefaultTableModel model = new DefaultTableModel(data.toArray(new Object[0][]), columnNames);
        memberReportTable.setModel(model);
    }

    // دالة لقراءة البيانات من الملف
    private ArrayList<String[]> readDataFromFile(String filePath) {
        ArrayList<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public JPanel createReportPanel() {
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(mainPanel, BorderLayout.NORTH);
        containerPanel.add(tabbedPane, BorderLayout.CENTER);
        return containerPanel;
    }
}
//