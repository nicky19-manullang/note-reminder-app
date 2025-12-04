/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dao;

import app.model.Note;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author admin
 */
public class NoteDAO {

    public List<Note> getAll() {
        List<Note> list = new ArrayList<>();

        String sql = "SELECT * FROM notes ORDER BY created_at DESC";

        try(Connection conn = DatabaseManager.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()) {
                Note n = new Note(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getInt("category_id")
                );
                list.add(n);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Note getById(int id) {
        String sql = "SELECT * FROM notes WHERE id = ?";
        Note note = null;

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                note = new Note(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getInt("category_id")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return note;
    }

    public void insert(Note note) {
        String sql = "INSERT INTO notes(title, content, created_at, category_id) VALUES (?, ?, ?, ?)";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, note.getTitle());
            ps.setString(2, note.getContent());
            ps.setTimestamp(3, Timestamp.valueOf(note.getCreatedAt()));
            ps.setInt(4, note.getCategoryId());

            ps.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Note note) {
        String sql = "UPDATE notes SET title = ?, content = ?, category_id = ? WHERE id = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, note.getTitle());
            ps.setString(2, note.getContent());
            ps.setInt(3, note.getCategoryId());
            ps.setInt(4, note.getId());

            ps.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM notes WHERE id = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}