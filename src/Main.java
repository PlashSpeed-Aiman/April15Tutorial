import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        createTable();
        String name = readUserName();
        String matricNumber = readMatricNumber();
        insertStudent(name, matricNumber);
        getStudents();


    }

    public static void createTable(){
        try{
            Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS persons (id INTEGER PRIMARY KEY, name TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY,name TEXT, matricNumber TEXT)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readUserName(){
        Scanner scanner = new Scanner(System.in);
        out.println("Enter your name: ");
        return scanner.nextLine();
    }

    public static String readMatricNumber(){
        Scanner scanner = new Scanner(System.in);
        out.println("Enter your matric number: ");
        return scanner.nextLine();
    }

    public static void insertStudent(String name, String matricNumber){
        try{
            Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement stmt = conn.createStatement();
            stmt.execute("INSERT INTO students (name, matricNumber) VALUES ('"+name+"', '"+matricNumber+"')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getStudents(){
        try{
            Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            while(rs.next()){
                out.println("Name: "+rs.getString("name")+", Matric Number: "+rs.getString("matricNumber"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Student getStudent(int id){
        try{
            Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students WHERE id = "+id);
            if(rs.next()){
                Student student = new Student();
                student.id = rs.getInt("id");
                student.name = rs.getString("name");
                student.matricNumber = rs.getString("matricNumber");
                return student;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

