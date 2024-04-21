package controller;

import lib.MyDateTime;
import lib.MyNumber;
import model.Admin;
import model.Staff;

import java.util.ArrayList;
import java.util.Date;

public class AdminController {
    public Admin admin;

    public AdminController(String username, String password) {
        this.admin = new Admin(username, password);
    }

    public AdminController() {

    }

    public boolean login() {
        return this.admin.login();
    }
    public boolean register(String name,String username,String password) {
        return this.admin.register(name,username,password);
    }
    public boolean changePassword(String username, String newPassword) {
        return this.admin.changePassword(username, newPassword);
    }
    public boolean logOut(){
        return this.admin.logout();
    }

    private String isValidInput(String name, String birthday, String salary, String gender, boolean isMarried) {
        String error = "";
        if (name == null || name.length() < 2) error += "Invalid name\n";
        Date birthdayDate = MyDateTime.convertStringtoDate(birthday, "dd/MM/yyyy");
        if (birthdayDate == null || birthdayDate.getTime() >= new Date().getTime()) error += "Invalid birthday\n";
        if (!MyNumber.isInt(salary)) error += "Invalid salary\n";
        if (convertGender(gender) == null) error += "Invalid gender\n";
        return error.trim();
    }

    private String convertGender(String gender) {
        switch (gender) {
            case "Nam":
                return "male";
            case "Nữ":
                return "female";
            case "Khác":
                return "other";
            default:
                return null;
        }
    }

    private String reconvertGender(String gender) {
        switch (gender) {
            case "male":
                return "Nam";
            case "female":
                return "Nữ";
            case "other":
                return "Khác";
            default:
                return null;
        }
    }

    public String editStaff(int id, String name, String birthday, String salary, String gender, boolean isMarried) {
        //valid input
        String error = isValidInput(name, birthday, salary, gender, isMarried);
        if (error.equals("")) {
            Staff staff = new Staff();
            staff.setId(id);
            staff.setName(name);
            staff.setBirthday(birthday, "dd/MM/yyyy");
            staff.setGender(convertGender(gender));
            staff.setMarried(isMarried);
            staff.setSalary(Integer.valueOf(salary));
            if (!staff.save()) {
                error += "Can't edit staff " + name;
            }
        }
        return error.trim();
    }

    public String addStaff(String name, String birthday, String salary, String gender, boolean isMarried) {
        //valid input
        String error = isValidInput(name, birthday, salary, gender, isMarried);
        if (error.equals("")) {
            if (!this.admin.addStaff(name, MyDateTime.convertStringtoDate(birthday, "dd/MM/yyyy"), Integer.parseInt(salary), convertGender(gender), (isMarried ? "yes" : "no"))) {
                error += "Can't add staff " + name + "\n";
            }
        }
        return error.trim();
    }

    public ArrayList<ArrayList<String>> getStaffList() {
        return convertSqlDatatoTableData(this.admin.getStaffList());
    }

    private ArrayList<ArrayList<String>> convertSqlDatatoTableData(ArrayList<ArrayList<String>> staffList) {
        if (staffList == null || staffList.size() == 0 || staffList.get(0) == null) return null;
        for (int i = 0; i < staffList.size(); i++) {
            ArrayList<String> staff = staffList.get(i);
            staff.set(4, reconvertGender(staff.get(4)));
            staff.set(5, (staff.get(5).equals("yes") ? "Đã cưới" : "Độc thân"));
            staffList.set(i, staff);
        }
        return staffList;
    }

    public String isValidSearchInput(String id) {
        String error = "";
        if (id.length()==0)
            error += "Invalid ID\n";

        return error.trim();
    }

    public ArrayList<ArrayList<String>> searchStaff(String id) {

        return convertSqlDatatoTableData(this.admin.searchStaff(id));
    }

    public void deleteStaff(int staffId) {
        this.admin.deleteStaff(staffId);
    }

    public ArrayList<ArrayList<String>> doStatistics1() {
        return convertSqlDatatoTableData(this.admin.doStatisticsType1());
    }

    public int doStatistics2() {
        try {
            return Integer.valueOf(this.admin.doStatisticsType2().get(0).get(0));
        } catch (Exception ex) {
            return -1;
        }
    }

    public double doStatistics3() {
        try {
            return Double.valueOf(this.admin.doStatisticsType3().get(0).get(0));
        } catch (Exception ex) {
            return -1;
        }
    }

    public boolean logout() {
        try {
            return true; // Trả về true nếu đăng xuất thành công
        } catch (Exception ex) {
            ex.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra trong quá trình đăng xuất
        }
    }

}
