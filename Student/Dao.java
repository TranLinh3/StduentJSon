package Student;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface DAO {
    public int insert(Student student) throws SQLException;
    public int update(Student student) throws SQLException;
    public int delete(int id) throws  SQLException;
    public Student select(String name) throws SQLException;
    public List<Student> getAll() throws SQLException;
    public List<Student> dataToFile() throws SQLException, IOException;


}
