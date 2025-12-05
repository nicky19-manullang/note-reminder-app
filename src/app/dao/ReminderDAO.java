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
        String sql = "SELECT * FROM reminders ORDER BY remind_at ASC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reminder r = new Reminder(
                        rs.getInt("id"),
                        rs.getInt("note_id"),
                        rs.getTimestamp("remind_at").toLocalDateTime(),
                        rs.getString("status")
                );
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Reminder r) {
        String sql = "INSERT INTO reminders(note_id, remind_at, status) VALUES(?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getNoteId());
            ps.setTimestamp(2, Timestamp.valueOf(r.getRemindAt()));
            ps.setString(3, r.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Reminder r) {
        String sql = "UPDATE reminders SET note_id=?, remind_at=?, status=? WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getNoteId());
            ps.setTimestamp(2, Timestamp.valueOf(r.getRemindAt()));
            ps.setString(3, r.getStatus());
            ps.setInt(4, r.getId());

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