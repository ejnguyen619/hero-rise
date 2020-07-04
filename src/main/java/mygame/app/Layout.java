package mygame.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mygame.app.App.ChoiceHandler;
import mygame.app.App.KeyHandler;

public class Layout {

    JFrame window;
    JPanel titleScreenPanel, startButtonPanel, mainTextPanel, choiceButtonPanel, playerPanel, hpPanel, mpPanel, imagePanel, healthBarPanel, manaBarPanel, msgPanel;
    JLabel titleNameLabel, hpLabel, hpLabelNumber, mpLabel, mpLabelNumber, imageLabel, msgLabel;
    JButton startButton;
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 90);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    Font helpFont = new Font("Times New Roman", Font.PLAIN, 20);
    JButton[] choices = new JButton[6];
    JTextArea mainTextArea;
    ImageIcon image;
    JTextField jtf;
    JProgressBar healthBar, manaBar;

    public void createStartScreen(ChoiceHandler cHandler, KeyHandler kHandler){

        // Create window for game
        window = new JFrame();

        // Create title screen
        titleScreenPanel = new JPanel();
        titleScreenPanel.setBounds(100, 100, 600, 150);
        titleScreenPanel.setBackground(Color.BLACK);

        titleNameLabel = new JLabel();
        titleNameLabel.setForeground(Color.WHITE);
        titleNameLabel.setFont(titleFont);

        // Add message for name
        msgPanel = new JPanel();
        msgPanel.setBounds(200, 325, 400, 50);
        msgPanel.setBackground(Color.BLACK);
        window.add(msgPanel);

        msgLabel = new JLabel();
        msgLabel.setForeground(Color.WHITE);
        msgLabel.setFont(normalFont);
        msgPanel.add(msgLabel);

        // Add start button
        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(250, 400, 300, 100);
        startButtonPanel.setBackground(Color.BLACK);
        startButtonPanel.setLayout(new GridLayout(2,1));

        // Create user input text box
        jtf = new JTextField();
        jtf.setFont(normalFont);
        jtf.addKeyListener(kHandler);
        startButtonPanel.add(jtf);

        startButton = new JButton();
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.WHITE);
        startButton.setFont(normalFont);
        startButton.setFocusPainted(false);
        startButton.addActionListener(cHandler);
        startButton.setActionCommand("start");
        startButton.setEnabled(false);

        titleScreenPanel.add(titleNameLabel);
        startButtonPanel.add(startButton);

        window.add(titleScreenPanel);
        window.add(startButtonPanel);

        // Set window parameters
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);
        window.setVisible(true);
    }

    public void createGameScreen(ChoiceHandler cHandler){

        // Hide title screen panels
        titleScreenPanel.setVisible(false);
        startButtonPanel.setVisible(false);
        msgPanel.setVisible(false);

        // Create main body text box
        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(50, 350, 430, 250);
        mainTextPanel.setBackground(Color.BLACK);
        window.add(mainTextPanel);

        mainTextArea = new JTextArea("Text Area for Hero Rise, which contains battle information.");
        mainTextArea.setBounds(50, 350, 430, 250);
        mainTextArea.setBackground(Color.BLACK);
        mainTextArea.setForeground(Color.WHITE);
        mainTextArea.setFont(normalFont);
        mainTextArea.setLineWrap(true);
        mainTextArea.setWrapStyleWord(true);
        mainTextPanel.add(mainTextArea);

        // Create choice box panel
        choiceButtonPanel = new JPanel();
        choiceButtonPanel.setBounds(500, 350, 250, 150);
        choiceButtonPanel.setBackground(Color.BLACK);
        choiceButtonPanel.setLayout(new GridLayout(6,1));
        window.add(choiceButtonPanel);

        String action;

        for(int i = 0; i < choices.length; i++){
            action = "c" + String.valueOf(i + 1);
            choices[i] = new JButton();
            choices[i].setBackground(Color.BLACK);
            choices[i].setForeground(Color.WHITE);
            choices[i].setFont(normalFont);
            choices[i].setFocusPainted(false);
            choices[i].addActionListener(cHandler);
            choices[i].setActionCommand(action);
            choiceButtonPanel.add(choices[i]);
        }

        // Create Health Bar
        healthBarPanel = new JPanel();
        hpLabel = new JLabel();
        healthBar = new JProgressBar(0,100);
        healthBar.setBackground(Color.RED);
        healthBar.setForeground(Color.GREEN);
        playerBarPanel(healthBarPanel, hpLabel, healthBar, 100);

        // Create Mana Bar
        manaBarPanel = new JPanel();
        mpLabel = new JLabel();
        manaBar = new JProgressBar(0,50);
        manaBar.setBackground(Color.WHITE);
        manaBar.setForeground(Color.BLUE);
        playerBarPanel(manaBarPanel, mpLabel, manaBar, 450);

        // Add image
        imagePanel = new JPanel();
        imagePanel.setBounds(100, 100, 600, 230);
        imagePanel.setBackground(Color.BLACK);

        imageLabel = new JLabel();
        image = new ImageIcon();
        imageLabel.setIcon(image);
        imagePanel.add(imageLabel);
        window.add(imagePanel);

        // Add numerical text boxes for HP and MP
        hpPanel = new JPanel();
        hpLabelNumber = new JLabel();
        playerTextPanel(hpPanel, hpLabelNumber, 225);

        mpPanel = new JPanel();
        mpLabelNumber = new JLabel();
        playerTextPanel(mpPanel, mpLabelNumber, 575);
    }

    // Set up player bar panel
    public void playerBarPanel(JPanel panel, JLabel label, JProgressBar bar, int panelx){
        panel.setBounds(panelx,15,250,30);
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridLayout(1,2));
        window.add(panel);

        label.setFont(normalFont);
        label.setForeground(Color.WHITE);
        panel.add(label);

        bar.setPreferredSize(new Dimension(200,30));
        bar.setValue(100);
        panel.add(bar);        
    }

    // Set up player text panel
    public void playerTextPanel(JPanel panel, JLabel label, int panelx){
        panel.setBounds(panelx, 45, 125, 25);
        panel.setBackground(Color.BLACK);
        window.add(panel);

        label.setForeground(Color.WHITE);
        label.setFont(helpFont);
        panel.add(label);        
    }

    // Toggle start button status based on user name input
    public void name(){
        if(jtf.getText().length() > 0)
            startButton.setEnabled(true);
        else
            startButton.setEnabled(false);
    }
}