
package project;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AddMedicationWindow extends JFrame {
    private JTextField medicationTextField;
    private JTextField daysTextField; 
    private JComboBox<String> pillsComboBox; 

    public AddMedicationWindow() {
        setTitle("Add Medication");
        setLayout(new GridLayout(4, 2)); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);

       
        getContentPane().setBackground(new Color(173, 216, 230)); 

  
        medicationTextField = new JTextField();
        daysTextField = new JTextField(); 

      
        String[] pillsOptions = {"1 pill", "2 pills", "3 pills", "4 pills", "5 pills"};
        pillsComboBox = new JComboBox<>(pillsOptions);

        add(new JLabel("Medication:"));
        add(medicationTextField);
        add(new JLabel("Days:")); 
        add(daysTextField);
        add(new JLabel("Pills:"));
        add(pillsComboBox);

        JButton addButton = new JButton("Add Medication");
        addButton.addActionListener(e -> addMedication());
        add(addButton);

        setLocationRelativeTo(null);
        setVisible(true);

       
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                returnToMainMenu();
            }
        });
    }

    private void addMedication() {
    String medication = medicationTextField.getText().trim();
    String daysInput = daysTextField.getText().trim();
    String pills = (String) pillsComboBox.getSelectedItem();

   
    if (medication.isEmpty() || daysInput.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return; 
    }

    try {
        int allowedDays = Integer.parseInt(daysInput); 

        
        if (isMedicationExist(medication)) {
            JOptionPane.showMessageDialog(this, "This medication already exists.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return; 
        }

        
        int days = 0;

        saveMedication(medication, days, allowedDays, pills);

       
        JOptionPane.showMessageDialog(this, "Medication added successfully!");

     
        returnToMainMenu();
    } catch (NumberFormatException e) {
       
        JOptionPane.showMessageDialog(this, "Please enter a valid number for Days.", "Input Error", JOptionPane.ERROR_MESSAGE);
    }
}


private boolean isMedicationExist(String medication) {
    try (BufferedReader reader = new BufferedReader(new FileReader("medications.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("Medication: " + medication + ",")) {
                return true; 
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading medication file: " + e.getMessage());
    }
    return false;
}

    private void saveMedication(String medication, int days, int allowedDays, String pills) {
        try (FileWriter writer = new FileWriter("medications.txt", true)) { 
            writer.write(String.format("Medication: %s, Days: %d, AllowedDays: %d, Pills: %s%n", medication, days, allowedDays, pills));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving medication: " + e.getMessage());
        }
    }

    private void returnToMainMenu() {
        dispose(); 
        new MainMenuWindow().setVisible(true); 
    } }