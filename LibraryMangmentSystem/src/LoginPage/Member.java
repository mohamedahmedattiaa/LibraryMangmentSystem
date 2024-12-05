package LoginPage;
import javax.swing.*;
import java.awt.*;
public class Member extends JFrame{
    public Member(){
                JFrame frame = new JFrame("Member Panel");
                frame.setSize(800, 600);
                frame.setLayout(null);

                // العنوان
                JLabel title = new JLabel("Member Panel");
                title.setBounds(350, 20, 200, 30);
                title.setFont(new Font("Arial", Font.BOLD, 18));
                frame.add(title);

                // زر عرض الكتب
                JButton viewBookButton = new JButton("View Books");
                viewBookButton.setBounds(300, 100, 200, 30);
                frame.add(viewBookButton);

                // زر إقراض كتاب
                JButton borrowBookButton = new JButton("Borrow Book");
                borrowBookButton.setBounds(300, 150, 200, 30);
                borrowBookButton.addActionListener(e -> {
                    // مبدئياً: هذه عملية إقراض الكتاب (ستحتاج إلى ربط مع قاعدة بيانات لاحقاً)
                    String bookTitle = JOptionPane.showInputDialog("Enter the title of the book to borrow:");
                    if (bookTitle != null && !bookTitle.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "You have successfully borrowed the book: " + bookTitle);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid book title", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
                frame.add(borrowBookButton);

                // زر إرجاع كتاب
                JButton returnBookButton = new JButton("Return Book");
                returnBookButton.setBounds(300, 200, 200, 30);
                returnBookButton.addActionListener(e -> {
                    // مبدئياً: هذه عملية إرجاع الكتاب (ستحتاج إلى ربط مع قاعدة بيانات لاحقاً)
                    String bookTitle = JOptionPane.showInputDialog("Enter the title of the book to return:");
                    if (bookTitle != null && !bookTitle.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "You have successfully returned the book: " + bookTitle);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid book title", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
                frame.add(returnBookButton);

                // زر البحث عن كتاب
                JButton searchBookButton = new JButton("Search Book");
                searchBookButton.setBounds(300, 250, 200, 30);
                searchBookButton.addActionListener(e -> {
                    // مبدئياً: عملية البحث عن الكتاب
                    String searchQuery = JOptionPane.showInputDialog("Enter the book title or author to search:");
                    if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                        // فقط محاكاة لعملية البحث
                        JOptionPane.showMessageDialog(frame, "Search results for: " + searchQuery);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid search query", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
                frame.add(searchBookButton);

                // زر الخروج
                JButton logoutButton = new JButton("Logout");
                logoutButton.setBounds(300, 350, 200, 30);
                logoutButton.addActionListener(e -> {
                    frame.dispose();
                    new Login(); // العودة إلى صفحة الدخول
                });
                frame.add(logoutButton);

                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        }