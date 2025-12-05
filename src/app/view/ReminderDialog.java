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

    private JTextField txtTitle;
    private JTextArea txtMessage;
    private JSpinner dateSpinner;
    private boolean saved = false;

    public ReminderDialog(JFrame parent) {
        super(parent, "Set Reminder", true);
        setSize(400, 350);
        setLocationRelativeTo(parent);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Panel input
        JPanel formPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        txtTitle = new JTextField();

        txtMessage = new JTextArea(5, 20);
        txtMessage.setLineWrap(true);
        txtMessage.setWrapStyleWord(true);

        dateSpinner = new JSpinner(
                new SpinnerDateModel()
        );
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd HH:mm");
        dateSpinner.setEditor(editor);

        formPanel.add(labeled("Reminder Title", txtTitle));
        formPanel.add(labeled("Message", new JScrollPane(txtMessage)));
        formPanel.add(labeled("Date & Time", dateSpinner));

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton btnSave = new JButton("Save");
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

    public String getTitleText() {
        return txtTitle.getText();
    }

    public String getMessageText() {
        return txtMessage.getText();
    }

    public LocalDateTime getDateTime() {
        java.util.Date date = (java.util.Date) dateSpinner.getValue();
        return LocalDateTime.ofInstant(
                date.toInstant(),
                java.time.ZoneId.systemDefault()
        );
    }
}