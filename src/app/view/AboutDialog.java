/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.view;
import javax.swing.*;
import java.awt.*;  
/**
 *
 * @author admin
 */
public class AboutDialog extends JDialog {

    public AboutDialog(Frame parent) {
        super(parent, "About", true);
        initComponents();
        setSize(400, 250);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // === Panel Title ===
        JLabel lblTitle = new JLabel("NoteKeeper App", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        add(lblTitle, BorderLayout.NORTH);

        // === Panel Content ===
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setText(
                "Version: 1.0\n" +
                "Developer: Nicky Rotin Suluh Manullang\n" +
                "Created: 2024\n\n" +
                "NoteKeeper adalah aplikasi desktop sederhana untuk mencatat,\n" +
                "mengelola kategori, dan mengatur pengingat.\n"
        );
        textArea.setBackground(new Color(240, 240, 240));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        add(textArea, BorderLayout.CENTER);

        // === Button Panel ===
        JPanel buttonPanel = new JPanel();
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> setVisible(false));

        buttonPanel.add(btnClose);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}