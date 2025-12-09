/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.view;
import javax.swing.*;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Font;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import app.model.Note;
import app.service.NoteService;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import app.model.Reminder;
import app.service.ReminderService;
import app.view.AboutDialog;


/**
 *
 * @author admin
 */
public class MainFrame extends JFrame {

    private JTable tblNotes;
    private DefaultTableModel tableModel;
    private java.util.List<Note> noteList = new java.util.ArrayList<>();
    private ReminderService reminderService = new ReminderService();
    private void startReminderChecker() {
    Timer timer = new Timer(1000, e -> checkReminder()); 
    timer.start();
}

    public MainFrame() {
        setTitle("Note Reminder App");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
        startReminderChecker();
    }

    private void initUI() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Title", "Category", "Content", "Created At"}, 0
        );
        tblNotes = new JTable(tableModel);
        loadNotesFromDatabase();
        JScrollPane scrollPane = new JScrollPane(tblNotes);

        // Buttons
        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");
        JButton btnReminder = new JButton("Reminder");

        JPanel panel = new JPanel();
        panel.add(btnAdd);
        panel.add(btnEdit);
        panel.add(btnDelete);
        panel.add(btnReminder);

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem menuExport = new JMenuItem("Export PDF");
        JMenuItem menuExit = new JMenuItem("Exit");

        menuFile.add(menuExport);
        menuFile.add(menuExit);

        JMenu menuHelp = new JMenu("Help");
        JMenuItem menuAbout = new JMenuItem("About");
        menuHelp.add(menuAbout);

        menuBar.add(menuFile);
        menuBar.add(menuHelp);

        setJMenuBar(menuBar);

        // LAYOUT
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        // Actions
        btnAdd.addActionListener(e -> openAddForm());
        btnEdit.addActionListener(e -> openEditForm());
        btnDelete.addActionListener(e -> deleteNote());
        btnReminder.addActionListener(e -> openReminder());
        menuExport.addActionListener(e -> exportPDF());
        menuAbout.addActionListener(e -> openAbout());
        menuExit.addActionListener(e -> System.exit(0));
    }

    private void openAddForm() {

    NoteFormDialog dialog = new NoteFormDialog(this);
    dialog.setVisible(true);

    if (dialog.isSaved()) {

        Note note = dialog.getNote();

        NoteService service = new NoteService();
        int generatedId = service.addNote(note);  
        note.setId(generatedId);

        noteList.add(note);

        tableModel.addRow(new Object[]{
                note.getId(),
                note.getTitle(),
                note.getCategory() != null ? note.getCategory().getName() : "-",
                note.getContent(),
                note.getCreatedAt()
        });
    }
}


    private void openEditForm() {
    int row = tblNotes.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Pilih catatan yang ingin diedit!");
        return;
    }

    // Ambil note dari list
    Note note = noteList.get(row);

    // Buka dialog edit
    NoteFormDialog dialog = new NoteFormDialog(this, note);
    dialog.setVisible(true);

    if (dialog.isSaved()) {

        // Simpan perubahan ke database
        NoteService service = new NoteService();
        boolean success = service.updateNote(note);

        if (!success) {
            JOptionPane.showMessageDialog(this, "Gagal mengupdate note!");
            return;
        }

        // Update table UI
        tableModel.setValueAt(note.getTitle(), row, 1);
        tableModel.setValueAt(
            note.getCategory() != null ? note.getCategory().getName() : "-",
            row, 
            2
        );
        tableModel.setValueAt(note.getContent(), row, 3);

        JOptionPane.showMessageDialog(this, "Note berhasil diupdate!");
    }
}

private void deleteNote() {
    int row = tblNotes.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Pilih catatan yang ingin dihapus!");
        return;
    }

    Note note = noteList.get(row);

    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Yakin ingin menghapus note ini?",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) return;

    // Delete ke database
    NoteService service = new NoteService();
    boolean success = service.deleteNote(note.getId());

    if (!success) {
        JOptionPane.showMessageDialog(this, "Gagal menghapus note dari database!");
        return;
    }

    // Hapus dari program
    noteList.remove(row);
    tableModel.removeRow(row);

    JOptionPane.showMessageDialog(this, "Note berhasil dihapus!");
}

private void openReminder() {
    ReminderDialog dialog = new ReminderDialog(this);
    dialog.setVisible(true);

    if (!dialog.isSaved()) return;

    int noteId = getSelectedNoteId();
    if (noteId == -1) return;

    Reminder r = new Reminder();
    r.setNoteId(noteId);
    r.setRemindAt(dialog.getDateTime());
    r.setStatus("PENDING");

    boolean saved = reminderService.insert(r);

    if (saved) {
        JOptionPane.showMessageDialog(this, "Reminder berhasil disimpan!");
    } else {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan reminder!");
    }
}
private void checkReminder() {
    // 1. Ambil data reminder terbaru
    List<Reminder> list = reminderService.getAll();
    LocalDateTime now = LocalDateTime.now(); 

    for (Reminder r : list) {
        LocalDateTime reminderTime = r.getRemindAt();
        if ("PENDING".equalsIgnoreCase(r.getStatus()) 
            && !reminderTime.isAfter(now)) {
            r.setStatus("DONE");
            reminderService.update(r);
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(
                        this,
                        "Ingat note atau schedule anda",  
                        "Reminder",                      
                        JOptionPane.INFORMATION_MESSAGE
                );
            });
            
            System.out.println("Reminder statis muncul untuk ID: " + r.getId());
        }
    }
}

    private void openAbout() {
        new AboutDialog(this).setVisible(true);
    }

private void exportPDF() {

    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Save Notes PDF");

    if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

    String path = chooser.getSelectedFile().getAbsolutePath();
    if (!path.endsWith(".pdf")) path += ".pdf";

    try {
        Document pdf = new Document();
        PdfWriter.getInstance(pdf, new FileOutputStream(path));
        pdf.open();

        // Title PDF
        Paragraph title = new Paragraph("Notes Export");
        title.setAlignment(Paragraph.ALIGN_CENTER);
        pdf.add(title);
        pdf.add(new Paragraph("\n"));

        // Table with 5 columns (ID, Title, Category, Content, Created At)
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);

        // Header font
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font rowFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        // Header cells
        table.addCell(new PdfPCell(new Paragraph("ID", headerFont)));
        table.addCell(new PdfPCell(new Paragraph("Title", headerFont)));
        table.addCell(new PdfPCell(new Paragraph("Category", headerFont)));
        table.addCell(new PdfPCell(new Paragraph("Content", headerFont)));
        table.addCell(new PdfPCell(new Paragraph("Created At", headerFont)));

        // Data rows
        for (Note n : noteList) {
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(n.getId()), rowFont)));
            table.addCell(new PdfPCell(new Paragraph(n.getTitle(), rowFont)));
            
            // === CATEGORY ===
            String category = (n.getCategory() != null) ? n.getCategory().getName() : "-";
            table.addCell(new PdfPCell(new Paragraph(category, rowFont)));

            table.addCell(new PdfPCell(new Paragraph(n.getContent(), rowFont)));
            table.addCell(new PdfPCell(new Paragraph(n.getCreatedAt().toString(), rowFont)));
        }

        pdf.add(table);
        pdf.close();

        JOptionPane.showMessageDialog(this, "Saved to: " + path);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed export!");
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }

    private void loadNotesFromDatabase() {
    NoteService noteService = new NoteService();
    List<Note> notes = noteService.getAllNotes();

    // kosongkan tabel 
    tableModel.setRowCount(0);
    noteList.clear();

    // masukkan data ke tabel
    for (Note n : notes) {
    noteList.add(n);
    tableModel.addRow(new Object[]{
        n.getId(),
        n.getTitle(),
        n.getCategory() != null ? n.getCategory().getName() : "-",
        n.getContent(),
        n.getCreatedAt()
        });
    }
}
    public void showNotes(List<Note> notes) {
    tableModel.setRowCount(0);
    noteList.clear();

    for (Note n : notes) {
    noteList.add(n);
    tableModel.addRow(new Object[]{
        n.getId(),
        n.getTitle(),
        n.getCategory() != null ? n.getCategory().getName() : "-",
        n.getContent(),
        n.getCreatedAt()
        });
        }
    }

    private int getSelectedNoteId() {
    int row = tblNotes.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Pilih note dulu!");
        return -1;
    }
    return (int) tableModel.getValueAt(row, 0); // kolom 0 = ID
}
}