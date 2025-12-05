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
        try(Connection conn = DatabaseManager.getConnection()) {
            System.out.println("Koneksi!");
        } catch(Exception e) {
            System.out.println("Gagal!");
        }
    }
}