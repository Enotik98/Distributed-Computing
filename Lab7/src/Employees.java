import java.util.List;

public class Employees {
    private String code;
    private String name;
    private String surname;
    private String phone;
    private String subdivisionId;


    public Employees(){}

    public Employees(Department department, String name, String surname, String phone, String subId) {
        department.createId(this);
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.subdivisionId = subId;
    }

    public String getSubdivisionId() {
        return subdivisionId;
    }

    public void setSubdivisionId(String subdivisionId) {
        this.subdivisionId = subdivisionId;
    }
    public void printEmployee(){
        System.out.println("\tid: " + code + "\tname: " + name + "\tsurname: " + surname + "\tphone: " + phone);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
