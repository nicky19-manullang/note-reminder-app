/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.view;
import app.model.Note;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
/**
 *
 * @author admin
 */
public class NoteFormDialog extends JDialog {

    private JTextField txtTitle;
    private JTextArea txtContent;
    private JButton btnSave, btnCancel;

    private Note note;         // null = mode tambah
    private boolean saved = false;

    public NoteFormDialog(Frame parent) {
        super(parent, true);
        setTitle("Add / Edit Note");
        setSize(450, 350);
        setLocationRelativeTo(parent);
        initUI();
    }

    public NoteFormDialog(Frame parent, Note existingNote) {
        super(parent, true);
        this.note = existingNote;   // mode edit
        setTitle("Edit Note");
        setSize(450, 350);
        setLocationRelativeTo(parent);
        initUI();
        loadData();
    }

    private void initUI() {

        // ========== INPUT COMPONENTS ==========
        JLabel lblTitle = new JLabel("Title:");
        txtTitle = new JTextField();

        JLabel lblContent = new JLabel("Content:");
        txtContent = new JTextArea(8, 20);
        txtContent.setLineWrap(true);
        txtContent.setWrapStyleWord(true);
        JScrollPane scrollContent = new JScrollPane(txtContent);

        // ========== BUTTONS ==========
        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);

        // ========== LAYOUT PANEL ==========
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5, 5, 5, 5);
        gc.fill = GridBagConstraints.HORIZONTAL;

        gc.gridx = 0; gc.gridy = 0;
        form.add(lblTitle, gc);

        gc.gridx = 1; gc.gridy = 0;
        form.add(txtTitle, gc);

        gc.gridx = 0; gc.gridy = 1;
        form.add(lblContent, gc);

        gc.gridx = 1; gc.gridy = 1;
        form.add(scrollContent, gc);

        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // ========== EVENTS ==========
        btnSave.addActionListener(e -> saveNote());
        btnCancel.addActionListener(e -> dispose());
    }

    private void loadData() {
        if (note != null) {
            txtTitle.setText(note.getTitle());
            txtContent.setText(note.getContent());
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
            // mode tambah
            note = new Note();
            note.setCreatedAt(LocalDateTime.now());
        }

        note.setTitle(title);
        note.setContent(content);

        saved = true;
        dispose();
    }

    // ========== GETTER UNTUK MAIN FRAME ==========
    public boolean isSaved() {
        return saved;
    }

    public Note getNote() {
        return note;
    }
}