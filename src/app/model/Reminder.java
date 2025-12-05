/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import java.time.LocalDateTime;

/**
 *
 * @author admin
 */
public class Reminder {

    private int id;
    private int noteId;
    private LocalDateTime remindAt;
    private String status;

    public Reminder() {}

    public Reminder(int id, int noteId, LocalDateTime remindAt, String status) {
        this.id = id;
        this.noteId = noteId;
        this.remindAt = remindAt;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getNoteId() {
        return noteId;
    }
    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public LocalDateTime getRemindAt() {
        return remindAt;
    }
    public void setRemindAt(LocalDateTime remindAt) {
        this.remindAt = remindAt;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}