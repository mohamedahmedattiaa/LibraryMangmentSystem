package GUI.LibrarianInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import Classes.*;

public class ReportGUI {
    private JPanel mainPanel;
    private JButton generalReportButton;
    private JButton memberReportButton;

    // Constructor
    public ReportGUI() {
        mainPanel = new JPanel(new GridBagLayout());
        generalReportButton = new JButton("Generate General Report");
        memberReportButton = new JButton("Generate Member Report");

        // Create GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Adds padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Makes buttons expand horizontally
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Makes each button take a full row

        // Add General Report Button
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(generalReportButton, gbc);

        // Add Member Report Button
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(memberReportButton, gbc);

        // Action Listeners
        addActionListeners();
    }

    // Method to set up action listeners
    private void addActionListeners() {
        generalReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Catalog catalog = new Catalog();
                Report.generateGeneralReport(catalog);
                JOptionPane.showMessageDialog(mainPanel, "General Report Generated. Check Console.");
            }
        });

        memberReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String memberID = JOptionPane.showInputDialog(mainPanel, "Enter Member ID:");
                Catalog catalog = new Catalog();
                if (memberID != null && !memberID.trim().isEmpty()) {
                    try {
                        Report.generateReportForMember(memberID, catalog);
                        JOptionPane.showMessageDialog(mainPanel, "Member Report Generated for ID: " + memberID + ". Check Console.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(mainPanel, "Error generating report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Member ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Create the report panel to return it for use in the tabbed pane
    public JPanel createReportPanel() {
        return mainPanel; // Return the main panel of the report GUI
    }
}