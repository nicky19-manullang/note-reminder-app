/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;
import app.dao.CategoryDAO;
import app.dao.NoteDAO;
import app.model.Category;
import app.model.Note;
import java.util.List;
/**
 *
 * @author admin
 */
public class CategoryService {
    private CategoryDAO categoryDAO = new CategoryDAO();

    public List<Category> getAll() {
        return categoryDAO.getAll();
    }

    public boolean insert(Category c) {
        return categoryDAO.insert(c);
    }

    public boolean update(Category c) {
        return categoryDAO.update(c);
    }

    public boolean delete(int id) {
        return categoryDAO.delete(id);
    }
}
