package crud;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CrudGUI {

    static Connection con;

    static JTable table;
    static DefaultTableModel model;

    static JTextField t1, t2, t3, t4, t5, t6, searchField;
    static JComboBox<String> genderBox;

    static TableRowSorter<DefaultTableModel> sorter;

    // LIGHT THEME COLORS
    static Color bg = new Color(245, 246, 250);
    static Color card = new Color(255, 255, 255);
    static Color accent = new Color(0, 120, 215);
    static Color danger = new Color(220, 70, 70);
    static Color success = new Color(46, 170, 110);
    static Color text = new Color(30, 30, 30);

    // ================= STYLE HELPERS =================
    public static void styleButton(JButton b, Color c) {
        b.setBackground(c);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
    }

    public static void styleField(JTextField t) {
        t.setBackground(Color.WHITE);
        t.setForeground(text);
        t.setCaretColor(text);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        t.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
    }

    // ================= CONNECT =================
    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/school",
                    "root",
                    ""
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // ================= LOAD TABLE =================
    public static void loadTable() {
        try {
            model.setRowCount(0);

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM childd");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("Cid"),
                        rs.getString("Fname"),
                        rs.getString("Lname"),
                        rs.getInt("Age"),
                        rs.getString("grade"),
                        rs.getString("s_name"),
                        rs.getString("gender")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public static void clearFields() {
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        t5.setText("");
        t6.setText("");
        genderBox.setSelectedIndex(0);
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        connect();

        JFrame f = new JFrame("Student Management System");
        f.setSize(1200, 760);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout(12, 12));
        main.setBackground(bg);
        main.setBorder(new EmptyBorder(15, 15, 15, 15));

        // ================= TITLE =================
        JLabel title = new JLabel("STUDENT MANAGEMENT SYSTEM", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(text);
        main.add(title, BorderLayout.NORTH);

        // ================= SEARCH =================
        searchField = new JTextField(20);
        styleField(searchField);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(bg);
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);

        // ================= TABLE =================
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "ID", "First Name", "Last Name", "Age", "Grade", "School", "Gender"
        });

        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(220, 235, 255));

        table.getTableHeader().setBackground(new Color(235, 235, 235));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane sp = new JScrollPane(table);

        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.setBackground(card);
        tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(sp, BorderLayout.CENTER);

        // ================= FORM =================
        JPanel form = new JPanel(new GridLayout(7, 2, 10, 10));
        form.setBackground(card);
        form.setBorder(new EmptyBorder(15, 15, 15, 15));

        t1 = new JTextField();
        t2 = new JTextField();
        t3 = new JTextField();
        t4 = new JTextField();
        t5 = new JTextField();
        t6 = new JTextField();

        genderBox = new JComboBox<>(new String[]{"Male", "Female"});

        styleField(t1);
        styleField(t2);
        styleField(t3);
        styleField(t4);
        styleField(t5);
        styleField(t6);

        form.add(new JLabel("ID")); form.add(t1);
        form.add(new JLabel("First Name")); form.add(t2);
        form.add(new JLabel("Last Name")); form.add(t3);
        form.add(new JLabel("Age")); form.add(t4);
        form.add(new JLabel("Grade")); form.add(t5);
        form.add(new JLabel("School")); form.add(t6);
        form.add(new JLabel("Gender")); form.add(genderBox);

        // ================= BUTTONS =================
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttons.setBackground(bg);

        JButton b1 = new JButton("Insert");
        JButton b2 = new JButton("Update");
        JButton b3 = new JButton("Delete");
        JButton b4 = new JButton("Refresh");
        JButton b5 = new JButton("Clear");

        styleButton(b1, success);
        styleButton(b2, accent);
        styleButton(b3, danger);
        styleButton(b4, new Color(120, 120, 120));
        styleButton(b5, new Color(150, 150, 150));

        buttons.add(b1);
        buttons.add(b2);
        buttons.add(b3);
        buttons.add(b4);
        buttons.add(b5);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(bg);
        bottom.add(form, BorderLayout.CENTER);
        bottom.add(buttons, BorderLayout.SOUTH);

        // ================= CENTER LAYOUT =================
        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setBackground(bg);

        center.add(tablePanel, BorderLayout.CENTER);
        center.add(bottom, BorderLayout.SOUTH);

        main.add(center, BorderLayout.CENTER);

        f.setContentPane(main);
        f.setVisible(true);

        loadTable();

        // ================= SEARCH =================
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchField.getText()));
            }
        });

        // ================= INSERT =================
        b1.addActionListener(e -> {
            try {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO childd VALUES (?, ?, ?, ?, ?, ?, ?)"
                );

                ps.setInt(1, Integer.parseInt(t1.getText()));
                ps.setString(2, t2.getText());
                ps.setString(3, t3.getText());
                ps.setInt(4, Integer.parseInt(t4.getText()));
                ps.setString(5, t5.getText());
                ps.setString(6, t6.getText());
                ps.setString(7, genderBox.getSelectedItem().toString());

                ps.executeUpdate();
                loadTable();
                clearFields();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        });

        // ================= UPDATE =================
        b2.addActionListener(e -> {
            try {
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE childd SET Fname=?, Lname=?, Age=?, grade=?, s_name=?, gender=? WHERE Cid=?"
                );

                ps.setString(1, t2.getText());
                ps.setString(2, t3.getText());
                ps.setInt(3, Integer.parseInt(t4.getText()));
                ps.setString(4, t5.getText());
                ps.setString(5, t6.getText());
                ps.setString(6, genderBox.getSelectedItem().toString());
                ps.setInt(7, Integer.parseInt(t1.getText()));

                ps.executeUpdate();
                loadTable();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        });

        // ================= DELETE =================
        b3.addActionListener(e -> {
            try {
                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM childd WHERE Cid=?"
                );

                ps.setInt(1, Integer.parseInt(t1.getText()));
                ps.executeUpdate();

                loadTable();
                clearFields();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        });

        // ================= REFRESH =================
        b4.addActionListener(e -> loadTable());

        // ================= CLEAR =================
        b5.addActionListener(e -> clearFields());

        // ================= TABLE CLICK =================
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                int r = table.getSelectedRow();

                t1.setText(model.getValueAt(r, 0).toString());
                t2.setText(model.getValueAt(r, 1).toString());
                t3.setText(model.getValueAt(r, 2).toString());
                t4.setText(model.getValueAt(r, 3).toString());
                t5.setText(model.getValueAt(r, 4).toString());
                t6.setText(model.getValueAt(r, 5).toString());
                genderBox.setSelectedItem(model.getValueAt(r, 6).toString());
            }
        });
    }
}
