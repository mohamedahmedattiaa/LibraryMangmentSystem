package GUI;

import Classes.Loan;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.List;

public class Notification {
    public static void checkForDueBooks(List<Loan> loans) {
        Date today = new Date();
        for (Loan loan : loans) {
            long diff = loan.getReturnDate().getTime() - today.getTime();
            if (diff < 86400000 && diff > 0) {
                JOptionPane.showMessageDialog(null, "Reminder: Book " + loan.getBookId() + " is due soon.");
            }
        }
    }
}
