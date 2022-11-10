package server;

import Dao.DepartmentDAO;
import models.Employees;
import models.Subdivisions;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread{
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private DepartmentDAO departmentDAO = null;

    ServerThread(Socket socket, DepartmentDAO departmentDAO) throws IOException {
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        this.departmentDAO = departmentDAO;
        start();
    }

    @Override
    public void run() {
        while (processQuery());
    }

    public boolean processQuery() {
        try {
            String command = (String) in.readObject();
            switch (command) {
                case "addSubdivision": {
                    String id = (String) in.readObject();
                    String name = (String) in.readObject();
                    departmentDAO.addSubdivisions(id, name);
                    break;
                }
                case "addEmployee": {
                    String id = (String) in.readObject();
                    String name = (String) in.readObject();
                    String surname = (String) in.readObject();
                    String subId = (String) in.readObject();
                    departmentDAO.addEmployee(id, name, surname, subId);
                    break;
                }
                case "updateEmployee": {
                    String surname = (String) in.readObject();
                    String update = (String) in.readObject();
                    departmentDAO.updateSurnameEmployee(surname, update);
                    break;
                }
                case "transferEmployee": {
                    String name = (String) in.readObject();
                    String subId = (String) in.readObject();
                    departmentDAO.transferEmployeeSubdivision(name, subId);
                    break;
                }
                case "countEmployee":{
                    String id = (String) in.readObject();
                    int count = departmentDAO.countEmployee(id);
                    out.writeObject(count);
                    break;
                }
                case "getEmployeeInSubdivision" : {
                    String id = (String) in.readObject();
                    ArrayList<Employees> employees = departmentDAO.getEmployeesInSubdivisions(id);
                    out.writeObject(employees);
                    break;
                }
                case "deleteSubdivision" : {
                    String id = (String) in.readObject();
                    departmentDAO.deleteSubdivision(id);
                    break;
                }
                case "deleteEmployee" : {
                    String id = (String) in.readObject();
                    departmentDAO.deleteEmployee(id);
                    break;
                }
                case "getAllSubdivisions" : {
                    ArrayList<Subdivisions> subdivisions = departmentDAO.getAllSubdivisions();
                    out.writeObject(subdivisions);
                    break;
                }
                case "getAllEmployees" : {
                    ArrayList<Employees> employees = departmentDAO.getAllEmployees();
                    out.writeObject(employees);
                    break;
                }
                case "stop": {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
