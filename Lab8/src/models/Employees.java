package models;

import java.io.Serializable;

public class Employees implements Serializable {
    private String id;
    private String name;
    private String surname;
    private String subdivisionsId;

    public Employees(String id, String name, String surname, String subdivisionsId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.subdivisionsId = subdivisionsId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSubdivisionsId() {
        return subdivisionsId;
    }

    public void setSubdivisionsId(String subdivisionsId) {
        this.subdivisionsId = subdivisionsId;
    }
}
