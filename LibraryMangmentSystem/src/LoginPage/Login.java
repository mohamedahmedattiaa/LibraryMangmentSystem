package LoginPage;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JTextField userText;
    private JPasswordField password;
    private JButton loginButton;
    private JPanel panel1;
    private JButton SignInButton;

    public Login() {
        JFrame frame = new JFrame("Library Management System - Login");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(735, 956);
        frame.setResizable(false);
        frame.setLayout(null);
        ImageIcon backgroundIcon = new ImageIcon("LoginPage/book.jpg");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 735, 956);
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(200, 300, 100, 30);

        userText = new JTextField(15);
        userText.setBounds(300, 300, 200, 30);
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(200, 350, 100, 30);

        password = new JPasswordField(15);
        password.setBounds(300, 350, 200, 30);
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(165, 42, 42));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.setBounds(300, 400, 200, 30);

        SignInButton = new JButton("SignIn");
        SignInButton.setBackground(new Color(165, 42, 42));
        SignInButton.setForeground(Color.WHITE);
        SignInButton.setFont(new Font("Arial", Font.BOLD, 12));
        SignInButton.setBounds(300, 450, 200, 30);
        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String pass = new String(password.getPassword());
            if (username.equals("admin") && pass.equals("1234")) {
                JOptionPane.showMessageDialog(frame, "Login Successful!");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        frame.add(userLabel);
        frame.add(userText);
        frame.add(passLabel);
        frame.add(password);
        frame.add(loginButton);
        frame.add(SignInButton);
        frame.add(backgroundLabel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    }