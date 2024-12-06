package GUI;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Login extends JFrame {
    private JTextField idText;
    private JPasswordField password;
    private JButton loginButton;
    private JButton signUpButton;
    private JRadioButton memberRadioButton;
    private JRadioButton librarianRadioButton;

    public Login() {
        // إعداد الإطار
        JFrame frame = new JFrame("Library Management System - Login");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(735, 956);
        frame.setResizable(false);
        frame.setLayout(null);

        // إعداد الخلفية
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/GUI/book.jpg"));
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 735, 956);

        // إعداد النصوص وعناصر الإدخال
        JLabel idLabel = new JLabel("ID:");
        idLabel.setForeground(Color.WHITE);
        idLabel.setBounds(200, 300, 100, 30);

        idText = new JTextField(15);
        idText.setBounds(300, 300, 200, 30);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(200, 350, 100, 30);

        password = new JPasswordField(15);
        password.setBounds(300, 350, 200, 30);

        // إعداد الأزرار
        loginButton = new JButton("Login");
        styleButton(loginButton, 300, 450);

        signUpButton = new JButton("Sign Up");
        styleButton(signUpButton, 300, 500);

        // إعداد أزرار الراديو
        // Configure Classes.Member Radio Button
        memberRadioButton = new JRadioButton("Member");
        memberRadioButton.setBounds(300, 400, 90, 30);
        memberRadioButton.setForeground(Color.WHITE); // Set text color
        memberRadioButton.setOpaque(false); // Make background transparent

// Configure Librarian Radio Button
        librarianRadioButton = new JRadioButton("Librarian");
        librarianRadioButton.setBounds(410, 400, 90, 30);
        librarianRadioButton.setForeground(Color.WHITE); // Set text color
        librarianRadioButton.setOpaque(false); // Make background transparent


        // إضافة أزرار الراديو إلى ButtonGroup
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(memberRadioButton);
        roleGroup.add(librarianRadioButton);

        // إجراء عند الضغط على زر Login
        loginButton.addActionListener(e -> {
            String id = idText.getText();
            String pass = new String(password.getPassword());

            // التحقق من اختيار الراديو
            if (!memberRadioButton.isSelected() && !librarianRadioButton.isSelected()) {
                JOptionPane.showMessageDialog(frame, "Please select a role (Classes.Member or Librarian)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (memberRadioButton.isSelected()) {
                // تحقق الدخول كعضو
                if (id.equals("user") && pass.equals("1234")) {
                    JOptionPane.showMessageDialog(frame, "Welcome Classes.Member!");
                    frame.dispose(); // غلق نافذة تسجيل الدخول
                    new  MemberGUI(); // الانتقال إلى شاشة Classes.Member
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Classes.Member ID or Password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (librarianRadioButton.isSelected()) {
                // تحقق الدخول كأمين مكتبة
                if (id.equals("librarian") && pass.equals("5678")) {
                    JOptionPane.showMessageDialog(frame, "Welcome Librarian!");
                    frame.dispose(); // غلق نافذة تسجيل الدخول
                    new LibrarianGUI(); // الانتقال إلى شاشة Librarian
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Librarian ID or Password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(idLabel);
        frame.add(idText);
        frame.add(passLabel);
        frame.add(password);
        frame.add(loginButton);
        frame.add(signUpButton);
        frame.add(memberRadioButton);
        frame.add(librarianRadioButton);
        frame.add(backgroundLabel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void styleButton(JButton button, int x, int y) {
        button.setBackground(new Color(165, 42, 42));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBounds(x, y, 200, 30);
    }
}