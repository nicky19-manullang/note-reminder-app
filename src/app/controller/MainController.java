/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.controller;

import app.model.Note;
import app.service.NoteService;
import app.view.MainFrame;
import java.util.List;

/**
 *
 * @author admin
 */
public class MainController {

    private NoteService noteService;
    private MainFrame view;

    public MainController(MainFrame view) {
        this.view = view;
        this.noteService = new NoteService();
    }

    public void loadNotes() {
        List<Note> notes = noteService.getAllNotes();
        view.showNotes(notes);
    }

    public void addNote(Note note) {
        noteService.addNote(note);
        loadNotes();
    }

    public void deleteNote(int id) {
        noteService.delete(id);
        loadNotes();
    }
}