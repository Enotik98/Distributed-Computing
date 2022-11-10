package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            Registry r = LocateRegistry.createRegistry(1099);
            Department server = new DepartmentImpl();
            r.rebind("SayHello", server);
            System.out.println("Server start...");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
