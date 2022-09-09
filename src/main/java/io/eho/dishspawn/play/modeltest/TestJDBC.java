package io.eho.dishspawn.play;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJDBC {

    public static void main(String[] args) {

        String jdbcUrl = "jdbc:mysql://localhost:3306/dishspawn_db";
        String user = "hbstudent";
        String password = "hbstudent";

        System.out.println("connecting to database");

        try {
            Connection c = DriverManager.getConnection(jdbcUrl, user, password);
            System.out.println("connection successfull!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
