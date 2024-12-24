package GUI.UserAuth;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import Classes.*;
import GUI.AccessControl.MemberGUI;

public class UserSignup extends JFrame {

    public UserSignup() {
        // Set window title and size
        setTitle("User Signup");
        setSize(1600, 900);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1600, 900);
        add(layeredPane);

        // Background Panel with Image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/GUI/photos/test.jpg"));
                Image img = backgroundImage.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setBounds(0, 0, 1600, 900);
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        // Text Labels
        JLabel welcomeLabel = new JLabel("Don't have an account?");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 50));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(25, 500, 700, 50);
        layeredPane.add(welcomeLabel, JLayeredPane.PALETTE_LAYER);

        JLabel infoLabel = new JLabel("Register to access all the features of our service.");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 25));
        infoLabel.setForeground(Color.LIGHT_GRAY);
        infoLabel.setBounds(50, 90, 1000, 100);
        layeredPane.add(infoLabel, JLayeredPane.PALETTE_LAYER);

        JLabel info2Label = new JLabel("Discover a new world of knowledge with every book you read.");
        info2Label.setFont(new Font("Arial", Font.BOLD, 25));
        info2Label.setForeground(Color.LIGHT_GRAY);
        info2Label.setBounds(50, 120, 1020, 100);
        layeredPane.add(info2Label, JLayeredPane.PALETTE_LAYER);

        // Right Panel with form
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

        // Form Components (First Name, Last Name, Email, etc.)
        addLabelFieldPair(rightPanel, "First Name:", 50, 250, 150, 25);
        JTextField firstNameField = new JTextField(20);
        firstNameField.setBounds(50, 280, 150, 25);
        rightPanel.add(firstNameField);

        addLabelFieldPair(rightPanel, "Last Name:", 300, 250, 150, 25);
        JTextField lastNameField = new JTextField(20);
        lastNameField.setBounds(300, 280, 150, 25);
        rightPanel.add(lastNameField);

        addLabelFieldPair(rightPanel, "Email:", 50, 330, 200, 25);
        JTextField emailField = new JTextField(20);
        emailField.setBounds(50, 360, 200, 25);
        rightPanel.add(emailField);

        addLabelFieldPair(rightPanel, "Password:", 50, 410, 200, 25);
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(50, 440, 200, 25);
        rightPanel.add(passwordField);

        addLabelFieldPair(rightPanel, "Confirm Password:", 50, 490, 200, 25);
        JPasswordField conpasswordField = new JPasswordField(20);
        conpasswordField.setBounds(50, 520, 200, 25);
        rightPanel.add(conpasswordField);

        // Sign Up Button
        JButton signUp = new JButton("Sign Up");
        signUp.setBounds(250, 600, 100, 25);
        signUp.setForeground(Color.WHITE);
        signUp.setBackground(new Color(62, 2, 2, 255));
        rightPanel.add(signUp);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setForeground(Color.WHITE);
        loginButton.setBounds(250, 730, 100, 25);
        loginButton.setBackground(new Color(62, 2, 2, 255));
        rightPanel.add(loginButton);

        // Sign-Up Button ActionListener
        signUp.addActionListener(e -> {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String confirmPassword = new String(conpasswordField.getPassword()).trim();

            // Check if any fields are empty
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            // Check if the email format is valid
            else if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(this, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            // Check if passwords match
            else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            // Check if the email is already taken
            else if (Member.isEmailTaken(email)) {
                JOptionPane.showMessageDialog(this, "Email is already taken.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                // Proceed with creating the new member
                String fullName = firstName + " " + lastName;
                try {
                    Member newMember = new Member(fullName, email, password);
                    String memberID = newMember.getmemberId();
                    JOptionPane.showMessageDialog(this, "Sign-Up Successful! Your ID is " + memberID, "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();  // Close the current sign-up window
                    new Login();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving member data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);

            loginButton.addActionListener(e -> {
                dispose();
                new Login();
            });
    }
    private void addLabelFieldPair(JPanel panel, String labelText, int x, int y, int width, int height) {
        JLabel label = new JLabel(labelText);
        label.setBounds(x, y, 100, 25);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(Color.WHITE);
        panel.add(label);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserSignup::new);
    }
}
