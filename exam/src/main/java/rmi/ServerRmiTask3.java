
package rmi;

import model.Patient;
import model.PatientManager;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ServerRmiTask3 {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.createRegistry(5000);
        RMI service = new Service();
        registry.rebind("hospital", service);
        System.out.println("server started");
    }

    static class Service extends UnicastRemoteObject implements RMI {
        List<Patient> patients;
        PatientManager manager;

        Service() throws Exception {
            super();
            patients = Patient.getList();
            manager = new PatientManager();
        }

        @Override
        public List<Patient> displayInterval(Integer a, Integer b) {
            List<Patient> results = new ArrayList<>();
            for(Patient patient: patients) {
                if(patient.getMedId() > a && patient.getMedId() < b) {
                    results.add(patient);
                }
            }
            return results;
        }

        @Override
        public List<Patient> displayDiagnosis() {
            List<Patient> results = manager.getPatientsByDiagnosis();
            Collections.sort(results);
            return results;
        }
    }
}

interface RMI extends Remote {
    List<Patient> displayInterval(Integer a, Integer b) throws Exception;
    List<Patient> displayDiagnosis() throws Exception;
}