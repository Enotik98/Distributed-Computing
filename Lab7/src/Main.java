import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.*;

public class Main {
    private static final String path = "Categoria.xml";
    public static void workXml() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Department department = Parser.parse(path);
        System.out.println("----------XML--------");

        department.printAll();

        System.out.println("\nAdd a new subdivision Office");
        Subdivisions subdivision = new Subdivisions(department, "Office");
        department.addSubdivision(subdivision);
        department.printAll();

        System.out.println("\nAdd new Employees");
        Employees employee1 = new Employees(department, "Sasha", "Boilok", "954835935", subdivision.getCode());
        department.addEmployee(employee1, subdivision.getCode());
        Employees employee2 = new Employees(department, "Kiril", "Zakhar", "954835935", subdivision.getCode());
        department.addEmployee(employee2, subdivision.getCode());
        department.printAll();

        System.out.println("\nChange employee's surname Zakhar \n");
        department.renameSurnameEmployee(department.getEmployee("Zakhar"), "Boba");
        department.printAll();

        System.out.println("\nChange subdivision's name price\n");
        department.renameSubdivision(department.getSubdivision("price"), "it");
        department.printAll();

        System.out.println("\nGet Office's employees\n");
        List<Employees> employeesList = department.getSubdivisionEmployees("Office");
        for (Employees emp: employeesList){
            emp.printEmployee();
        }

        System.out.println("\nGet all subdivisions");
        Map<String, Subdivisions> subs = department.getSubdivisionsMap();
        for (String id: subs.keySet()){
            subs.get(id).printSubdivision();
        }

        System.out.println("\nDelete a Office's employee");
        department.removeEmployee(employee2);
        employeesList = department.getSubdivisionEmployees("Office");
        for (Employees emp: employeesList){
            emp.printEmployee();
        }

        System.out.println("\nDelete a it");
        Subdivisions subdivision1 = department.getSubdivision("it");
        department.removeSubdivision(subdivision1);
        department.printAll();
        Parser.write(department,path);

    }

    public static void SqlRequest() throws SQLException {
        System.out.println("----------SQL--------");
        DepartmentSQL dep = new DepartmentSQL();
        dep.showSubdivisions();
        dep.addSubdivisions("x003", "it");
        dep.addEmployee("e03", "Alex", "Boba", "x003");
        dep.addEmployee("e04", "Alick", "Biba", "x003");
        dep.showSubdivisions();
        dep.deleteSubdivisions("x003");
        dep.showSubdivisions();
        System.out.println();
    }
    public static void main(String[] args) throws SQLException, ParserConfigurationException, IOException, TransformerException, SAXException{
        SqlRequest();
        workXml();
    }


}
