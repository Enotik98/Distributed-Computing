package RMI;

import models.Employees;
import models.Subdivisions;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Client {
    public static void printSubdivisions(ArrayList<Subdivisions> subdivisions){
        for (Subdivisions sub : subdivisions){
            System.out.println("\t" + sub.getId() + " " + sub.getName());
        }
    }
    public static void printEmployees(ArrayList<Employees> employees){
        for (Employees emp : employees){
            System.out.println("\t\t" + emp.getId() + " " + emp.getName() + " " + emp.getSurname() + " " + emp.getSubdivisionsId());
        }
    }
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException, SQLException {
        Department server = (Department) Naming.lookup("//127.0.0.1/SayHello");
        System.out.println(server.hello());
        System.out.println("Add Subdivision");
        server.addSubdivision("x003", "Boba");
        ArrayList<Subdivisions> subdivisionsArrayList = server.getAllSubdivisions();
        printSubdivisions(subdivisionsArrayList);
        System.out.println("Add Employee");
        server.addEmployee("e03", "Sasha", "Boba", "x003");
        printEmployees(server.getAllEmployees());
        System.out.println("Update date in Employee");
        server.updateEmployeeSurname("Boba", "Baba");
        printEmployees(server.getAllEmployees());
        System.out.println("Transfer Employee in another Subdivision");
        server.transferEmployee("Sasha", "x002");
        printEmployees(server.getEmployeeInSubdivision("x002"));
        System.out.println("Count Employee in subdivision x002: " + server.countEmployeeInSubdivision("x002"));
        System.out.println("Delete Subdivision x003");
        server.deleteSubdivision("x003");
        System.out.println("Delete Employee e03");
        server.deleteEmployee("e03");
        printSubdivisions(server.getAllSubdivisions());
        printEmployees(server.getAllEmployees());
    }
}
