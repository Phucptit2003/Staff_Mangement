package view;

import controller.AdminController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JPanel loginPanel;
    private JLabel formLogin;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton regretButton;

    private AdminController adminController;

    public LoginForm() {
        // Initialize components
        loginPanel = new JPanel();
        formLogin = new JLabel("Login Form");
        usernameTextField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        JButton regretButton = new JButton("Regret Password");
        JButton changePasswordButton = new JButton("Change Password");

        // Add components to the loginPanel
        loginPanel.add(formLogin);
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameTextField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(regretButton);
        loginPanel.add(changePasswordButton);

        setTitle("Login Form");
        setContentPane(this.loginPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(400, 500);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        regretButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegretWindow();
            }
        });

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openChangePasswordWindow();
            }
        });
    }
    private void login(){
        String username = usernameTextField.getText();
        String password = new String(passwordField.getPassword());

        adminController = new AdminController(username, password);
        if (adminController.login()) {
            JOptionPane.showMessageDialog(loginPanel, "Đăng nhập thành công");
            new StaffManagementForm(this.adminController);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(loginPanel, "Login failed");
        }
    }

    private void openRegretWindow() {
        RegretForm regretForm = new RegretForm();
        regretForm.setVisible(true);
    }

    private void openChangePasswordWindow() {
        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        changePasswordForm.setVisible(true);
    }

    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm();
    }
}

class RegretForm extends JFrame {
    private JPanel registerPanel;
    private JTextField nameTextField;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JTextField phoneTextField;
    private JButton regretButton;

    public RegretForm() {
        // Initialize components
        registerPanel = new JPanel();
        nameTextField = new JTextField(20);
        usernameTextField = new JTextField(20);
        passwordField = new JPasswordField(20);
        phoneTextField = new JTextField(20); // Initialize phone number field
        regretButton = new JButton("Submit");

        registerPanel.add(new JLabel("Phone Number:")); // Add label for phone number
        registerPanel.add(phoneTextField);
        registerPanel.add(regretButton);

        setTitle("Regret Form");
        setContentPane(this.registerPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(300, 280); // Adjusted height to accommodate phone number field

        regretButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //showPasswordChangeForm(); // Show password change form when regret button is clicked
                regret();
            }
        });
    }
    private void regret(){
        String phoneNumber = phoneTextField.getText();

        // Check if phone number is valid
        if (!isValidPhoneNumber(phoneNumber)) {
            JOptionPane.showMessageDialog(this, "Invalid phone number. Please enter a 10-digit numeric phone number.");
            return;
        }

        String otp = "12345678"; // Dummy OTP

        // Get OTP from user
        String inputOTP = JOptionPane.showInputDialog(this, "Enter OTP sent to your phone:");

        // Validate OTP
        if (!isValidOTP(inputOTP)) {
            JOptionPane.showMessageDialog(this, "Invalid OTP. Please try again.");
            return;
        }
        showPasswordChangeForm();

//        String username = JOptionPane.showInputDialog(this, "Enter your username:");
//
//        // Ask user for new password
//        String newPassword = JOptionPane.showInputDialog(this, "Enter your new password:");
//
//        // Ask user to confirm new password
//        String confirmPassword = JOptionPane.showInputDialog(this, "Confirm your new password:");
//
//        // Check if passwords match
//        if (!newPassword.equals(confirmPassword)) {
//            JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.");
//            return;
//        }
//
//        // Update password in database
//        AdminController adminController = new AdminController(username, newPassword);
//        boolean passwordChanged = adminController.changePassword(username, newPassword);
//
//        if (passwordChanged) {
//            JOptionPane.showMessageDialog(this, "Password changed successfully.");
//            // Close the current form and go back to login form
//            dispose();
//            LoginForm loginForm = new LoginForm();
//            loginForm.setVisible(true);
//        } else {
//            JOptionPane.showMessageDialog(this, "Failed to change password. Please try again later.");
//        }
    }
    private void showPasswordChangeForm() {
        // Create a new instance of PasswordChangeForm
        PasswordChangeForm passwordChangeForm = new PasswordChangeForm(this);
        // Make the password change form visible
        passwordChangeForm.setVisible(true);
    }

    public void onPasswordChangeFormClosed(String username, String newPassword) {
        // Handle the information obtained from the password change form
        // Update password in database
        AdminController adminController = new AdminController(username, newPassword);
        boolean passwordChanged = adminController.changePassword(username, newPassword);

        if (passwordChanged) {
            JOptionPane.showMessageDialog(this, "Password changed successfully.");
            // Close the current form and go back to login form
            dispose();
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to change password. Please try again later.");
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Check if the phone number contains exactly 10 digits
        return phoneNumber.matches("\\d{10}");
    }
    private boolean isValidOTP(String otp) {
        // Check if the OTP contains exactly 8 digits
        return otp.matches("\\d{8}");
    }
}
class PasswordChangeForm extends JFrame {
    private JTextField usernameTextField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton submitButton;
    private RegretForm parentForm;

    public PasswordChangeForm(RegretForm parentForm) {
        this.parentForm = parentForm;

        setTitle("Change Password");
        JPanel panel = new JPanel();
        usernameTextField = new JTextField(20);
        newPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        submitButton = new JButton("Submit");

        panel.setLayout(new GridLayout(0, 2, 10, 10)); // Sử dụng GridLayout với 0 hàng và 2 cột

        panel.add(new JLabel("Username:"));
        panel.add(usernameTextField);
        panel.add(new JLabel("New Password:"));
        panel.add(newPasswordField);
        panel.add(new JLabel("Confirm Password:"));
        panel.add(confirmPasswordField);
        panel.add(new JLabel()); // Thêm một label trống để dễ đọc hơn
        panel.add(submitButton);

        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        });
    }

    private void submit() {
        String username = usernameTextField.getText();
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Check if passwords match
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.");
            return;
        }

        // Pass the information back to the parent form
        parentForm.onPasswordChangeFormClosed(username, newPassword);
        // Close this form
        dispose();
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
    }
}
class ChangePasswordForm extends JFrame {
    private JTextField usernameTextField;
    private JPasswordField oldPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton changePasswordButton;
    private JFrame previousForm;
    public ChangePasswordForm() {
        // Initialize components
        JPanel changePasswordPanel = new JPanel();
        usernameTextField = new JTextField(20);
        oldPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        changePasswordButton = new JButton("Change Password");

        // Add components to the changePasswordPanel
        changePasswordPanel.add(new JLabel("Username:"));
        changePasswordPanel.add(usernameTextField);
        changePasswordPanel.add(new JLabel("Old Password:"));
        changePasswordPanel.add(oldPasswordField);
        changePasswordPanel.add(new JLabel("Confirm Old Password:"));
        changePasswordPanel.add(confirmPasswordField);
        changePasswordPanel.add(changePasswordButton);

        setTitle("Change Password Form");
        setContentPane(changePasswordPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(300, 250);

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call changePassword method
                changePassword();
            }
        });
    }

    private void changePassword() {
        String username = usernameTextField.getText();
        String oldPassword = new String(oldPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Check if old passwords match and match with the one in the database
        if (!oldPassword.equals(confirmPassword) || !checkOldPassword(username, oldPassword)) {
            JOptionPane.showMessageDialog(this, "Check your old password.");
            return;
        }

        // Proceed to new password entry
        newPasswordEntry(username);
    }

    private boolean checkOldPassword(String username, String oldPassword) {
        // Logic to check if old password matches with the one in the database
        // Replace this with your own implementation
        return true; // Dummy implementation
    }

    private void newPasswordEntry(String username) {
        JPasswordField newPasswordField = new JPasswordField(20);
        JPasswordField confirmPasswordField = new JPasswordField(20);

        JPanel newPasswordPanel = new JPanel();
        newPasswordPanel.setLayout(new GridLayout(0, 2));
        newPasswordPanel.add(new JLabel("New Password:"));
        newPasswordPanel.add(newPasswordField);
        newPasswordPanel.add(new JLabel("Confirm New Password:"));
        newPasswordPanel.add(confirmPasswordField);

        int result = JOptionPane.showConfirmDialog(this, newPasswordPanel, "Enter New Password",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Check if new passwords match
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.");
            }

            // Update password in the database
            AdminController adminController = new AdminController(username, newPassword);
            if (adminController.changePassword(username, newPassword)) {
                JOptionPane.showMessageDialog(this, "Password changed successfully for username: " + username);
                dispose(); // Close the form
                // Show login form
                LoginForm loginForm= new LoginForm();
                loginForm.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to change password. Please try again later.");
            }
        }
    }
}
