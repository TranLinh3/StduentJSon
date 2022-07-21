package Student;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


public class Student {
    private int id;
    private int age;
    private String name;
    private String email;
    private int phone;

    public Student() {
    }

    public Student(int id, int age, String name, String email, int phone) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Student" +
                " | id = " + id +
                " | age = " + age +
                " | name = " + name +
                " | email = " + email +
                " | phone = " + phone
                ;
    }
}
