package GUI;
import Classes.*;

import javax.swing.*;
import java.awt.*;

public class BookIdDialog extends JDialog {
    private JTextField bookIdField;
    private JButton confirmButton;

    public BookIdDialog(JFrame parent) {
        super(parent, "Enter Book ID", true);

        setSize(400, 200);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(121, 85, 72));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel idLabel = new JLabel("Enter Book ID:");
        idLabel.setFont(new Font("Caveat", Font.BOLD, 24));
        idLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);

        bookIdField = new JTextField(20);
        bookIdField.setFont(new Font("Caveat", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(bookIdField, gbc);

        confirmButton = new JButton("Confirm");
        confirmButton.setBackground(new Color(141, 110, 99));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setPreferredSize(new Dimension(100, 40));
        confirmButton.addActionListener(e -> {
            String bookId = bookIdField.getText();
            if (bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a book ID.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Book bookToUpdate = Catalog.searchBook(bookId); // Search for the book
                if (bookToUpdate != null) {
                    // If book found, open the update form
                    new UpdateBookDialog(parent, bookToUpdate); // Pass parent frame and book object
                    dispose(); // Close the ID dialog
                } else {
                    JOptionPane.showMessageDialog(this, "Book ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(confirmButton, gbc);

        getContentPane().add(panel);
        setVisible(true);
    }
}