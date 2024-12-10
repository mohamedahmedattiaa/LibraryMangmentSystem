package GUI;
import Classes.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MemberGUI extends JFrame {
    private Catalog catalog;
    private dataBaseMembers member;
    private JTextArea displayArea;

    public MemberGUI() {
        catalog = new Catalog();
        catalog.addBook(new Book("B001", "The Great Gatsby", "Fiction"));
        catalog.addBook(new Book("B002", "1984", "Dystopian"));
        catalog.addBook(new Book("B003", "To Kill a Mockingbird", "Dystopian"));
        setTitle("Library Member Interface");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(5, 1, 10, 10));
        navPanel.setPreferredSize(new Dimension(220, getHeight()));
        navPanel.setBackground(new Color(45, 45, 45));

        addNavButton(navPanel, "View Catalog", e -> displayCatalog());
        addNavButton(navPanel, "Borrow Book", e -> borrowBook());
        addNavButton(navPanel, "Return Book", e -> returnBook());
        addNavButton(navPanel, "View Pending Requests", e -> PendingRequestsQueue.display());
        addNavButton(navPanel, "Logout", e -> logout());

        add(navPanel, BorderLayout.WEST);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        displayArea.setFont(new Font("Arial", Font.PLAIN, 14));
        add(scrollPane, BorderLayout.CENTER);
        RedirectSystemOut.redirectToTextArea(displayArea); //  IDK BUT IT IS WORKIIIIIINGGGGGGGG YAAAAAAYYYYY
        getContentPane().setBackground(new Color(240, 240, 240));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addNavButton(JPanel panel, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        styleNavButton(button);
        panel.add(button);
    }

    private void styleNavButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(68, 68, 68));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 100, 100));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(68, 68, 68));
            }
        });
    }

    private void displayCatalog() {
        displayArea.setText("Library Catalog:\n");
        catalog.displayCatalog();
    }

    private void borrowBook() {
        String bookId = JOptionPane.showInputDialog(this, "Enter Book ID to borrow:");
        String memberId = JOptionPane.showInputDialog(this, "Enter your Member ID:");

        try {
            Loan.borrowBook(memberId, bookId, catalog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void returnBook() {
        String bookId = JOptionPane.showInputDialog(this, "Enter Book ID to return:");
        String memberId = JOptionPane.showInputDialog(this, "Enter your Member ID:");

        try {
            Loan.returnBook(memberId, bookId, catalog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logout() {
        dispose();
        new Login();
    }

    public static void main(String[] args) {
        new MemberGUI();
    }
}
