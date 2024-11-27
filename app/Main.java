package app;

import utils.DBConnection;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("database connected successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}