package model;

import lib.DB;
import lib.MyDateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Admin {
    private int id;
    private String name;
    private String username;
    private String password;

    private Connection conn;
    private DB db;

    public Admin(String username, String password) {

        this.username = username;
        this.password=password;
        this.db = new DB(); // Initialize the DB object
        this.conn = DB.getJDBCConnection();
    }

    public boolean login() {
        String query = "SELECT id, name FROM admin WHERE username = ? AND password = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, this.username);
            statement.setString(2, this.password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                this.id = resultSet.getInt("id");
                this.name = resultSet.getString("name");
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public boolean register(String name,String username,String password) {
        String query = "INSERT INTO admin (name,username, password) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, username);
            statement.setString(3, password);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public boolean logout() {
        // Thực hiện các thao tác cần thiết để đăng xuất người dùng
        // Ví dụ: Reset các giá trị, xóa thông tin phiên đăng nhập, vv.

        // Trả về true nếu việc đăng xuất thành công
        return true;
    }

    public boolean changePassword(String username, String newPassword) {
        String query = "UPDATE admin SET password = ? WHERE username = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, newPassword);
            statement.setString(2, username);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                this.password = newPassword; // Update password in object if database update was successful
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }



    public boolean addStaff(String name, Date birthday, int salary, String gender, String isMarried) {
        return (db.insert("staff", new String[] { "name", "birthday", "salary", "gender", "is_married" },
                new String[] { name, MyDateTime.convertDatetoString(birthday, "yyyy-MM-dd"), String.valueOf(salary),
                        gender, isMarried }));
    }

    public ArrayList<ArrayList<String>> getStaffList() {
        if (db.select("id, name, FORMAT(birthday, 'dd/MM/yyyy'), salary, gender, is_married", "staff",
                "1=1 ORDER BY id ASC")) {
            System.out.println(db.select("id, name, FORMAT(birthday, 'dd/MM/yyyy'), salary, gender, is_married", "staff",
                    "1=1 ORDER BY id ASC"));
            return db.getResult();
        } else
            return null;
    }

    public ArrayList<ArrayList<String>> doStatisticsType1() {
        if (db.select("id, name, FORMAT(birthday, 'dd/MM/yyyy'), salary, gender, is_married", "staff",
                "1=1 ORDER BY salary DESC")) {
            return db.getResult();
        } else
            return null;
    }

    public ArrayList<ArrayList<String>> doStatisticsType2() {
        if (db.select("sum(salary)", "staff", "1=1")) {
            return db.getResult();
        } else
            return null;
    }

    public ArrayList<ArrayList<String>> doStatisticsType3() {
        if (db.select("SUM(salary) / COUNT(id) AS average_salary", "staff", "1=1 ORDER BY average_salary DESC;")) {
            return db.getResult();
        } else
            return null;
    }

    public ArrayList<ArrayList<String>> searchStaff(String id) {
        String condition = "1=1";
        if (id != null)
            condition += " AND id=" + DB.validSql(id);

        condition = condition.trim();
        if (db.select("id, name, birthday, salary, gender, is_married", "staff",
                condition + " ORDER BY date_created ASC")) {
            return db.getResult();
        } else
            return null;
    }

    public void deleteStaff(int staffId) {
        this.db.execute("DELETE FROM staff WHERE id = " + staffId);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }
}
