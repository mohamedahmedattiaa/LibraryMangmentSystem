package GUI.MemberInterface;

import Classes.Loan;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class Notification {

    // Queue to hold active loans
    public static Queue<Loan> activeLoans = new LinkedList<>();


    public static void checkForDueBooks(Queue<Loan> loans) {
        if (loans == null || loans.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No loans to check.");
            return;
        }

        Date today = new Date();
        for (Loan loan : loans) {
            if (loan.getReturnDate() == null) {
                JOptionPane.showMessageDialog(null,
                        "Loan ID " + loan.getLoanID() + " has no return date. Please check the loan details.");
                continue;
            }

            long diffInMillis = loan.getReturnDate().getTime() - today.getTime();
            long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis);

            if (diffInMillis > 0 && diffInHours <= 24) {
                // Reminder for books due within 24 hours
                JOptionPane.showMessageDialog(null,
                        "Reminder: Book ID " + loan.getBookId() + " is due soon. Please return it by " + loan.getReturnDate() + ".");
            }
        }
    }

    /**
     * Checks for overdue books and sends an overdue alert.
     */
    public static void checkForOverdueBooks(Queue<Loan> loans) {
        if (loans == null || loans.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No loans to check for overdue status.");
            return;
        }

        Date today = new Date();
        for (Loan loan : loans) {
            if (loan.getReturnDate() != null && today.after(loan.getReturnDate())) {
                // Book is overdue
                JOptionPane.showMessageDialog(null,
                        "Overdue Alert: Book ID " + loan.getBookId() + " is overdue. It was due on " + loan.getReturnDate() + ".");
            }
        }
    }

    /**
     * Returns the loan and updates its status in active loans.
     */
    public static void returnLoan(Loan loan) {
        if (loan == null || !activeLoans.contains(loan)) {
            JOptionPane.showMessageDialog(null, "Invalid loan. Loan not found in the active loans.");
            return;
        }

        // Set the return date to current date and remove from active loans
        loan.setReturnDate(new Date());
        activeLoans.remove(loan);
        JOptionPane.showMessageDialog(null,
                "Loan ID " + loan.getLoanID() + " has been returned successfully.");
    }

    public static void sendNotifications() {
        checkForDueBooks(activeLoans);

        checkForOverdueBooks(activeLoans);
    }

    public static void showMessage(Component parent, String message, String title, int messageType) {
        JOptionPane.showMessageDialog(parent, message, title, messageType);
    }
}
