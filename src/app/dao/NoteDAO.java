/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dao;

import app.model.Note;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author admin
 */
public class NoteDAO {

    // Membuat tabel jika belum ada
    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS notes (
                id INT AUTO_INCREMENT PRIMARY KEY,
                title VARCHAR(255) NOT NULL,
                content TEXT NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
        """;

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Tabel notes berhasil dibuat atau sudah ada!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ============================================
    // INSERT
    // ============================================
    public void insert(Note note) {
        String sql = "INSERT INTO notes (title, content) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, note.getTitle());
            ps.setString(2, note.getContent());
            ps.executeUpdate();

            System.out.println("Data berhasil ditambahkan!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ============================================
    // UPDATE
    // ============================================
    public void update(Note note) {
        String sql = "UPDATE notes SET title = ?, content = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, note.getTitle());
            ps.setString(2, note.getContent());
            ps.setInt(3, note.getId());
            ps.executeUpdate();

            System.out.println("Data berhasil diperbarui!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ============================================
    // DELETE
    // ============================================
    public void delete(int id) {
        String sql = "DELETE FROM notes WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Data berhasil dihapus!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ============================================
    // GET ALL NOTES
    // ============================================
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM notes ORDER BY created_at DESC";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Note note = new Note();
                note.setId(rs.getInt("id"));
                note.setTitle(rs.getString("title"));
                note.setContent(rs.getString("content"));
                note.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                notes.add(note);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notes;
    }
}