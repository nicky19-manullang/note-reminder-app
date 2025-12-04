/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;
import app.dao.NoteDAO;
import app.model.Note;
import java.util.List;
/**
 *
 * @author admin
 */
public class NoteService {

    private NoteDAO dao = new NoteDAO();

    public List<Note> getAllNotes() {
        return dao.getAll();
    }

    public void addNote(Note note) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
