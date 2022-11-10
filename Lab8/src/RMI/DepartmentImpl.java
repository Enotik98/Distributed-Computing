package RMI;

import Dao.DepartmentDAO;
import models.Employees;
import models.Subdivisions;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartmentImpl extends UnicastRemoteObject implements Department {
    private DepartmentDAO departmentDAO;

    DepartmentImpl() throws RemoteException, SQLException {
        departmentDAO = new DepartmentDAO();
    }

    public String hello() throws RemoteException, SQLException {
        return "Hello from server";
    }

    public void addSubdivision(String id, String name) throws RemoteException, SQLException {
        departmentDAO.addSubdivisions(id, name);
    }


    public void addEmployee(String id, String name, String surname, String subId) throws RemoteException, SQLException{
        departmentDAO.addEmployee(id, name, surname, subId);
    }


    public void deleteSubdivision(String id) throws RemoteException, SQLException{
        departmentDAO.deleteSubdivision(id);
    }


    public void deleteEmployee(String id) throws RemoteException, SQLException{
        departmentDAO.deleteEmployee(id);
    }

    public void updateEmployeeSurname(String surname, String update) throws RemoteException, SQLException{
        departmentDAO.updateSurnameEmployee(surname, update);
    }


    public void transferEmployee(String name, String subId) throws RemoteException, SQLException{
        departmentDAO.transferEmployeeSubdivision(name, subId);
    }


    public void updateSubdivisionName(String name, String update) throws RemoteException, SQLException {
        departmentDAO.updateNameSubdivision(name, update);
    }

    public int countEmployeeInSubdivision(String sudId) throws RemoteException, SQLException {
        return departmentDAO.countEmployee(sudId);
    }
    public ArrayList<Employees> getEmployeeInSubdivision(String subId) throws RemoteException, SQLException {
        return departmentDAO.getEmployeesInSubdivisions(subId);
    }

    public ArrayList<Subdivisions> getAllSubdivisions() throws RemoteException {
        return departmentDAO.getAllSubdivisions();
    }
    public ArrayList<Employees> getAllEmployees() throws RemoteException, SQLException {
        return departmentDAO.getAllEmployees();
    }
}
