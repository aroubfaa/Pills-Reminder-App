package project;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuWindow extends JFrame {
    public MainMenuWindow() {
        setTitle("Medication Reminder App");
        setLayout(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

       
        getContentPane().setBackground(new Color(173, 216, 230)); 

     
        JMenuBar menuBar = new JMenuBar();
        JMenu backgroundMenu = new JMenu("Change Background Color");

   
        String[] colors = {"Light Blue", "Light Pink", "Light Purple"};
        for (String color : colors) {
            JMenuItem menuItem = new JMenuItem(color);
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    changeBackgroundColor(color);
                }
            });
            backgroundMenu.add(menuItem);
        }

        menuBar.add(backgroundMenu);
        setJMenuBar(menuBar);

       
     ImageIcon originalIcon = new ImageIcon(getClass().getResource("/r/logo.jpg"));
     Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
     JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
     iconLabel.setBounds(250, 10, 100, 100); // x,y,w,h
     add(iconLabel);


        JLabel welcomeLabel = new JLabel("Welcome to the Medication Reminder App", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        welcomeLabel.setBounds(0, 120, 600, 50); 
        add(welcomeLabel);

        
        JButton addMedicationButton = new JButton("Add Medication");
        addMedicationButton.setBounds(200, 180, 200, 30); 
        addMedicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AddMedicationWindow().setVisible(true);
            }
        });

        addMedicationButton.setBackground(Color.WHITE); 
        add(addMedicationButton);

       
        JButton viewHistoryButton = new JButton("View Medication History");
        viewHistoryButton.setBounds(200, 220, 200, 30); 
        viewHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new ViewMedicationHistoryWindow().setVisible(true);
            }
        });

        viewHistoryButton.setBackground(Color.WHITE); 
        add(viewHistoryButton);

        
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(200, 260, 200, 30); 
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        exitButton.setBackground(Color.WHITE); 
        add(exitButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void changeBackgroundColor(String color) {
        switch (color) {
            case "Light Blue":
                getContentPane().setBackground(new Color(173, 216, 230)); // Light Blue
                break;
            case "Light Pink":
                getContentPane().setBackground(new Color(246, 232, 239)); // Pink
                break;
            case "Light Purple":
                getContentPane().setBackground(new Color(230, 230, 250)); // Light Purple
                break;
        }
        revalidate(); // when you have made changes to the components in a container (like adding or removing components)
        repaint(); // ensures that the new background color is displayed on the screen
    }
}
