/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dao;

import app.model.Category;
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

public List<Note> getAll() {
    List<Note> notes = new ArrayList<>();

    String sql = "SELECT n.*, c.id AS cid, c.name AS cname " +
                 "FROM notes n LEFT JOIN categories c " +
                 "ON n.category_id = c.id ORDER BY n.id ASC";

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Note note = new Note();
            note.setId(rs.getInt("id"));
            note.setTitle(rs.getString("title"));
            note.setContent(rs.getString("content"));
            note.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            note.setCategoryId(rs.getInt("category_id"));

            // category
            Category category = new Category();
            category.setId(rs.getInt("cid"));
            category.setName(rs.getString("cname"));
            note.setCategory(category);

            notes.add(note);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return notes;
}
public Note getById(int id) {
        Note note = null;
        String sql = "SELECT * FROM notes WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                note = new Note();
                note.setId(rs.getInt("id"));
                note.setTitle(rs.getString("title"));
                note.setContent(rs.getString("content"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) {
                    note.setCreatedAt(ts.toLocalDateTime());
                }
                
                int categoryId = rs.getInt("category_id");
                note.setCategoryId(categoryId);
                if (categoryId > 0) {
                    CategoryDAO categoryDAO = new CategoryDAO();
                    Category category = categoryDAO.getById(categoryId);
                    note.setCategory(category);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error di NoteDAO.getById: " + e.getMessage());
        }

        return note;
    }


    // Return ID baru
    public int insert(Note note) {
        String sql = "INSERT INTO notes (title, content, created_at, category_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, note.getTitle());
            ps.setString(2, note.getContent());
            ps.setTimestamp(3, Timestamp.valueOf(note.getCreatedAt()));
            ps.setInt(4, note.getCategoryId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int newId = rs.getInt(1);
                note.setId(newId);
                return newId;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1; // gagal
    }


    // Return true jika update berhasil
    public boolean update(Note note) {
        String sql = "UPDATE notes SET title = ?, content = ?, category_id = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, note.getTitle());
            ps.setString(2, note.getContent());
            ps.setInt(3, note.getCategoryId());
            ps.setInt(4, note.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // Return true jika delete berhasil
    public boolean delete(int id) {
        String sql = "DELETE FROM notes WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}