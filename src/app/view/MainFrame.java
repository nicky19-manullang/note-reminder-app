/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.view;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import app.model.Note;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.logging.Logger;
/**
 *
 * @author admin
 */
public class MainFrame extends JFrame {

    private static final Logger logger = Logger.getLogger(MainFrame.class.getName());

    // UI Components
    private JTable tblNotes;
    private JButton btnAdd, btnEdit, btnDelete, btnReminder;
    private JMenuItem menuExport, menuExit, menuAbout;

    private DefaultTableModel tableModel;

    public MainFrame() {
        setTitle("Note & Reminder App");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {

        // =============== TABLE ===============
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Title", "Content", "Created At"}, 0
        );

        tblNotes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblNotes);

        // =============== BUTTONS ===============
        btnAdd = new JButton("Add Note");
        btnEdit = new JButton("Edit Note");
        btnDelete = new JButton("Delete Note");
        btnReminder = new JButton("Reminder");

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnReminder);

        // =============== MENU BAR ===============
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        menuExport = new JMenuItem("Export Notes");
        menuExit = new JMenuItem("Exit");

        menuFile.add(menuExport);
        menuFile.add(menuExit);

        JMenu menuHelp = new JMenu("Help");
        menuAbout = new JMenuItem("About");

        menuHelp.add(menuAbout);

        menuBar.add(menuFile);
        menuBar.add(menuHelp);

        setJMenuBar(menuBar);

        // =============== LAYOUT ===============
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // =============== EVENT ===============
        btnAdd.addActionListener(e -> openAddForm());
        btnEdit.addActionListener(e -> openEditForm());
        btnDelete.addActionListener(e -> deleteNote());
        btnReminder.addActionListener(e -> openReminder());
        menuExport.addActionListener(e -> exportNotes());
        menuExit.addActionListener(e -> System.exit(0));
        menuAbout.addActionListener(e -> openAbout());
    }

    // =================== EVENT METHODS ===================

    private void openAddForm() {
        NoteFormDialog dialog = new NoteFormDialog(this);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            Note n = dialog.getNote();
            tableModel.addRow(new Object[]{
                    n.getId(),
                    n.getTitle(),
                    n.getContent(),
                    n.getCreatedAt()
            });
        }
    }

    private void openEditForm() {
        int row = tblNotes.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih catatan dulu!");
            return;
        }
        // Nanti dilanjut saat NoteFormDialog sudah ada
    }

    private void deleteNote() {
        int row = tblNotes.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih catatan yang mau dihapus!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Hapus catatan ini?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(row);
        }
    }

    private void openReminder() {
        ReminderDialog dialog = new ReminderDialog(this);
        dialog.setVisible(true);
    }

    private void exportNotes() {
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Save Notes as PDF");

    int result = chooser.showSaveDialog(this);
    if (result != JFileChooser.APPROVE_OPTION) return;

    String filePath = chooser.getSelectedFile().getAbsolutePath();

    if (!filePath.endsWith(".pdf")) {
        filePath += ".pdf";
    }

    try {
        com.itextpdf.text.Document pdf = new com.itextpdf.text.Document();
        com.itextpdf.text.pdf.PdfWriter.getInstance(pdf, new java.io.FileOutputStream(filePath));

        pdf.open();
        pdf.add(new com.itextpdf.text.Paragraph("Notes Export"));
        pdf.add(new com.itextpdf.text.Paragraph("====================================\n\n"));

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String id = String.valueOf(tableModel.getValueAt(i, 0));
            String title = String.valueOf(tableModel.getValueAt(i, 1));
            String content = String.valueOf(tableModel.getValueAt(i, 2));
            String createdAt = String.valueOf(tableModel.getValueAt(i, 3));

            pdf.add(new com.itextpdf.text.Paragraph(
                    "ID: " + id + "\n" +
                    "Title: " + title + "\n" +
                    "Content: " + content + "\n" +
                    "Created At: " + createdAt + "\n\n"
            ));
        }

        pdf.close();

        JOptionPane.showMessageDialog(this, "PDF berhasil disimpan:\n" + filePath);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal export PDF!");
    }
}

    private void openAbout() {
        AboutDialog dialog = new AboutDialog(this);
        dialog.setVisible(true);
    }

    // RUN APP
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}