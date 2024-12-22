package GUI;

import Classes.Member;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JTextField idText;
    private JPasswordField password;
    private JButton loginButton;
    private JButton signUpButton;

    public Login() {
        setTitle("Library Management System - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(735, 956);
        setResizable(false);
        setLayout(null);
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/GUI/book.jpg"));
        JLabel backgroundLabel = new JLabel(backgroundIcon);

        backgroundLabel.setBounds(0, 0, 735, 956);
        JLabel idLabel = new JLabel("Email/ID:");
        idLabel.setForeground(Color.WHITE);
        idLabel.setBounds(200, 300, 100, 30);
        idText = new JTextField(15);
        idText.setBounds(300, 300, 200, 30);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(200, 350, 100, 30);
        password = new JPasswordField(15);
        password.setBounds(300, 350, 200, 30);

        loginButton = new JButton("Login");
        styleButton(loginButton, 300, 450);

        signUpButton = new JButton("Sign Up");
        styleButton(signUpButton, 300, 500);

        loginButton.addActionListener(e -> {
            String email = idText.getText();  // Get email (used as ID)
            String pass = new String(password.getPassword());
            String memberId = Member.validateUser(email, pass);  // Get the memberId after validation
            if (memberId != null) {
                JOptionPane.showMessageDialog(this, "Welcome Member!");
                dispose();
                new MemberGUI(memberId);
            } else if (email.equals("ADMIN3060") && pass.equals("U@Admin36798")) {
                JOptionPane.showMessageDialog(this, "Welcome Admin!");
                dispose();
                new LibrarianGUI();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid ID or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        signUpButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(UserSignup::new);
        });
        add(idLabel);
        add(idText);
        add(passLabel);
        add(password);
        add(loginButton);
        add(signUpButton);
        add(backgroundLabel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton button, int x, int y) {
        button.setBackground(new Color(165, 42, 42));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBounds(x, y, 200, 30);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}