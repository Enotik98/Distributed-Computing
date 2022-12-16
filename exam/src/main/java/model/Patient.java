package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Patient implements Serializable, Comparable<Patient> {
    private int id;
    private int medId;
    private String name;
    private String middleName;
    private String surname;
    private String address;
    private String phoneNumber;
    private String diagnosis;

    public Patient(int id, int medId, String name, String middleName,
                   String surname, String address, String phoneNumber,
                   String diagnosis) {
        this.id = id;
        this.medId = medId;
        this.name = name;
        this.middleName = middleName;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.diagnosis = diagnosis;
    }

    public static ArrayList<Patient> getList() {
        var patients = new ArrayList<Patient>();
        patients.add(new Patient(1, 11, "Alex", "Bilok", "Alex",
                "Kiev", "0987654321", "COVID-19"));
        patients.add(new Patient(2, 12, "Kirill", "Zakharchenko", "Olegovich",
                "Lviv", "0987654332", "Autism"));
        patients.add(new Patient(3, 13, "Aboba", "Bon", "Bobr",
                "Kiev", "0987654344", "COVID-19"));
        return patients;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedId() {
        return medId;
    }

    public void setMedId(int medId) {
        this.medId = medId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public int compareTo(Patient o) {
        return Integer.compare(this.id, o.id);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", medId=" + medId +
                ", name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                '}';
    }
}