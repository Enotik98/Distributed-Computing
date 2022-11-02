import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Parser {
    public static class SimpleErrorHandler implements ErrorHandler {
        // метод для обробки попереджень
        @Override
        public void warning(SAXParseException e) throws SAXException {
            System.out.println("Рядок" + e.getLineNumber() + ":");
            System.out.println(e.getMessage());
        }

        // метод для обробки помилок
        @Override
        public void error(SAXParseException e) throws SAXException {
            System.out.println("Рядок" + e.getLineNumber() + ":");
            System.out.println(e.getMessage());
        }

        // метод для обробки критичних помилок
        @Override
        public void fatalError(SAXParseException e) throws SAXException {
            System.out.println("Рядок" + e.getLineNumber() + ":");
            System.out.println(e.getMessage());
        }
    }

    public static Department parse(String path) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setValidating(true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        documentBuilder.setErrorHandler(new SimpleErrorHandler());
        Document document = documentBuilder.parse(new File(path));
        document.getDocumentElement().normalize();

        Department department = new Department();
        NodeList nodeList = document.getElementsByTagName("Subdivision");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            Subdivisions subdivisions = new Subdivisions();
            subdivisions.setCode (element.getAttribute("id"));
            subdivisions.setName(element.getAttribute("name"));
            department.addSubdivision(subdivisions);
        }

        nodeList = document.getElementsByTagName("Employees");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            Employees employee = new Employees();
            employee.setCode(element.getAttribute("id"));
            employee.setSubdivisionId(element.getAttribute("subdivisionId"));
            employee.setName(element.getAttribute("name"));
            employee.setSurname(element.getAttribute("surname"));
            employee.setPhone(element.getAttribute("phone"));
            department.addEmployee(employee, element.getAttribute("subdivisionId"));


        }
        return department;
    }
    public static void write(Department department, String path) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setValidating(true);
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element root = document.createElement("Department");
        document.appendChild(root);
        Map<String, Subdivisions> subdivisions = department.getSubdivisionsMap();
        for (Map.Entry<String, Subdivisions> entry : subdivisions.entrySet()){
            Element element = document.createElement("Subdivision");
            element.setAttribute("id", entry.getValue().getCode());
            element.setAttribute("name", entry.getValue().getName());
            root.appendChild(element);
            for (Employees employees: entry.getValue().getEmployeesList()){
                Element ele = document.createElement("Employees");
                ele.setAttribute("subdivisionId", employees.getSubdivisionId());
                ele.setAttribute("id", employees.getCode());
                ele.setAttribute("name", employees.getName());
                ele.setAttribute("surname", employees.getSurname());
                ele.setAttribute("phone", employees.getPhone());
                element.appendChild(ele);
            }
        }
        Source domSource = new DOMSource(document);
        Result fileResult = new StreamResult(new File(path));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "categoria.dtd");
        transformer.transform(domSource, fileResult);
    }
}

