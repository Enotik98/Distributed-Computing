package RMI;

import models.Employees;
import models.Subdivisions;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Department extends Remote {
    String hello() throws RemoteException, SQLException;

    void addSubdivision(String id, String name) throws RemoteException, SQLException;

    void addEmployee(String id, String name, String surname, String subId) throws RemoteException, SQLException;

    void deleteSubdivision(String id)throws RemoteException, SQLException;
    void deleteEmployee(String id)throws RemoteException, SQLException;
    void updateEmployeeSurname(String surname, String update)throws RemoteException, SQLException;
    void transferEmployee(String name, String subId)throws RemoteException, SQLException;
    void updateSubdivisionName(String name, String update)throws RemoteException, SQLException;
    int countEmployeeInSubdivision(String sudId)throws RemoteException, SQLException;
    ArrayList<Employees> getEmployeeInSubdivision(String subId)throws RemoteException, SQLException;
    ArrayList<Subdivisions> getAllSubdivisions()throws RemoteException;
    ArrayList<Employees> getAllEmployees()throws RemoteException, SQLException;
}
