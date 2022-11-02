import java.sql.*;

public class DepartmentSQL {

    private Connection connection = null;
    private Statement statement = null;

    public DepartmentSQL() throws SQLException {
        final String url = "jdbc:mysql://localhost:3306/Department";
        connection = DriverManager.getConnection(url, "root", "macro854");
        statement = connection.createStatement();
    }

    public void showSubdivisions() {
        String sql = "select * from Subdivisions";
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                System.out.println("\t" + id + "\t" + name);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql1 = "select *from Employees";
        try {
            ResultSet rs = statement.executeQuery(sql1);
            while (rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String surId = rs.getString("subdivisionsId");
                System.out.println("\t\t" + id + "\t" + name + "\t" + surname + "\t" + surId);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addSubdivisions(String id, String name) {
        String sql = "insert into Subdivisions (id, name) values (" + "'" + id + "', '" + name + "')";

        try {
            statement.executeUpdate(sql);
            System.out.println("add " + id + " " + name);
            return true;
        } catch (SQLException e) {
            System.out.println(">>" + e.getMessage());
            return false;
        }
    }
    public boolean addEmployee(String id, String name, String surname, String subdivisionsId){
        String sql = "insert into Employees (id, name, surname, subdivisionsId) values (" + "'" + id + "', '" + name + "', '" + surname + "', '" + subdivisionsId + "')";

        try {
            statement.executeUpdate(sql);
            System.out.println("add " + id + " " + name + " " + surname + " " + subdivisionsId);
            return true;
        } catch (SQLException e) {
            System.out.println(">>" + e.getMessage());
            return false;
        }
    }
    public boolean deleteSubdivisions(String id){
        String sql = "delete from Subdivisions where id='" + id + "'";
        try {
            int str = statement.executeUpdate(sql);
            if (str > 0 ){
                System.out.println("delete " + id);
                String sql1 = "delete from Employees where subdivisionsId='" + id + "'";
                statement.executeUpdate(sql1);
                return true;
            }else {
                System.out.println("error delete by " + id);
                return false;
            }
        } catch (SQLException e) {
            System.out.println(">> " + e.getMessage());
            return false;
        }
    }

    public void showEmployees() {
        String sql = "SElECT *FROM Employees";
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getNString("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String idSub = rs.getString("subdivisionsId");
                System.out.println("\t\t" + id + "\t" + name + "\t" + surname + "\t" + idSub);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
