/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ViewMedicationHistoryWindow extends JFrame {
    private JPanel medicationPanel;
    private ArrayList<MedicationInfo> medications = new ArrayList<>();

    public ViewMedicationHistoryWindow() {
        setTitle("Medication History");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(400, 300);

        
        Color backgroundColor = new Color(173, 216, 230); // Light Blue
        getContentPane().setBackground(backgroundColor); 

        medicationPanel = new JPanel();
        medicationPanel.setLayout(new BoxLayout(medicationPanel, BoxLayout.Y_AXIS));
        medicationPanel.setBackground(backgroundColor); 
        add(new JScrollPane(medicationPanel), BorderLayout.CENTER);

    
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                returnToMainMenu();
            }
        });

        loadHistory();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void returnToMainMenu() {
        setVisible(false);
        new MainMenuWindow().setVisible(true);
    }

    private void loadHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("medications.txt"))) {
            String line;
            medicationPanel.removeAll();
            medications.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                
                if (parts.length >= 4) {
                    String medication = parts[0].split(": ")[1];
                    int days = Integer.parseInt(parts[1].split(": ")[1]);
                    int allowedDays = Integer.parseInt(parts[2].split(": ")[1]);
                    String pills = parts[3].split(": ")[1];

                    medications.add(new MedicationInfo(medication, days, allowedDays, pills));
                    addMedicationToPanel(medication, days, pills, allowedDays);
                }
            }
            medicationPanel.revalidate();
            medicationPanel.repaint();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading history: " + ex.getMessage());
        }
    }

    private void addMedicationToPanel(String medication, int days, String pills, int allowedDays) {
        JPanel medPanel = new JPanel();
        medPanel.setLayout(new FlowLayout());
        medPanel.setBackground(new Color(173, 216, 230)); 

        JLabel medLabel = new JLabel(String.format("%s (%s): %d/%d days", medication, pills, days, allowedDays));
        JButton increaseButton = new JButton("Increase");
        increaseButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            increaseDays(medication);
            medLabel.setText(String.format("%s (%s): %d/%d days", medication, pills, getDays(medication), allowedDays));
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(ViewMedicationHistoryWindow.this, ex.getMessage()); 
        }
    }
});

        medPanel.add(medLabel);
        medPanel.add(increaseButton);
        medicationPanel.add(medPanel);
    }

    private void increaseDays(String medication) {
    for (int i = 0; i < medications.size(); i++) {
        MedicationInfo info = medications.get(i);
        if (info.name.equals(medication)) {
            if (info.days >= info.allowedDays) {
                throw new IllegalStateException("Cannot increase days: treatment period for " + medication + " has ended.");
            }
            info.days++;
            saveMedication(); 
            break; 
        }
    }
}

    private int getDays(String medication) {
    for (int i = 0; i < medications.size(); i++) {
        MedicationInfo info = medications.get(i);
        if (info.name.equals(medication)) {
            return info.days;
        }
    }
    return 0;
    }

    private void saveMedication() {
    try (FileWriter writer = new FileWriter("medications.txt")) {
        for (int i = 0; i < medications.size(); i++) {
            MedicationInfo info = medications.get(i);
            writer.write(String.format("Medication: %s, Days: %d, AllowedDays: %d, Pills: %s%n",
                    info.name, info.days, info.allowedDays, info.pills));
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error saving medication: " + e.getMessage());
    }
}

  
    private class MedicationInfo {
        String name;
        int days;
        int allowedDays;
        String pills;

        MedicationInfo(String name, int days, int allowedDays, String pills) {
            this.name = name;
            this.days = days;
            this.allowedDays = allowedDays;
            this.pills = pills;
        }
    }
}