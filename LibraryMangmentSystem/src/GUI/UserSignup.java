package GUI;
import javax.swing.*;
import java.awt.*;

public class UserSignup extends JFrame {

    public UserSignup() {
        setTitle("User Signup");
        setSize(1600, 900);
        setLayout(null);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1600, 900);
        add(layeredPane);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("test.jpg"));
                Image img = backgroundImage.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setBounds(0, 0, 1600, 900);
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        JLabel welcomeLabel = new JLabel("Don't have an account?");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 50));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(25, 500, 700, 50);
        layeredPane.add(welcomeLabel, JLayeredPane.PALETTE_LAYER);

        JLabel infoLabel = new JLabel("Register to access all the features of our service.");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 25));
        infoLabel.setForeground(Color.LIGHT_GRAY);
        infoLabel.setBounds(50, 90, 1000, 1000);
        layeredPane.add(infoLabel, JLayeredPane.PALETTE_LAYER);

        JLabel info2Label = new JLabel("Discover a new world of knowledge with every book you read.");
        info2Label.setFont(new Font("Arial", Font.BOLD, 25));
        info2Label.setForeground(Color.LIGHT_GRAY);
        info2Label.setBounds(50, 120, 1020, 1000);
        layeredPane.add(info2Label, JLayeredPane.PALETTE_LAYER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(new Color(126, 86, 24, 95));
        rightPanel.setBounds(900, 0, 700, 900);
        layeredPane.add(rightPanel, JLayeredPane.PALETTE_LAYER);

        JLabel signInLabel = new JLabel("Sign Up");
        signInLabel.setFont(new Font("Arial", Font.BOLD, 28));
        signInLabel.setForeground(Color.WHITE);
        signInLabel.setBounds(50, 180, 600, 30);
        rightPanel.add(signInLabel);

        JLabel firstName = new JLabel("First Name:");
        firstName.setBounds(50, 250, 100, 25);
        firstName.setFont(new Font("Arial", Font.BOLD, 12));
        firstName.setForeground(Color.WHITE);
        rightPanel.add(firstName);

        JTextField firstNameField = new JTextField(20);
        firstNameField.setBounds(50, 280, 150, 25);
        rightPanel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        lastNameLabel.setBounds(300, 250, 100, 25);
        lastNameLabel.setForeground(Color.WHITE);
        rightPanel.add(lastNameLabel);

        JTextField lastNameField = new JTextField(20);
        lastNameField.setBounds(300, 280, 150, 25);
        rightPanel.add(lastNameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 12));
        emailLabel.setBounds(50, 330, 100, 25);
        emailLabel.setForeground(Color.WHITE);
        rightPanel.add(emailLabel);

        JTextField emailField = new JTextField(20);
        emailField.setBounds(50, 360, 200, 25);
        rightPanel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 12));
        passwordLabel.setBounds(50, 410, 100, 25);
        passwordLabel.setForeground(Color.WHITE);
        rightPanel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(50, 440, 200, 25);
        rightPanel.add(passwordField);

        JLabel conpasswordLabel = new JLabel("Confirm Password:");
        conpasswordLabel.setFont(new Font("Arial", Font.BOLD, 12));
        conpasswordLabel.setForeground(Color.WHITE);
        conpasswordLabel.setBounds(50, 490, 200, 25);
        rightPanel.add(conpasswordLabel);

        JPasswordField conpasswordField = new JPasswordField(20);
        conpasswordField.setBounds(50, 520, 200, 25);
        rightPanel.add(conpasswordField);

        JButton signUp = new JButton("Sign Up");
        signUp.setBounds(250, 600, 100, 25);
        signUp.setForeground(Color.WHITE);
        signUp.setBackground(new Color(62, 2, 2, 255));
        rightPanel.add(signUp);

        JButton loginButton = new JButton("Login");
        loginButton.setForeground(Color.WHITE);
        loginButton.setBounds(250, 730, 100, 25);
        loginButton.setBackground(new Color(62, 2, 2, 255));
        rightPanel.add(loginButton);

        signUp.addActionListener(e -> {
            String name = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(conpasswordField.getPassword());
            if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() ||  confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if(UserDatabase.isEmailTaken(email)){
                JOptionPane.showMessageDialog(this, "Email is already in taken.", "Error", JOptionPane.ERROR_MESSAGE);
            }else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                UserDatabase.addUser(name, lastName, email, password, "User");
                JOptionPane.showMessageDialog(this, "Sign-Up Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new Login();
            }
        });

        loginButton.addActionListener(e -> {
            dispose();
            new Login();
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserSignup::new);
    }
}