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

    public AboutDialog(JFrame parent) {
        super(parent, "About Application", true);
        setSize(400, 260);
        setLocationRelativeTo(parent);
        setResizable(false);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10,10));

        // ===== Judul =====
        JLabel lblTitle = new JLabel("Note & Reminder App", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));

        // ===== Konten =====
        JTextArea txtAbout = new JTextArea();
        txtAbout.setEditable(false);
        txtAbout.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtAbout.setText(
                "Aplikasi sederhana untuk mencatat dan mengatur reminder.\n\n" +
                "Fitur:\n" +
                "• Tambah catatan\n" +
                "• Reminder dengan tanggal dan waktu\n" +
                "• Export catatan ke PDF\n" +
                "• About dialog\n\n" +
                "Developed by:\n" +
                "Kelompok 2 (PBO Mafufu) \n" +
                "(2025)"
        );

        txtAbout.setBackground(getBackground());

        JScrollPane scroll = new JScrollPane(txtAbout);
        scroll.setBorder(null);

        // ===== BUTTON =====
        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> dispose());

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnOk);

        // ===== Menambahkan =====
        add(lblTitle, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }
}