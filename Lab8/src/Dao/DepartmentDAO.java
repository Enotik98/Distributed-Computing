package Dao;

import models.Employees;
import models.Subdivisions;

import java.sql.*;
import java.util.ArrayList;

public class DepartmentDAO {
    private Connection connection = null;
    private Statement statement = null;

    public DepartmentDAO() throws SQLException {
        final String url = "jdbc:mysql://localhost:3306/Department";
        connection = DriverManager.getConnection(url, "root", "macro854");
        statement = connection.createStatement();
    }

    public void stop() throws SQLException {
        connection.close();
    }

    public boolean addSubdivisions(String id, String name) {
        String sql = "Insert into Subdivisions (id, name) Values ('" + id + "', '" + name + "')";
        try {
            statement.executeUpdate(sql);
            System.out.println("Add subdivisions " + id + ", " + name);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteSubdivision(String id) {
        String sql = "Delete from Subdivisions where id='" + id + "'";
        try {
            int c = statement.executeUpdate(sql);
            if (c > 0) {
                System.out.println("Delete subdivisions " + id);
                String sql1 = "delete from Employees where subdivisionsId='" + id + "'";
                statement.executeUpdate(sql1);
                return true;
            } else {
                System.out.println("Subdivisions not found");
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean addEmployee(String id, String name, String surname, String subId) {
        String sql = "Insert into Employees (id, name, surname, subdivisionsId) Values ('" + id + "', '" + name + "', '" + surname + "', '" + subId + "')";
        try {
            statement.executeUpdate(sql);
            System.out.println("Add Employees " + id + ", " + name + ", " + surname + ", " + subId);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public boolean deleteEmployee(String id) {
        String sql = "Delete from Employees where id='" + id + "'";
        try {
            int c = statement.executeUpdate(sql);
            if (c > 0) {
                System.out.println("Delete employee " + id);
                return true;
            } else {
                System.out.println("Employee not found");
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateSurnameEmployee(String surname, String update) {
        String sql = "Update Employees set surname='" + update + "' where surname='" + surname + "'";
        try {
            int c = statement.executeUpdate(sql);
            if (c > 0) {
                System.out.println("Update Employee surname = " + surname + " -> " + update);
                return true;
            } else {
                System.out.println("Not found Employee " + surname);
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public boolean updateNameSubdivision(String name, String update) {
        String sql = "Update Subdivisions set name='" + update + "' where name='" + name + "'";
        try {
            int c = statement.executeUpdate(sql);
            if (c > 0) {
                System.out.println("Update Employee name = " + name + " -> " + update);
                return true;
            } else {
                System.out.println("Not found Subdivision " + name);
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean transferEmployeeSubdivision(String name, String id) {
        String sql = "Update Employees set subdivisionsId='" + id + "' where name='" + name + "'";
        try {
            int c = statement.executeUpdate(sql);
            if (c > 0) {
                System.out.println("Update Employee name = " + name + " -> " + id);
                return true;
            } else {
                System.out.println("Not found Employee " + name);
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public int countEmployee(String subId) {
        int count  = 0;
        String sql = "select count(*) from Employees where subdivisionsId='" + subId + "'";
        try {
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            count = rs.getInt(1);
            System.out.println("count = " + count);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }

    public ArrayList<Employees> getEmployeesInSubdivisions(String subId) {
        ArrayList<Employees> employees = new ArrayList<>();
        String sql = "Select * from Employees where subdivisionsId='" + subId + "'";
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String surId = rs.getString("subdivisionsId");
                employees.add(new Employees(id, name, surname, surId));
                System.out.println("\t\t" + id + "\t" + name + "\t" + surname + "\t" + surId);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employees;
    }

    public void showAllSubdivisions() {
        String sql = "select * from Subdivisions";
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                System.out.println("\t" + id + "\t" + name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Subdivisions> getAllSubdivisions(){
        ArrayList<Subdivisions> subdivisions = new ArrayList<>();
        String sql = "select * from Subdivisions";
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                subdivisions.add(new Subdivisions(id, name));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return subdivisions;
    }
    public ArrayList<Employees> getAllEmployees(){
        ArrayList<Employees> employees = new ArrayList<>();
        String sql = "select * from Employees";
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String surId = rs.getString("subdivisionsId");
                employees.add(new Employees(id, name, surname, surId));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employees;
    }
//    public static void main(String[] args) throws SQLException {
//        Dao.DepartmentDAO dep = new Dao.DepartmentDAO();
//        ArrayList<Subdivisions> subs = dep.getAllSubdivisions();
//        for (Subdivisions sub : subs){
//            System.out.println("\t" + sub.getId() + " " + sub.getName());
//        }
////        dep.addSubdivisions("x003", "iti");
////        dep.updateNameSubdivision("iti", "it");
////        dep.addEmployee("e03", "Sasha", "Boba", "x003");
////        dep.updateSurnameEmployee("Boba", "Baoba");
////        dep.deleteEmployee("e03");
////        dep.transferEmployeeSubdivision("Alex", "x001");
////        dep.countEmployee("x002");
////        dep.getEmployeesInSubdivisions("x003");
////        dep.showAllSubdivisions();
////        dep.deleteSubdivision("x003");
////        dep.showAllSubdivisions();
//
//    }
}
