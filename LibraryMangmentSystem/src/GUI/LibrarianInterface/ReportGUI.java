package GUI.LibrarianInterface;
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
public class ReportGUI {
    private JFrame frame;
    private JButton generalReportButton;
    private JButton memberReportButton;

    public ReportGUI() {
        frame = new JFrame("Library Reports");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        // General Report Button
        generalReportButton = new JButton("Generate General Report");
        generalReportButton.setBounds(50, 50, 300, 30);
        frame.add(generalReportButton);

        // Member Report Button
        memberReportButton = new JButton("Generate Member Report");
        memberReportButton.setBounds(50, 100, 300, 30);
        frame.add(memberReportButton);

        // Action Listeners
        generalReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Catalog catalog = new Catalog();
                Report.generateGeneralReport(catalog);
                JOptionPane.showMessageDialog(frame, "General Report Generated. Check Console.");
            }
        });

        memberReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String memberID = JOptionPane.showInputDialog(frame, "Enter Member ID:");
                Catalog catalog = new Catalog();
                if (memberID != null && !memberID.trim().isEmpty()) {
                    try {
                        Report.generateReportForMember(memberID,catalog);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(frame, "Member Report Generated for ID: " + memberID + ". Check Console.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Member ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            //public void generateReportForMember(String memberId, Catalog catalog) throws IOException {
            //    System.out.println("\nGenerating report for Member ID: " + memberId);


              //  System.out.println("\nActive Loans for Member ID: " + memberId);
              //  displayActiveLoansForMember(memberId);


             //   System.out.println("\nPending Requests for Member ID: " + memberId);
             //   displayPendingLoansForMember(memberId);


           // }
        });

        frame.setVisible(true);
    }
}

