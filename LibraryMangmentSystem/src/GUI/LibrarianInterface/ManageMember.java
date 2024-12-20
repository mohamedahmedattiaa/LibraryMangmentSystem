package GUI.LibrarianInterface;
import Classes.Member;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
    public class ManageMember extends JPanel {
        private JPanel cards;
        private CardLayout cardLayout;
        private DefaultTableModel tableModel;
        public ManageMember() {
            cardLayout = new CardLayout();
            cards = new JPanel(cardLayout);

            // Adding individual action panels
            cards.add(createAddMemberPanel(), "Add Member");
            cards.add(createRemoveMemberPanel(), "Remove Member");
            cards.add(createUpdateMemberPanel(), "Update Member");
            cards.add(createDisplayMembersPanel(), "Display Members");

            setLayout(new BorderLayout());
            add(cards, BorderLayout.CENTER);

            // Add a tabbed pane or buttons to switch between actions (Add, Remove, etc.)
            JPanel navigationPanel = createNavigationPanel();
            add(navigationPanel, BorderLayout.NORTH);
        }

        // Method to create the navigation panel (buttons to switch between actions)
        private JPanel createNavigationPanel() {
            JPanel navigationPanel = new JPanel();
            JButton addButton = new JButton("Add Member");
            JButton removeButton = new JButton("Remove Member");
            JButton updateButton = new JButton("Update Member");
            JButton displayButton = new JButton("Display Members");

            addButton.addActionListener(e -> cardLayout.show(cards, "Add Member"));
            removeButton.addActionListener(e -> cardLayout.show(cards, "Remove Member"));
            updateButton.addActionListener(e -> cardLayout.show(cards, "Update Member"));
            displayButton.addActionListener(e -> cardLayout.show(cards, "Display Members"));

            navigationPanel.add(addButton);
            navigationPanel.add(removeButton);
            navigationPanel.add(updateButton);
            navigationPanel.add(displayButton);

            return navigationPanel;
        }

        // Method for Add Member Panel
        public JPanel createAddMemberPanel() {
            JPanel addMemberPanel = new JPanel(new GridBagLayout());
            addMemberPanel.setBackground(new Color(121, 85, 72));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel idLabel = new JLabel("Name:");
            idLabel.setFont(new Font("Caveat", Font.BOLD, 24));
            idLabel.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 0;
            addMemberPanel.add(idLabel, gbc);

            JTextField idField = new JTextField(20);
            idField.setFont(new Font("Caveat", Font.PLAIN, 16));
            gbc.gridx = 1;
            gbc.gridy = 0;
            addMemberPanel.add(idField, gbc);

            JLabel nameLabel = new JLabel("MemberID:");
            nameLabel.setFont(new Font("Caveat", Font.BOLD, 24));
            nameLabel.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 1;
            addMemberPanel.add(nameLabel, gbc);

            JTextField nameField = new JTextField(20);
            nameField.setFont(new Font("Caveat", Font.PLAIN, 16));
            gbc.gridx = 1;
            gbc.gridy = 1;
            addMemberPanel.add(nameField, gbc);

            JLabel successMessageLabel = new JLabel("");
            successMessageLabel.setFont(new Font("Arial", Font.BOLD, 16));
            successMessageLabel.setForeground(Color.GREEN);
            gbc.gridx = 1;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.SOUTH;
            addMemberPanel.add(successMessageLabel, gbc);

            JButton addButton = new JButton("Add");
            addButton.setPreferredSize(new Dimension(100, 40));
            addButton.setFont(new Font("Arial", Font.PLAIN, 16));
            addButton.setBackground(new Color(141, 110, 99));
            addButton.setForeground(Color.WHITE);

            // Add button action
            addButton.addActionListener(e -> {
                String memberId = idField.getText().trim();
                String name = nameField.getText().trim();

                if (memberId.isEmpty() || name.isEmpty()) {
                    JOptionPane.showMessageDialog(addMemberPanel, "Both MemberID and Name are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Fetch member by ID and Name
                Member member = Member.getMemberByIDandName(memberId, name);

                if (member == null) {
                    JOptionPane.showMessageDialog(addMemberPanel, "No member found with the provided ID and Name.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if the member already exists in the table
                boolean exists = false;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String existingId = tableModel.getValueAt(i, 0).toString();
                    String existingName = tableModel.getValueAt(i, 1).toString();
                    if (memberId.equals(existingId) && name.equals(existingName)) {
                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    JOptionPane.showMessageDialog(addMemberPanel, "This member is already added!", "Duplicate Member", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Add the member's data to the table
                tableModel.addRow(new Object[] { member.getmemberId(), member.getName(), member.getEmail(), member.getPassword() });
                successMessageLabel.setText("Member added successfully!");

                // Clear fields after successful addition
                idField.setText("");
                nameField.setText("");
            });

            JButton clearButton = new JButton("Clear");
            clearButton.setPreferredSize(new Dimension(100, 40));
            clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
            clearButton.setBackground(new Color(141, 110, 99));
            clearButton.setForeground(Color.WHITE);

            // Clear button action
            clearButton.addActionListener(e -> {
                idField.setText("");
                nameField.setText("");
                successMessageLabel.setText("");
            });

            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            buttonPanel.setBackground(new Color(121, 85, 72));
            buttonPanel.add(addButton);
            buttonPanel.add(clearButton);
            addMemberPanel.add(buttonPanel, gbc);

            return addMemberPanel;
        }
        public JPanel createRemoveMemberPanel() {
            JPanel removeMemberPanel = new JPanel(new GridBagLayout());
            removeMemberPanel.setBackground(new Color(121, 85, 72));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel memberIdLabel = new JLabel("Member ID:");
            memberIdLabel.setFont(new Font("Caveat", Font.BOLD, 24));
            memberIdLabel.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 0;
            removeMemberPanel.add(memberIdLabel, gbc);

            JTextField memberIdField = new JTextField(20);
            memberIdField.setFont(new Font("Caveat", Font.PLAIN, 16));
            gbc.gridx = 1;
            gbc.gridy = 0;
            removeMemberPanel.add(memberIdField, gbc);

            JLabel successMessageLabel = new JLabel("");
            successMessageLabel.setFont(new Font("Arial", Font.BOLD, 16));
            successMessageLabel.setForeground(Color.GREEN);
            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.SOUTH;
            gbc.insets = new Insets(10, 10, 10, 10);
            removeMemberPanel.add(successMessageLabel, gbc);

            JButton removeButton = new JButton("Remove");
            removeButton.setPreferredSize(new Dimension(100, 40));
            removeButton.setFont(new Font("Arial", Font.PLAIN, 16));
            removeButton.setBackground(new Color(141, 110, 99));
            removeButton.setForeground(Color.WHITE);
            removeButton.addActionListener(e -> {

                String memberId = memberIdField.getText().trim();

                if (memberId.isEmpty()) {
                    JOptionPane.showMessageDialog(removeMemberPanel, "Member ID is required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean removed = Member.removeMemberByID(memberId);
                if (removed) {
                    successMessageLabel.setText("Member removed successfully!");
                } else {
                    JOptionPane.showMessageDialog(removeMemberPanel, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });


            JButton clearButton = new JButton("Clear");
            clearButton.setPreferredSize(new Dimension(100, 40));
            clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
            clearButton.setBackground(new Color(141, 110, 99));
            clearButton.setForeground(Color.WHITE);
            clearButton.addActionListener(e -> {
                memberIdField.setText("");
                successMessageLabel.setText("");
            });

            gbc.gridx = 1;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            buttonPanel.setBackground(new Color(121, 85, 72));
            buttonPanel.add(removeButton);
            buttonPanel.add(clearButton);
            removeMemberPanel.add(buttonPanel, gbc);

            return removeMemberPanel;
        }

        // Method for Update Member Panel
        public JPanel createUpdateMemberPanel() {
            JPanel updateMemberPanel = new JPanel(new GridBagLayout());
            updateMemberPanel.setBackground(new Color(121, 85, 72));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel memberIdLabel = new JLabel("Member ID:");
            memberIdLabel.setFont(new Font("Caveat", Font.BOLD, 24));
            memberIdLabel.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 0;
            updateMemberPanel.add(memberIdLabel, gbc);

            JTextField memberIdField = new JTextField(20);
            memberIdField.setFont(new Font("Caveat", Font.PLAIN, 16));
            gbc.gridx = 1;
            gbc.gridy = 0;
            updateMemberPanel.add(memberIdField, gbc);

            JLabel newNameLabel = new JLabel("New Name:");
            newNameLabel.setFont(new Font("Caveat", Font.BOLD, 24));
            newNameLabel.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 1;
            updateMemberPanel.add(newNameLabel, gbc);

            JTextField newNameField = new JTextField(20);
            newNameField.setFont(new Font("Caveat", Font.PLAIN, 16));
            gbc.gridx = 1;
            gbc.gridy = 1;
            updateMemberPanel.add(newNameField, gbc);

            JLabel newEmailLabel = new JLabel("New Email:");
            newEmailLabel.setFont(new Font("Caveat", Font.BOLD, 24));
            newEmailLabel.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 2;
            updateMemberPanel.add(newEmailLabel, gbc);

            JTextField newEmailField = new JTextField(20);
            newEmailField.setFont(new Font("Caveat", Font.PLAIN, 16));
            gbc.gridx = 1;
            gbc.gridy = 2;
            updateMemberPanel.add(newEmailField, gbc);

            JLabel newPasswordLabel = new JLabel("New Password:");
            newPasswordLabel.setFont(new Font("Caveat", Font.BOLD, 24));
            newPasswordLabel.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 3;
            updateMemberPanel.add(newPasswordLabel, gbc);

            JPasswordField newPasswordField = new JPasswordField(20);
            newPasswordField.setFont(new Font("Caveat", Font.PLAIN, 16));
            gbc.gridx = 1;
            gbc.gridy = 3;
            updateMemberPanel.add(newPasswordField, gbc);
//
// Initialize the success message label here
            JLabel successMessageLabel = new JLabel("");
            successMessageLabel.setFont(new Font("Arial", Font.BOLD, 16));
            successMessageLabel.setForeground(Color.GREEN);
            gbc.gridx = 1;
            gbc.gridy = 5;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.SOUTH;
            gbc.insets = new Insets(10, 10, 10, 10);
            updateMemberPanel.add(successMessageLabel, gbc);

            JButton updateButton = new JButton("Update");
            updateButton.setPreferredSize(new Dimension(100, 40));
            updateButton.setFont(new Font("Arial", Font.PLAIN, 16));
            updateButton.setBackground(new Color(141, 110, 99));
            updateButton.setForeground(Color.WHITE);
            updateButton.addActionListener(e -> {
                String memberId = memberIdField.getText().trim();
                String newName = newNameField.getText().trim();
                String newEmail = newEmailField.getText().trim();
                String newPassword = new String(newPasswordField.getPassword()).trim();

                if (memberId.isEmpty() || newName.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(updateMemberPanel, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean memberFound = false;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String tableMemberId = tableModel.getValueAt(i, 0).toString().trim();
                    if (tableMemberId.equals(memberId)) {
                        tableModel.setValueAt(newName, i, 1);   // Update Name
                        tableModel.setValueAt(newEmail, i, 2);  // Update Email
                        tableModel.setValueAt(newPassword, i, 3); // Update Password
                        memberFound = true;
                        break;
                    }
                }

                if (memberFound) {
                    successMessageLabel.setText("Member updated successfully!");
                    successMessageLabel.setForeground(Color.GREEN);

                    // Clear the fields after successful update
                    memberIdField.setText("");
                    newNameField.setText("");
                    newEmailField.setText("");
                    newPasswordField.setText("");
                } else {
                    successMessageLabel.setText("No member found!");
                    successMessageLabel.setForeground(Color.RED);
                }
            });

            gbc.gridx = 1;
            gbc.gridy = 4;
            updateMemberPanel.add(updateButton, gbc);

            return updateMemberPanel;
        }

        public JPanel createSearchMemberPanel() {
            JPanel searchMemberPanel = new JPanel(new GridBagLayout());
            searchMemberPanel.setBackground(new Color(121, 85, 72)); // Background color

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10); // Spacing between components

            // Label for input field
            JLabel searchLabel = new JLabel("Enter Member Name or ID:");
            searchLabel.setFont(new Font("Caveat", Font.BOLD, 24));
            searchLabel.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.WEST;
            searchMemberPanel.add(searchLabel, gbc);

            // Input text field
            JTextField searchField = new JTextField(20);
            searchField.setFont(new Font("Caveat", Font.PLAIN, 16));
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            searchMemberPanel.add(searchField, gbc);

// Search button
            JButton searchButton = new JButton("Search");
            searchButton.setPreferredSize(new Dimension(100, 40));
            searchButton.setFont(new Font("Arial", Font.PLAIN, 16));
            searchButton.setBackground(new Color(141, 110, 99));
            searchButton.setForeground(Color.WHITE);
            searchButton.addActionListener(e -> {
                String query = searchField.getText().trim();
                if (query.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Search query cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Call the method to search for members by name or ID
                Member result = null; // You can use "ID" or "Name" depending on the search type
                try {
                    result = Member.SearchMember(query);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "An error occurred while searching!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Handle search result
                if (result == null) {
                    JOptionPane.showMessageDialog(this, "No member found!", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    StringBuilder resultText = new StringBuilder();

                    // No need for a loop, we only have one result
                    resultText.append("Member ID: ").append(result.getmemberId())  // Assuming Member class has getMemberId()
                            .append("\nName: ").append(result.getName())  // Assuming Member class has getName()
                            .append("\nEmail: ").append(result.getEmail())  // Assuming Member class has getEmail()
                            .append("\nPassword: ").append(result.getPassword())  // Assuming Member class has getPassword()
                            .append("\n");

                    // Display the result in a message dialog
                    JOptionPane.showMessageDialog(this, resultText.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
                }
            });




            // Clear button
            JButton clearButton = new JButton("Clear");
            clearButton.setPreferredSize(new Dimension(100, 40));
            clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
            clearButton.setBackground(new Color(141, 110, 99));
            clearButton.setForeground(Color.WHITE);
            clearButton.addActionListener(e -> {
                searchField.setText(""); // Clear the search field
                // Clear the result text area
            });

            // Button panel
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            buttonPanel.setBackground(new Color(121, 85, 72));
            buttonPanel.add(searchButton);
            buttonPanel.add(clearButton);
            searchMemberPanel.add(buttonPanel, gbc);

            return searchMemberPanel;
        }

        // Method for Display Members Panel
        public JPanel createDisplayMembersPanel() {
            JPanel displayMembersPanel = new JPanel();
            displayMembersPanel.setLayout(new BorderLayout());  // Use BorderLayout for proper table display

            // Create column names for the table
            String[] columnNames = {"Member ID", "Name", "Email", "Password"};

            // Get the member data from the file
            Object[][] data = readMemberData();

            // Create the table using DefaultTableModel
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable memberTable = new JTable(model);

            // Add table to JScrollPane for scroll functionality
            JScrollPane scrollPane = new JScrollPane(memberTable);
            displayMembersPanel.add(scrollPane, BorderLayout.CENTER);

            return displayMembersPanel;
        }

        // This method reads member data from the file and returns it as an Object[][] array
        private Object[][] readMemberData() {
            String line;
            Object[][] data = new Object[100][4];  // Adjust size if needed
            int rowIndex = 0;

            try (BufferedReader reader = new BufferedReader(new FileReader("Members.txt"))) {
                while ((line = reader.readLine()) != null) {
                    String[] memberData = line.split(",");
                    if (memberData.length >= 4) {
                        String memberID = memberData[0].trim();
                        String name = memberData[1].trim();
                        String email = memberData[2].trim();
                        String password = memberData[3].trim();

                        // Add data to the table
                        data[rowIndex][0] = memberID;
                        data[rowIndex][1] = name;
                        data[rowIndex][2] = email;
                        data[rowIndex][3] = password;
                        rowIndex++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Resize array to fit actual number of rows
            Object[][] resizedData = new Object[rowIndex][4];
            System.arraycopy(data, 0, resizedData, 0, rowIndex);
            return resizedData;
        }
    }
