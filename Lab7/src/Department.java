import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Department {
    private Map<String, Subdivisions> subdivisionsMap = new HashMap<>();
    private Map<String, String> subdivisionNames = new HashMap<>();
    private Map<String, Employees> employeesMap = new HashMap<>();
    private Map<String, String> employeeSurnames = new HashMap<>();

    public Department(){}

    public void createId(Subdivisions subdivisions){
        int id = subdivisionsMap.size();
        String idToString = "x00" + id;
        while (subdivisionsMap.get(idToString) != null){
            id++;
            idToString = "x00" + id;
        }
        subdivisions.setCode(idToString);
    }
    public void createId(Employees employees){
        int id = employeesMap.size();
        String idToString = "e0" + id;
        while (employeesMap.get(idToString) != null){
            id++;
            idToString = "e0" + id;
        }
        employees.setCode(idToString);
    }

    public void addSubdivision(Subdivisions subdivisions){
        subdivisionsMap.put(subdivisions.getCode(), subdivisions);
        subdivisionNames.put(subdivisions.getName(), subdivisions.getCode());
    }

    public void addEmployee(Employees employees, String subId){
        Subdivisions subdivisions = subdivisionsMap.get(subId);
        employees.setSubdivisionId(subId);
        employeesMap.put(employees.getCode(), employees);
        employeeSurnames.put(employees.getSurname(), employees.getCode());
        subdivisions.getEmployeesList().add(employees);
    }
    public void removeSubdivision(Subdivisions subdivisions){
        subdivisionsMap.remove(subdivisions.getCode());
        subdivisionNames.remove(subdivisions.getName());
        for (Employees employees: subdivisions.getEmployeesList()){
            employeesMap.remove(employees.getCode());
        }
    }
    public void removeEmployee(Employees employees){
        employeesMap.remove(employees.getSubdivisionId());
        employeeSurnames.remove(employees.getSurname());
        subdivisionsMap.get(employees.getSubdivisionId()).getEmployeesList().remove(employees);
    }
    public void changeEmployeesSubdivision(Employees employees, Subdivisions subdivisions){
        Subdivisions old = subdivisionsMap.get(employees.getSubdivisionId());
        if (old != null){
            old.getEmployeesList().remove(employees);
        }
        employees.setSubdivisionId(employees.getSubdivisionId());
        subdivisions.getEmployeesList().add(employees);
    }
    public void renameSurnameEmployee(Employees employees, String newSurname){
        employeeSurnames.remove(employees.getSurname());
        employees.setSurname(newSurname);
        employeeSurnames.put(employees.getSurname(), employees.getSubdivisionId());
    }
    public void renameSubdivision(Subdivisions subdivisions, String newSubdivision){
        subdivisionNames.remove(subdivisions.getName());
        subdivisions.setName(newSubdivision);
        subdivisionNames.put(subdivisions.getName(), subdivisions.getCode());
    }
    public Employees getEmployee(String surname){
        String id = employeeSurnames.get(surname);
        if (id != null){
            return employeesMap.get(id);
        }
        return null;
    }
    public Subdivisions getSubdivision(String name){
        String id = subdivisionNames.get(name);
        if (id != null){
            return subdivisionsMap.get(id);
        }
        return null;
    }

    public List<Employees> getSubdivisionEmployees(String name){
        return getSubdivision(name).getEmployeesList();
    }
    public Map<String, Subdivisions> getSubdivisionsMap(){
        return subdivisionsMap;
    }

    public void printAll(){
        for (String id: subdivisionsMap.keySet()){
            Subdivisions subdivisions = subdivisionsMap.get(id);
            subdivisions.printSubdivision();
            List<Employees> employeesList = subdivisions.getEmployeesList();
            for (Employees employees: employeesList){
                employees.printEmployee();
            }
        }
    }
}
