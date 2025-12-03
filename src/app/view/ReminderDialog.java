/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.view;
import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
/**
 *
 * @author admin
 */
public class ReminderDialog extends JDialog {

    private JCheckBox enableReminderCheckBox;
    private JSpinner dateSpinner;
    private JSpinner timeSpinner;
    private boolean saved = false;

    public ReminderDialog(Frame parent) {
        super(parent, "Set Reminder", true);
        initComponents();
        setupLayout();
        setSize(350, 250);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        enableReminderCheckBox = new JCheckBox("Enable Reminder");

        // Date Picker
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd-MM-yyyy");
        dateSpinner.setEditor(dateEditor);

        // Time Picker
        timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Enable Reminder:"));
        formPanel.add(enableReminderCheckBox);

        formPanel.add(new JLabel("Date:"));
        formPanel.add(dateSpinner);

        formPanel.add(new JLabel("Time:"));
        formPanel.add(timeSpinner);

        add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> {
            saved = true;
            setVisible(false);
        });

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> {
            saved = false;
            setVisible(false);
        });

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // === Public getters ===
    public boolean isSaved() {
        return saved;
    }

    public boolean isReminderEnabled() {
        return enableReminderCheckBox.isSelected();
    }

    public Date getReminderDateTime() {
        Calendar calendar = Calendar.getInstance();

        Date date = (Date) dateSpinner.getValue();
        Date time = (Date) timeSpinner.getValue();

        calendar.setTime(date);

        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(time);

        // gabungkan date + time
        calendar.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));

        return calendar.getTime();
    }
}