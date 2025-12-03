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
public class Note {

    private int id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    // Konstruktor kosong (wajib untuk Swing & DAO)
    public Note() {}

    // Konstruktor lengkap
    public Note(int id, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Konstruktor tanpa ID (untuk insert)
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return title; // supaya tampil bagus di combo box atau list
    }
}
