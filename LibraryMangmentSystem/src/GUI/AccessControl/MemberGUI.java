package GUI.AccessControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import Classes.Catalog;
import GUI.UserAuth.Login;
import GUI.MemberInterface.Dashboard;
import GUI.MemberInterface.MemeberAction;

public class MemberGUI extends JFrame {
    private static final Color BROWN_COLOR = new Color(121, 85, 72); // Dark Brown
    private static final Color LIGHT_BROWN_COLOR = new Color(141, 110, 99); // Light Brown
    private static final Color WHITE_COLOR = Color.WHITE; // White for text
    private MemeberAction memberAction;
    private Dashboard dashboard;
    private Catalog catalog = new Catalog();
    private String memberId;
    private CardLayout card;
    private JPanel mainPanel;

    public MemberGUI(String memberId) {
        this.memberId = memberId;  // Set the memberId passed from the Login class

        // Initialize memberAction with the memberId
        memberAction = new MemeberAction(memberId);
        dashboard = new Dashboard(catalog, memberId);  // Initialize dashboard

        // Set up JFrame
        setTitle("Member Panel");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, User " + memberId, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.WHITE); // White color for text
        welcomeLabel.setOpaque(true);
        welcomeLabel.setBackground(new Color(92, 64, 51)); // Brown background
        add(welcomeLabel, BorderLayout.NORTH);

        // Initialize card layout and main panel
        card = new CardLayout();
        mainPanel = new JPanel(card);
        mainPanel.add(createMembersActionPanel(), "Members Action");
        mainPanel.add(createDashboardPanel(), "Dashboard");  // Add the dashboard panel here
        add(mainPanel, BorderLayout.CENTER);
        add(createNavPanel(), BorderLayout.WEST);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createNavPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        navPanel.setLayout(new GridLayout(3, 1, 10, 10));
        navPanel.setPreferredSize(new Dimension(200, getHeight()));
        navPanel.setBackground(new Color(92, 64, 51)); // Background color
        addNavButton(navPanel, "Members Action", e -> switchPage("Members Action"), gbc, 0);
        addNavButton(navPanel, "Dashboard", e -> switchPage("Dashboard"), gbc, 1);
        addNavButton(navPanel, "Logout", e -> logout(), gbc, 3);
        return navPanel;
    }

    private void addNavButton(JPanel panel, String text, ActionListener actionListener, GridBagConstraints gbc, int gridy) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(LIGHT_BROWN_COLOR); // Light brown color
        button.setForeground(WHITE_COLOR); // White text color
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setPreferredSize(new Dimension(150, 50));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(141, 110, 99));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(LIGHT_BROWN_COLOR);
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

    private JPanel createDashboardPanel() {
        JPanel dashboardPanel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Dashboard", dashboard.createMemberReportPanel());  // Add the dashboard as a tab
        dashboardPanel.add(tabbedPane, BorderLayout.CENTER);
        return dashboardPanel;
    }

    private void switchPage(String pageName) {
        card.show(mainPanel, pageName);  // Switch between panels using CardLayout
    }

    private void logout() {
        dispose();
        SwingUtilities.invokeLater(Login::new);  // Close current frame and open the Login screen
    }
}
