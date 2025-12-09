/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.view;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
/**
 *
 * @author admin
 */
public class ReminderDialog extends JDialog {

    private JSpinner dateSpinner;
    private boolean saved = false;

    public ReminderDialog(JFrame parent) {
        super(parent, "Set Reminder", true);
        setSize(350, 200);
        setLocationRelativeTo(parent);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(1, 1, 10, 10));

        // Date-time spinner
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd HH:mm:ss");
        dateSpinner.setEditor(editor);

        formPanel.add(labeled("Reminder Date & Time", dateSpinner));
        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton btnSave = new JButton("Save Reminder");
        JButton btnCancel = new JButton("Cancel");

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            saved = true;
            dispose();
        });

        btnCancel.addActionListener(e -> dispose());
    }

    private JPanel labeled(String text, Component c) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(text), BorderLayout.NORTH);
        p.add(c, BorderLayout.CENTER);
        return p;
    }

    public boolean isSaved() {
        return saved;
    }

    public LocalDateTime getDateTime() {
        java.util.Date date = (java.util.Date) dateSpinner.getValue();
        return LocalDateTime.ofInstant(
                date.toInstant(),
                java.time.ZoneId.systemDefault()
        );
    }
}