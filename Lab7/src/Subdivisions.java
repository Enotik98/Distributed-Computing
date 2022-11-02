import java.util.ArrayList;
import java.util.List;

public class Subdivisions {
    private String code;
    private String name;
    private List<Employees> employeesList = new ArrayList<>();

    public Subdivisions(){}
    public Subdivisions(Department department, String name) {
//        this.code = code;
        this.name = name;
        department.createId(this);
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

    public List<Employees> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<Employees> employeesList) {
        this.employeesList = employeesList;
    }
    public void printSubdivision(){
        System.out.println("\tid: " + code + "\tname: " + name);
    }
}
