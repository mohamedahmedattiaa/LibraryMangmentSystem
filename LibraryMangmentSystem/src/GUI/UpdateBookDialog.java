package GUI;
import Classes.*;

import javax.swing.*;
import java.awt.*;

public class UpdateBookDialog extends JDialog {
    private Book updateBook;
    private JTextField titleField, authorField, genreField;
    private JCheckBox availabilityCheckBox;

    public UpdateBookDialog(Frame parent, Book book) {
        super(parent, "Update Book", true);
        this.updateBook = book;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(book.getBookTitle(), 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);
        gbc.gridx = 1;
        add(titleField, gbc);

        JLabel authorLabel = new JLabel("Author:");
        authorField = new JTextField(book.getAuthor(), 20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(authorLabel, gbc);
        gbc.gridx = 1;
        add(authorField, gbc);

        JLabel genreLabel = new JLabel("Genre:");
        genreField = new JTextField(book.getGenere(), 20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(genreLabel, gbc);
        gbc.gridx = 1;
        add(genreField, gbc);

        JLabel availabilityLabel = new JLabel("Available:");
        availabilityCheckBox = new JCheckBox();
        availabilityCheckBox.setSelected(book.getAvailablityStatus());
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(availabilityLabel, gbc);
        gbc.gridx = 1;
        add(availabilityCheckBox, gbc);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateBookDetails());
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> setVisible(false));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(buttonPanel, gbc);

        setSize(400, 300);
        setLocationRelativeTo(parent);
    }

    private void updateBookDetails() {
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreField.getText();
        boolean availabilityStatus = availabilityCheckBox.isSelected();

        if (title.isEmpty() || author.isEmpty()  ||  genre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        updateBook.setBookTitle(title);
        updateBook.setAuthor(author);
        updateBook.setGenere(genre);
        updateBook.setAvailablityStatus(availabilityStatus);

        JOptionPane.showMessageDialog(this, "Book details updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        setVisible(false);
    }
}