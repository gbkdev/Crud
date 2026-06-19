package crud;




import java.sql.*;

public class TestDB {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/school",
                "root",
                ""
            );

            System.out.println("CONNECTED SUCCESSFULLY!");

        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
    }
}