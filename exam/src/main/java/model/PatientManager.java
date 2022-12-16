package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class PatientManager {
    private final List<Patient> patients;

    public PatientManager() {
        patients = new ArrayList<>();
        patients.add(new Patient(1, 11, "Alex", "Bilok", "Alex",
                "Kiev", "0987654321", "COVID-19"));
        patients.add(new Patient(2, 12, "Kirill", "Zakharchenko", "Olegovich",
                "Lviv", "0987654332", "Autism"));
        patients.add(new Patient(3, 13, "Aboba", "Bon", "Bobr",
                "Kiev", "0987654344", "COVID-19"));
  }


    public synchronized List<Patient> getPatients() {return patients;}

    public synchronized List<Patient> getPatientsByDiagnosis() {
        List<Patient> res = new ArrayList<>();
        patients.forEach(patient -> {
            if (Objects.equals(patient.getDiagnosis(), "COVID-19"))
                res.add(patient);
        });
        return res;
    }

    public synchronized List<Patient> getPatientsByMedId(int a, int b) {
        List<Patient> res = new ArrayList<>();
        patients.forEach(patient -> {
            if (patient.getMedId()> a && patient.getMedId()<b)
                res.add(patient);
        });
        return res;
    }
}
