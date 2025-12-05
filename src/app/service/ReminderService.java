/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;
import app.dao.ReminderDAO;
import app.model.Reminder;
import java.util.List;

/**
 *
 * @author admin
 */
public class ReminderService {

    private ReminderDAO reminderDAO = new ReminderDAO();

    public List<Reminder> getAll() {
        return reminderDAO.getAll();
    }

    public boolean insert(Reminder r) {
        return reminderDAO.insert(r);
    }

    public boolean update(Reminder r) {
        return reminderDAO.update(r);
    }

    public boolean delete(int id) {
        return reminderDAO.delete(id);
    }
}