package LoginPage;

import javax.swing.*;

public class Main {
    public static void main(String[]args){
        // create object from login page to see its working or not
        new Login();
        new Member();
        new AdminGUI();
        SwingUtilities.invokeLater(Login::new);
    }
}
