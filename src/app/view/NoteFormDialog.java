/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.view;
import app.dao.CategoryDAO;
import app.model.Note;
import app.model.Category;
import app.service.CategoryService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
/**
 *
 * @author admin
 */
public class NoteFormDialog extends JDialog {

    private JTextField txtTitle;
    private JTextArea txtContent;
    private JComboBox<Category> cbCategory;
    private JButton btnSave, btnCancel;

    private Note note;         
    private boolean saved = false;

    public NoteFormDialog(Frame parent) {
        super(parent, true);
        setTitle("Add Note");
        setSize(450, 350);
        setLocationRelativeTo(parent);
        initUI();
        loadCategories();
    }

    public NoteFormDialog(Frame parent, Note existingNote) {
        super(parent, true);
        this.note = existingNote;
        setTitle("Edit Note");
        setSize(450, 350);
        setLocationRelativeTo(parent);
        initUI();
        loadCategories();
        loadData();
    }

    private void initUI() {

        JLabel lblTitle = new JLabel("Title:");
        txtTitle = new JTextField();

        JLabel lblCategory = new JLabel("Category:");
        cbCategory = new JComboBox<>();

        JLabel lblContent = new JLabel("Content:");
        txtContent = new JTextArea(8, 20);
        txtContent.setLineWrap(true);
        txtContent.setWrapStyleWord(true);
        JScrollPane scrollContent = new JScrollPane(txtContent);

        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5, 5, 5, 5);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;

        gc.gridx = 0; gc.gridy = 0;
        form.add(lblTitle, gc);

        gc.gridx = 1; gc.gridy = 0;
        form.add(txtTitle, gc);

        gc.gridx = 0; gc.gridy = 1;
        form.add(lblCategory, gc);

        gc.gridx = 1; gc.gridy = 1;
        form.add(cbCategory, gc);

        gc.gridx = 0; gc.gridy = 2;
        form.add(lblContent, gc);

        gc.gridx = 1; gc.gridy = 2;
        form.add(scrollContent, gc);

        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> saveNote());
        btnCancel.addActionListener(e -> dispose());
    }

    private void loadCategories() {
        CategoryDAO dao = new CategoryDAO();
        List<Category> categories = dao.getAll();

        cbCategory.removeAllItems(); // penting

        for(Category c : categories) {
            cbCategory.addItem(c);
        }
    }

    private void loadData() {
        if (note != null) {
            txtTitle.setText(note.getTitle());
            txtContent.setText(note.getContent());

            for(int i = 0; i < cbCategory.getItemCount(); i++){
                Category c = cbCategory.getItemAt(i);
                if(c.getId() == note.getCategoryId()){
                    cbCategory.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void saveNote() {
        String title = txtTitle.getText().trim();
        String content = txtContent.getText().trim();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title tidak boleh kosong!");
            return;
        }

        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Content tidak boleh kosong!");
            return;
        }

        if (note == null) {
            note = new Note();
            note.setCreatedAt(LocalDateTime.now());
        }

        note.setTitle(title);
        note.setContent(content);

        Category selected = (Category) cbCategory.getSelectedItem();
        if(selected != null){
            note.setCategoryId(selected.getId());
        }

        saved = true;
        dispose();
    }

    public boolean isSaved() {
        return saved;
    }

    public Note getNote() {
        return note;
    }
}