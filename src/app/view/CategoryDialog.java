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
public class CategoryDialog extends JDialog {

    private JTextField txtCategoryName;
    private JButton btnSave;
    private JButton btnCancel;

    private String categoryNameResult = null;

    public CategoryDialog(Frame parent) {
        super(parent, "Category", true);
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        JLabel lblTitle = new JLabel("Category Name:");
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        txtCategoryName = new JTextField(20);

        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");

        // Panel Input
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelForm.add(lblTitle, gbc);

        gbc.gridx = 1;
        panelForm.add(txtCategoryName, gbc);

        // Panel Button
        JPanel panelButtons = new JPanel();
        panelButtons.add(btnSave);
        panelButtons.add(btnCancel);

        // Action Listener
        btnSave.addActionListener(e -> saveCategory());
        btnCancel.addActionListener(e -> dispose());

        // Frame Layout
        setLayout(new BorderLayout());
        add(panelForm, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        pack();
    }

    private void saveCategory() {
        String name = txtCategoryName.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Category name cannot be empty!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        categoryNameResult = name;
        dispose();
    }

    /**
     * Mengembalikan kategori yang diinput user
     */
    public String getCategoryName() {
        return categoryNameResult;
    }

    /**
     * Untuk menampilkan dialog
     */
    public static String showDialog(Frame parent) {
        CategoryDialog dialog = new CategoryDialog(parent);
        dialog.setVisible(true);
        return dialog.getCategoryName();
    }
}