package Student;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Run implements DAO {
    List<Student> list = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);
    Connection connection = Connector.getMssql();
    private static final String SQL_SELECT1 = "SELECT * FROM Student where name = ?";
    private static final String SQL_INSERT = "insert into Student values(?,?,?,?,?)";
    private static final String SQL_UPDATE = "update Student set age = ?,name = ?,email = ?,phone = ? where id = ?";
    private static final String SQL_DELETE = "delete from Student where id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM Student";

    public Run() throws SQLException {
    }

    @Override
    public int insert(Student student) throws SQLException {
        if (student.getName() == null) {
            return 0;
        }
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result = 0;
        preparedStatement = connection.prepareStatement(SQL_INSERT);
        preparedStatement.setInt(1, student.getId());
        preparedStatement.setInt(2, student.getAge());
        preparedStatement.setString(3, student.getName());
        preparedStatement.setString(4, student.getEmail());
        preparedStatement.setInt(5, student.getPhone());
        result = preparedStatement.executeUpdate();
        return result;
    }
    @Override
    public int update(Student student) throws SQLException {
        if (student.getName() == null) {
            return 0;
        }
        PreparedStatement preparedStatement = null;
        int result = 0;
        preparedStatement = connection.prepareStatement(SQL_UPDATE);
        preparedStatement.setInt(5, student.getId());
        preparedStatement.setInt(1, student.getAge());
        preparedStatement.setString(2, student.getName());
        preparedStatement.setString(3, student.getEmail());
        preparedStatement.setInt(4, student.getPhone());
        result = preparedStatement.executeUpdate();
        return result;
    }
    @Override
    public int delete(int id) throws SQLException {
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            int result = 0;
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException a) {
            a.printStackTrace();
        }
        return 0;
    }
    @Override
    public Student select(String name) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Student student = null;
        preparedStatement = connection.prepareStatement(SQL_SELECT1);
        preparedStatement.setString(1, name);
        resultSet = preparedStatement.executeQuery();
        if (resultSet == null) {
            System.out.println("Không tìm thấy tên");
        }
        while (resultSet.next()) {
            System.out.println("ID : " + resultSet.getInt("id")
                    + "\nAge : " + resultSet.getInt("age")
                    + "\nName : " + resultSet.getString("name")
                    + "\nEmail : " + resultSet.getString("email")
                    + "\nPhone : " + resultSet.getInt("phone"));
        }
        return student;
    }
    public void findByNameJson(String name) throws IOException {

        FileReader reader = new FileReader("student.json");
        list = new Gson().fromJson(reader, new TypeToken<List<Student>>(){}.getType());

        for (Student product : list) {
            if (product.getName().equals(name)) {
                System.out.println(product);
                break;
            } else {
                System.err.println("Not Found");
            }
        }
        reader.close();
    }
    @Override
    public List<Student> getAll() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Student> students = new ArrayList<>();
        preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            System.out.println("ID: " + resultSet.getInt("id")
                    + " - Age: " + resultSet.getInt("age")
                    + " - Name: " + resultSet.getString("name")
                    + " - Email: " + resultSet.getString("email")
                    + " - Phone: " + resultSet.getInt("phone"));
        }
        return students;
    }
    @Override
    public List<Student> dataToFile() throws SQLException, IOException {
        Gson gson = new Gson();
        FileWriter writer = new FileWriter("student.json");
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Student> students = new ArrayList<>();
        preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id1 = resultSet.getInt("id");
            int age1 = resultSet.getInt("age");
            String name1 = resultSet.getString("name");
            String email1 = resultSet.getString("email");
            int mobile1 = resultSet.getInt("phone");
            Student s = new Student(id1, age1, name1, email1, mobile1);
            students.add(s);
            gson.toJson(students,writer);
        }
        writer.close();
        return students;
    }
    public void dataToXml() throws SQLException, ParserConfigurationException, TransformerException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document dom = builder.newDocument();
        Element root = dom.createElement("list");
        dom.appendChild(root);
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Student> students = new ArrayList<>();
        preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
        resultSet = preparedStatement.executeQuery();
        int id1 = 0;
        int age1 = 0;
        String name1 = null;
        String email1 = null;
        int phone1 = 0;
        while (resultSet.next()) {
            id1 = resultSet.getInt("id");
            age1 = resultSet.getInt("age");
            name1 = resultSet.getString("name");
            email1 = resultSet.getString("email");
            phone1 = resultSet.getInt("phone");
            Student s = new Student(id1, age1, name1, email1, phone1);
            students.add(s);

        }
        for (Student student : students) {

            Element user = dom.createElement("user");
            Attr attr = dom.createAttribute("id");
            attr.setValue(String.valueOf(id1));
            Element age = dom.createElement("age");
            age.setTextContent(String.valueOf(age1));
            Element name = dom.createElement("name");
            name.setTextContent(name1);
            Element email = dom.createElement("email");
            email.setTextContent(email1);
            Element phone = dom.createElement("phone");
            phone.setTextContent(String.valueOf(phone1));

            user.setAttributeNode(attr);
            user.appendChild(age);
            user.appendChild(name);
            user.appendChild(email);
            user.appendChild(phone);
            root.appendChild(user);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(dom), new StreamResult((new File("students.xml"))));
        }

    }
    public void menu() throws SQLException, IOException,ParserConfigurationException, TransformerException {
        Scanner sc = new Scanner(System.in);
        int action = 0;
        do {
            System.out.println("============Menu============");
            System.out.println("1. Insert");
            System.out.println("2. Update");
            System.out.println("3. Delete");
            System.out.println("4. Find By Name");
            System.out.println("5. Show all");
            System.out.println("6. Save to json");
            System.out.println("7. Save to xml");
            System.out.println("8. Exit");
            System.out.print("Your choice: ");
            action = sc.nextInt();
            switch (action){
                case 1:
                    int id = 0;
                    int age;
                    String name;
                    String email;
                    int phone;

                    System.out.print("Enter Id: ");
                    id = input.nextInt();
                    System.out.print("Enter Age: ");
                    age = input.nextInt();
                    System.out.print("Enter name: ");
                    name = input.next();
                    System.out.print("Enter email: ");
                    email = input.next();
                    System.out.print("Enter phone: ");
                    phone = input.nextInt();
                    Student s = new Student(id,age,name,email,phone);

                    insert(s);

                    break;
                case 2:
                    System.out.print("Enter id: ");
                    int idp = sc.nextInt();
                    System.out.println("Enter new Age");
                    int agep = sc.nextInt();
                    System.out.println("Enter new name");
                    String namep = sc.next();
                    System.out.println("Enter new email");
                    String emailp = sc.next();
                    System.out.println("Enter new phone");
                    int phonep = sc.nextInt();
                    Student product1 = new Student(idp,agep,namep,emailp,phonep);

                    update(product1);

                    break;
                case 3:
                    System.out.println("Enter id too delete: ");
                    int delete = sc.nextInt();
                    delete(delete);
                    break;
                case 4:
                    System.out.println("Enter name too show: ");
                    String find = sc.next();
                    findByNameJson(find);
                    break;
                case 5:
                    getAll();
                    break;
                case 6:
                    dataToFile();
                    break;
                case 7:
                    dataToXml();
                    break;
            }
        } while (action != 8);
    }

    public static void main(String[] args) throws SQLException, IOException,ParserConfigurationException, TransformerException {
        Run run = new Run();
        run.menu();
    }



}