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

    private ReminderDAO dao = new ReminderDAO();

    public List<Reminder> getAll() {
        return dao.getAll();
    }

    public boolean insert(Reminder r) {
        return dao.insert(r);
    }

    public boolean update(Reminder r) {
        return dao.update(r);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }
}