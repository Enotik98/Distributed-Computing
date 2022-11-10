package server;

import models.Employees;
import models.Subdivisions;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client1 {
    private Socket socket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    public Client1(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void addSubdivision(String id, String name) throws IOException {
        out.writeObject("addSubdivision");
        out.writeObject(id);
        out.writeObject(name);

    }
    public void addEmployee(String id, String name, String surname, String subId) throws IOException {
        out.writeObject("addEmployee");
        out.writeObject(id);
        out.writeObject(name);
        out.writeObject(surname);
        out.writeObject(subId);
    }
    public void updateEmployee(String surname, String update) throws IOException {
        out.writeObject("updateEmployee");
        out.writeObject(surname);
        out.writeObject(update);
    }
    public void transferEmployee(String name, String subId) throws IOException {
        out.writeObject("transferEmployee");
        out.writeObject(name);
        out.writeObject(subId);
    }
    public int countEmployeeInSubdivision(String id) throws IOException, ClassNotFoundException {
        out.writeObject("countEmployee");
        out.writeObject(id);
        return (int) in.readObject();
    }
    public ArrayList<Employees> getEmployeeInSubdivision(String id) throws IOException, ClassNotFoundException {
        out.writeObject("getEmployeeInSubdivision");
        out.writeObject(id);
        return (ArrayList<Employees>) in.readObject();
    }
    public void deleteSubdivision(String id) throws IOException {
        out.writeObject("deleteSubdivision");
        out.writeObject(id);
    }
    public void deleteEmployee(String id) throws IOException {
        out.writeObject("deleteEmployee");
        out.writeObject(id);
    }
    public ArrayList<Subdivisions> getAllSubdivisions() throws IOException, ClassNotFoundException {
        out.writeObject("getAllSubdivisions");
        return (ArrayList<Subdivisions>) in.readObject();
    }

    public ArrayList<Employees> getAllEmployees() throws IOException, ClassNotFoundException {
        out.writeObject("getAllEmployees");
        return (ArrayList<Employees>) in.readObject();
    }

    public void disconnect() throws IOException {
        out.writeObject("stop");
//        socket.close();
    }
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
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Client1 client1 = new Client1("localhost", 9898);
        System.out.println("Add Employee");
        client1.addEmployee("e03", "Sasha", "Boba", "x003");
        System.out.println("Add Subdivision");
        client1.addSubdivision("x003", "Biba");
        printSubdivisions(client1.getAllSubdivisions());
        printEmployees(client1.getAllEmployees());
        System.out.println("Update date in Employee");
        client1.updateEmployee("Boba", "Baba");
        printEmployees(client1.getAllEmployees());
        System.out.println("Transfer Employee in another Subdivision");
        client1.transferEmployee("Sasha", "x002");
        printEmployees(client1.getEmployeeInSubdivision("x002"));
        System.out.println("Count Employee in subdivision x002: " + client1.countEmployeeInSubdivision("x002"));
        System.out.println("Delete Subdivision x003");
        client1.deleteSubdivision("x003");
        System.out.println("Delete Employee e03");
        client1.deleteEmployee("e03");
        printSubdivisions(client1.getAllSubdivisions());
        printEmployees(client1.getAllEmployees());
        client1.disconnect();


//        ArrayList<Subdivisions> subdivisions = (ArrayList<Subdivisions>) in.readObject();
//        return subdivisions;
    }
}
