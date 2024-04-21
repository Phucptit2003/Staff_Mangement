package view;

import controller.AdminController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class DeleteForm extends JFrame {
    private JTable searchResultTable;
    private JButton searchButton;
    private JButton resetInputButton;
    private JTextField nameTextField;
    private JPanel searchPanel;
    private JButton deleteButton;

    private AdminController adminController;

    public DeleteForm(AdminController adminController) {
        this.adminController = adminController;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Delete Staff Form");
        searchPanel = new JPanel();
        setContentPane(this.searchPanel);
        setSize(530, 600);

        // Khởi tạo searchResultTable và thêm vào JScrollPane
        searchResultTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(searchResultTable);
        searchPanel.add(scrollPane);

        setTable(this.adminController.getStaffList());

        // Khởi tạo các thành phần giao diện
        searchButton = new JButton("Search");
        resetInputButton = new JButton("Reset");
        nameTextField = new JTextField(20);
        deleteButton = new JButton("Delete");

        // Thêm các thành phần giao diện vào panel
        searchPanel.add(new JLabel("Enter ID:"));
        searchPanel.add(nameTextField);
        searchPanel.add(searchButton);
        searchPanel.add(resetInputButton);
        searchPanel.add(deleteButton);

        // Thêm các ActionListener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });

        resetInputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetInputData();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStaff();
            }
        });

        nameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    search();
                }
            }
        });



//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Sửa lại để thoát chương trình khi đóng cửa sổ
        setVisible(true);
    }



    private void resetInputData() {
        nameTextField.setText("");
    }

    private void search() {
        String id = nameTextField.getText().trim();

        String error = this.adminController.isValidSearchInput(id);
        if (error.equals("")) {
            setTable(this.adminController.searchStaff(id));
            resetInputData();
        } else {
            JOptionPane.showMessageDialog(searchPanel, error);
        }
    }
    private void deleteStaff(){
        int[] selectedRows = searchResultTable.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(searchPanel, "Bạn chưa chọn nhân viên nào cả");
        } else {
            String warningMessage = "Bạn có chắc muốn xóa "+ ((selectedRows.length > 1) ? "các " : "") +"nhân viên ";
            for (int i = 0; i < selectedRows.length; i++) {
                int selectedRow = selectedRows[i];
                warningMessage += searchResultTable.getValueAt(selectedRow, 1).toString();
                if(i < selectedRows.length - 1) warningMessage += ", ";
            }
            warningMessage += " chứ?";
            if(JOptionPane.showConfirmDialog(searchPanel, warningMessage, "Warning!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                //do delete
                for (int i = 0; i < selectedRows.length; i++) {
                    int staffId = Integer.valueOf(searchResultTable.getValueAt(selectedRows[i], 0).toString());
                    this.adminController.deleteStaff(staffId);
                    System.out.println("Removed staff has id " + staffId);
                }
                //remove deleted row from table
                for (int i = selectedRows.length -1; i >= 0; i--) {
                    ((DefaultTableModel)searchResultTable.getModel()).removeRow(selectedRows[i]);
                }

                JOptionPane.showMessageDialog(searchPanel, "Xóa thành công");
            }

        }
    }

    private void setTable(ArrayList<ArrayList<String>> data) {
        Object[][] os = new Object[][]{};
        if (data != null && data.get(0) != null) {
            os = new Object[data.size()][data.get(0).size()];
            for (int j = 0; j < data.size(); j++) {
                Object[] rowData = new Object[data.get(j).size()];
                for (int i = 0; i < rowData.length; i++) {
                    rowData[i] = data.get(j).get(i);
                }
                os[j] = rowData;
            }
        }
        searchResultTable.setModel(new DefaultTableModel(
                os,
                new String[]{
                        "ID", "Tên", "Ngày sinh", "Lương", "Giới tính", "Tình trạng hôn nhân"
                }
        ));
    }

    public static void main(String[] args) {
        // Example usage:
        AdminController adminController = new AdminController(); // Khởi tạo controller
        new DeleteForm(adminController); // Hiển thị form chỉnh sửa
    }
}