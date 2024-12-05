package LoginPage;
import javax.swing.*;
import java.awt.*;

public class AdminGUI extends JFrame {
    public AdminGUI() {
        JFrame frame = new JFrame("Admin panel");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLayout(null);
        JLabel title = new JLabel("Admin Panel");
        title.setBounds(350,20,200,30);
        title.setFont(new Font("Arial",Font.BOLD,18));
        frame.add(title);
        JButton addLibrarianButton = new JButton("Add Librarian");
        addLibrarianButton.setBounds(300,100,200,30);
        frame.add(addLibrarianButton);
        JButton viewLibrarianButton = new JButton("view Librarians");
        viewLibrarianButton.setBounds(300,150,200,30);
        frame.add(viewLibrarianButton);
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(300,250,200,30);
        logoutButton.addActionListener(e ->{
            frame.dispose();
            new Login();

        });
        frame.add(logoutButton);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}