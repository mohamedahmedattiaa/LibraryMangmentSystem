package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Classes.Loan;
import GUI.Login;
import GUI.MemberInterface.MemeberAction;

public class MemberGUI extends JFrame {
    private CardLayout card;
    private JPanel mainPanel;
    private static final Color BROWN_COLOR = new Color(121, 85, 72); // البني الداكن
    private static final Color LIGHT_BROWN_COLOR = new Color(141, 110, 99); // البني الفاتح
    private static final Color WHITE_COLOR = Color.WHITE; // الأبيض للنصوص
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
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridBagLayout()); // استخدام GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        navPanel.setBackground(new Color(92, 64, 51)); // خلفية بنية

        addNavButton(navPanel, "Members Action", e -> switchPage("Members Action"), gbc, 0);
        addNavButton(navPanel, "Manage Books", e -> switchPage("Manage Books"), gbc, 1);
        addNavButton(navPanel, "Requests", e -> switchPage("Requests"), gbc, 2);
        addNavButton(navPanel, "Logout", e -> logout(), gbc, 3);

        return navPanel;
    }

    private void addNavButton(JPanel panel, String text, ActionListener actionListener, GridBagConstraints gbc, int gridy) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(LIGHT_BROWN_COLOR); // اللون البني الفاتح
        button.setForeground(WHITE_COLOR); // اللون الأبيض للنصوص
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setPreferredSize(new Dimension(150, 50));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(141, 110, 99)); // تغيير اللون عند المرور
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(LIGHT_BROWN_COLOR); // العودة للون الأصلي
            }
        });

        gbc.gridx = 0;
        gbc.gridy = gridy;
        panel.add(button, gbc);
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
        JPanel manageBookPanel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Request Book", memberAction.createBorrowPanel());
        manageBookPanel.add(tabbedPane, BorderLayout.CENTER);
        return manageBookPanel;
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

    private void switchPage(String pageName) {
        card.show(mainPanel, pageName);
    }

    private void logout() {
        dispose();
        new Login();
    }

    public static void main(String[] args) {
        new MemberGUI();
    }
}
