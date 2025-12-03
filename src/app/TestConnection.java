/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;
import app.dao.DatabaseManager;
import app.dao.NoteDAO;
import java.sql.Connection;
/**
 *
 * @author admin
 */
public class TestConnection {
    public static void main(String[] args) {

        Connection conn = DatabaseManager.getConnection();

        if (conn != null) {
            System.out.println("Koneksi database BERHASIL!");
        } else {
            System.out.println("Koneksi database GAGAL!");
            return;
        }

        NoteDAO dao = new NoteDAO();
        dao.createTable();
    }
}