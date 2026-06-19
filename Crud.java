package crud;

import java.sql.*;
import java.util.Scanner;

public class Crud {

    static Scanner sc = new Scanner(System.in);

    public static Connection connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/school",
            "root",
            "1234"
        );
    }

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Insert");
            System.out.println("2. View");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> insert();
                case 2 -> view();
                case 3 -> update();
                case 4 -> delete();
                case 5 -> System.exit(0);
                default -> System.out.println("Invalid choice!");
            }
        }
    }
    
public static void insert() {
    try (Connection con = connect()) {

        System.out.print("ID: ");
        int id = sc.nextInt();

        System.out.print("First Name: ");
        String fname = sc.next();

        System.out.print("Last Name: ");
        String lname = sc.next();

        System.out.print("Age: ");
        int age = sc.nextInt();

        String sql = "INSERT INTO childd VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);
        ps.setString(2, fname);
        ps.setString(3, lname);
        ps.setInt(4, age);
        ps.setString(5, "A");
        ps.setString(6, "school");
        ps.setString(7, "male");

        ps.executeUpdate();
        System.out.println("Inserted!");

    } catch (Exception e) {
        System.out.println(e);
    }
}

public static void view() {
    try (Connection con = connect()) {

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM childd");

        while (rs.next()) {
            System.out.println(
                rs.getInt("Cid") + " " +
                rs.getString("Fname") + " " +
                rs.getString("Lname") + " " +
                rs.getInt("Age")
            );
        }

    } catch (Exception e) {
        System.out.println(e);
    }
}

public static void update() {
    try (Connection con = connect()) {

        System.out.print("Enter ID to update: ");
        int id = sc.nextInt();

        System.out.print("New Age: ");
        int age = sc.nextInt();

        String sql = "UPDATE childd SET Age=? WHERE Cid=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, age);
        ps.setInt(2, id);

        ps.executeUpdate();
        System.out.println("Updated!");

    } catch (Exception e) {
        System.out.println(e);
    }
}

public static void delete() {
    try (Connection con = connect()) {

        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM childd WHERE Cid=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        ps.executeUpdate();
        System.out.println("Deleted!");

    } catch (Exception e) {
        System.out.println(e);
    }
}