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
        System.out.println("=== AboutDialog Baru Dipakai ===");
        initUI();
    }

    private void initUI() {

        setSize(400, 260);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout(10,10));

        JLabel lblTitle = new JLabel("Note & Reminder App", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JTextArea txtAbout = new JTextArea();
        txtAbout.setEditable(false);
        txtAbout.setOpaque(false);
        txtAbout.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtAbout.setText(
                "Aplikasi sederhana untuk mencatat dan mengatur reminder.\n" +
                "Fitur:\n" +
                "1. Tambah catatan\n" +
                "2. Reminder dengan tanggal dan waktu\n" +
                "3. Export catatan ke PDF\n" +
                "4. About dialog\n\n" +
                "Developed by:\n" +
                "Kelompok 2 (PBO Mafufu)\n" +
                "(2025)"
        );

        JScrollPane scroll = new JScrollPane(txtAbout);
        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);

        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> dispose());

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnOk);

        add(lblTitle, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }
}