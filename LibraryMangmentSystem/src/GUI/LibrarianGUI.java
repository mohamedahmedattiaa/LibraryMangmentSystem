package GUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Classes.*;
import GUI.LibrarianInterface.ManageBook;
import GUI.LibrarianInterface.ManageMember;
import GUI.LibrarianInterface.ReportGUI;
import GUI.Login;

public class LibrarianGUI extends JFrame {
    private JFrame frame;
    private JButton reportButton;
    private DefaultTableModel tableModel;
    private CardLayout card;
    private JPanel mainPanel;
    private ManageBook manageBook = new ManageBook();
    private ManageMember manage = new ManageMember();
    private Catalog catalog = new Catalog();  // Initialize the catalog with data

    public LibrarianGUI() {
        setTitle("Librarian Panel");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        card = new CardLayout();
        mainPanel = new JPanel(card);

        mainPanel.add(createBookCatalogPanel(), "Book Catalog");
        mainPanel.add(createCombinedManageBookTabbed(), "Manage Books");
        mainPanel.add(createReportsPanel(catalog), "Reports");
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
                new ReportGUI(catalog); // Correctly pass catalog to the ReportGUI
            }
        });
    }

    private JPanel createNavPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(5, 1, 10, 10));
        navPanel.setPreferredSize(new Dimension(200, getHeight()));
        navPanel.setBackground(new Color(92, 64, 51));

        addNavButton(navPanel, "Manage Books", e -> switchPage("Manage Books"));
        addNavButton(navPanel, "Manage Members", e -> switchPage("Manage Members"));
        addNavButton(navPanel, "Book Catalog", e -> switchPage("Book Catalog"));
        addNavButton(navPanel, "Reports", e -> switchPage("Reports"));
        addNavButton(navPanel, "Logout", e -> logout());

        return navPanel;
    }

    private void addNavButton(JPanel panel, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        styleNavButton(button);
        panel.add(button);
    }

    private JPanel createBookCatalogPanel() {
        JPanel bookCatalogPanel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Book Catalog", manageBook.createViewBooksPanel());
        bookCatalogPanel.add(tabbedPane, BorderLayout.CENTER);
        return bookCatalogPanel;
    }

    private JPanel createCombinedManageBookTabbed() {
        JPanel manageBookPanel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Book", manageBook.createAddBook());
        tabbedPane.addTab("Remove Book", manageBook.createRemoveBookPanel());
        tabbedPane.addTab("Update Book", manageBook.createUpdateBook());
        tabbedPane.addTab("Search Book", manageBook.createSearchPanel());
        manageBookPanel.add(tabbedPane, BorderLayout.CENTER);
        return manageBookPanel;
    }

    private JPanel createCombinedManageMemberTabbed() {
        JPanel manageMemberPanel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();;
        tabbedPane.addTab("Remove Member", manage.createRemoveMemberPanel());
        tabbedPane.addTab("Update Member", manage.createUpdateMemberPanel());
        tabbedPane.addTab("Search Member", manage.createSearchMemberPanel());
        tabbedPane.addTab("Display Members", manage.createDisplayMembersPanel());
        manageMemberPanel.add(tabbedPane, BorderLayout.CENTER);
        return manageMemberPanel;
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

    public JPanel createReportsPanel(Catalog catalog) {
        // Create the main panel with a BorderLayout
        JPanel reportsPanel = new JPanel(new BorderLayout());

        // Create the tabbed pane to hold different report tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create an instance of ReportGUI and pass the catalog to it
        ReportGUI reportGUI = new ReportGUI(catalog);

        // Add the "Member Report" tab from the ReportGUI to the tabbed pane
        tabbedPane.addTab("Member Report", reportGUI.createMemberReportPanel(catalog));

        // Add the "General Report" tab from the ReportGUI to the tabbed pane
        tabbedPane.addTab("General Report", reportGUI.createGeneralReportPanel(catalog));

        // Add the tabbedPane to the main reportsPanel
        reportsPanel.add(tabbedPane, BorderLayout.CENTER);

        return reportsPanel;
    }


    private void logout() {
        dispose();
        new Login(); // Assuming Login class exists
    }

    public static void main(String[] args) {
        new LibrarianGUI();
    }
}