/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dao;
import app.model.Reminder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author admin
 */
public class ReminderDAO {

    public List<Reminder> getAll() {
        List<Reminder> list = new ArrayList<>();
        String sql = "SELECT * FROM reminders ORDER BY id DESC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reminder r = new Reminder();
                r.setId(rs.getInt("id"));
                r.setNoteId(rs.getInt("note_id"));
                r.setReminderDate(rs.getTimestamp("reminder_date"));
                r.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Reminder getById(int id) {
        Reminder r = null;
        String sql = "SELECT * FROM reminders WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                r = new Reminder();
                r.setId(rs.getInt("id"));
                r.setNoteId(rs.getInt("note_id"));
                r.setReminderDate(rs.getTimestamp("reminder_date"));
                r.setCreatedAt(rs.getTimestamp("created_at"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    public boolean insert(Reminder r) {
        String sql = "INSERT INTO reminders(note_id, reminder_date, created_at) VALUES(?, ?, NOW())";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getNoteId());
            ps.setTimestamp(2, r.getReminderDate());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Reminder r) {
        String sql = "UPDATE reminders SET note_id=?, reminder_date=? WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getNoteId());
            ps.setTimestamp(2, r.getReminderDate());
            ps.setInt(3, r.getId());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM reminders WHERE id=?";

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
