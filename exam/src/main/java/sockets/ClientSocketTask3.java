package sockets;

import model.Patient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientSocketTask3 {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientSocketTask3(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<Patient> getPatients() {
        try {
            out.writeObject("get patients");
            return (List<Patient>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<Patient> getSortedPatients() {
        try {
            out.writeObject("get patients diagnosis");
            return (List<Patient>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<Patient> getPatientsByPatientId(int a, int b) {
        try {
            out.writeObject("get patients by med id");
            out.writeObject(a);
            out.writeObject(b);
            return (List<Patient>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ClientSocketTask3 client = new ClientSocketTask3("localhost", 25565);
        System.out.println("All patients");
        System.out.println(client.getPatients());
        System.out.println("patients COVID-19");
        System.out.println(client.getSortedPatients());
        System.out.println("med id >10 <12");
        System.out.println(client.getPatientsByPatientId(10, 12));
    }
}
