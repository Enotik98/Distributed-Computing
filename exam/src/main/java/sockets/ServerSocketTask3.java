package sockets;


import model.PatientManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerSocketTask3 {
    private ServerSocket serverSocket;
    private final Executor executor;
    private final PatientManager manager;
    public ServerSocketTask3(int port, int size) {
        executor = Executors.newSingleThreadExecutor();
        manager = new PatientManager();
        try {
            serverSocket = new ServerSocket(port, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerSocketTask3 server = new ServerSocketTask3(25565, 5);
        server.run();
    }
    public void run() {
        while (true){
            try {
                var client = serverSocket.accept();
                System.out.println(client + " connected!");
                executor.execute(new ClientRunnable(manager,client));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static class ClientRunnable implements Runnable{
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private final PatientManager manager;
        private final Socket client;
        public ClientRunnable(PatientManager manager, Socket client) {
            this.manager = manager;
            this.client = client;
            try {
                out = new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void getPatients() throws IOException {
            var patients = manager.getPatients();
            out.writeObject(Objects.requireNonNullElseGet(patients, ArrayList::new));
        }
        private void getSortedPatients() throws IOException {
            var patients = manager.getPatientsByDiagnosis();
            out.writeObject(Objects.requireNonNullElseGet(patients, ArrayList::new));
        }
        private void getPatientsByIdAndRange() throws IOException, ClassNotFoundException {
            Integer a = (Integer) in.readObject();
            Integer b = (Integer) in.readObject();
            var patients = manager.getPatientsByMedId(a,b);
            out.writeObject(Objects.requireNonNullElseGet(patients, ArrayList::new));
        }
        @Override
        public void run() {
            while (!Thread.interrupted()){
                try {
                    if(client.isClosed() || !client.isConnected()) break;
                    String code = (String)in.readObject();
                    System.out.println(">" + code);
                    switch (code) {
                        case "get patients" -> getPatients();
                        case "get patients diagnosis" -> getSortedPatients();
                        case "get patients by med id" -> getPatientsByIdAndRange();
                        default -> out.writeObject(new ArrayList<>());
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println(client + " disconnected!");
                    return;
                }
            }
        }
    }
}
