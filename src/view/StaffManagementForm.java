package view;

import controller.AdminController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffManagementForm extends JFrame {
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton logoutButton;
    private JPanel functionsPanel;
    private JLabel welcomeMessage;

    private AdminController adminController;

    public StaffManagementForm(AdminController adminController) {
        this.adminController = adminController;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Staff Management Form");
        setContentPane(this.functionsPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(400, 500);

        welcomeMessage.setText("Hello " + adminController.admin.getName());
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddStaffForm();
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEditForm();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDeleteForm();
            }
        });

        // Add ActionListener for logoutButton
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
    }

    public void openAddStaffForm() {
        new AddStaffForm(this.adminController);
    }

    public void openDeleteForm() {
        new DeleteForm(this.adminController);
    }

    public void openEditForm() {
        new EditForm(this.adminController);
    }

    // Method to handle logout
    public void logout() {
        // Close the current window
        this.dispose();
        // Open the login form again
        new LoginForm();
    }
}
