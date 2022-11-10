package server;

import Dao.DepartmentDAO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static ServerSocket serverSocket = null;
//    private Socket socket = null;

    private static DepartmentDAO departmentDAO = null;


    private static final ArrayList<Process> processesList = new ArrayList<>();


    public static void main(String[] args) throws Exception {
        serverSocket = new ServerSocket(9898, 2);
        departmentDAO = new DepartmentDAO();
        for (int i = 1; i <= 1; i++) {
            Socket socket = serverSocket.accept();
            ServerThread serverThread = new ServerThread(socket, departmentDAO);
        }
    }
}
