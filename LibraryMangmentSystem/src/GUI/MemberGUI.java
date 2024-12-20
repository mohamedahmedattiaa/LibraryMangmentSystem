package GUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import Classes.*;
import GUI.Login;
import GUI.MemberInterface.MemeberAction;
import static Classes.Loan.*;
//
public class MemberGUI extends JFrame {
    private DefaultTableModel tableModel;
    private JTable bookTable;
    private CardLayout card;
    private JPanel mainPanel;
    private static final Color BROWN_COLOR = new Color(121, 85, 72); // Define your desired brown color
    private static final Color LIGHT_BROWN_COLOR = new Color(141, 110, 99); // Lighter brown for buttons on hover
    private static final Color WHITE_COLOR = Color.WHITE; // White for text
    MemeberAction memberAction = new MemeberAction();

    public MemberGUI() {
        setTitle("Member Panel");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
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
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(LIGHT_BROWN_COLOR); // Using the defined color
        button.setForeground(WHITE_COLOR); // White text color
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setPreferredSize(new Dimension(150, 50));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(141, 110, 99)); // Light brown on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(LIGHT_BROWN_COLOR); // Default light brown
            }
        });

        panel.add(button);
    }


    private JPanel createMembersActionPanel() {
        JPanel membersActionPanel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Borrow", memberAction.createBorrowPanel());
        tabbedPane.addTab("Return", memberAction.createReturnPanel());
        tabbedPane.addTab("Search", memberAction.createSearchPanel());

        membersActionPanel.add(tabbedPane, BorderLayout.CENTER);
        return membersActionPanel;
    }

    private JPanel createManageBookPanel() {
        JPanel ManageBookPanel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Request Book", memberAction.createBorrowPanel());
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

    private void logout() {
        dispose();
        new Login();
    }
    public static void main(String[] args) {
        new MemberGUI();
    }//
}