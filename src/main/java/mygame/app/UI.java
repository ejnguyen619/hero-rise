package mygame.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.*;

import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mygame.app.App.ChoiceHandler;

public class UI {

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
    KeyHandler key = new KeyHandler();
    JProgressBar healthBar, manaBar;

    public void createUI(ChoiceHandler cHandler){

        // Create window for game
        window = new JFrame();

        // Create title screen
        titleScreenPanel = new JPanel();
        titleScreenPanel.setBounds(100, 100, 600, 150);
        titleScreenPanel.setBackground(Color.BLACK);

        titleNameLabel = new JLabel("HERO RISE");
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
        msgLabel.setText("Please enter your name");
        msgPanel.add(msgLabel);

        // Add start button
        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(250, 400, 300, 100);
        startButtonPanel.setBackground(Color.BLACK);
        startButtonPanel.setLayout(new GridLayout(2,1));

        // Create user input text box
        jtf = new JTextField();
        jtf.setFont(normalFont);
        jtf.addKeyListener(key);
        startButtonPanel.add(jtf);

        startButton = new JButton("START");
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
        healthBarPanel.setBounds(100,15,250,30);
        healthBarPanel.setBackground(Color.BLACK);
        healthBarPanel.setLayout(new GridLayout(1,2));
        window.add(healthBarPanel);

        hpLabel = new JLabel("HP:");
        hpLabel.setFont(normalFont);
        hpLabel.setForeground(Color.WHITE);
        healthBarPanel.add(hpLabel);

        healthBar = new JProgressBar(0,100);
        healthBar.setPreferredSize(new Dimension(200,30));
        healthBar.setBackground(Color.RED);
        healthBar.setForeground(Color.GREEN);
        healthBar.setValue(100);
        healthBarPanel.add(healthBar);

        // Create Mana Bar
        manaBarPanel = new JPanel();
        manaBarPanel.setBounds(450,15,250,30);
        manaBarPanel.setBackground(Color.BLACK);
        manaBarPanel.setLayout(new GridLayout(1,2));
        window.add(manaBarPanel);

        mpLabel = new JLabel("MP:");
        mpLabel.setFont(normalFont);
        mpLabel.setForeground(Color.WHITE);
        manaBarPanel.add(mpLabel);

        manaBar = new JProgressBar(0,50);
        manaBar.setPreferredSize(new Dimension(200,30));
        manaBar.setBackground(Color.WHITE);
        manaBar.setForeground(Color.BLUE);
        manaBar.setValue(50);
        manaBarPanel.add(manaBar);

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
        hpPanel.setBounds(225, 45, 125, 25);
        hpPanel.setBackground(Color.BLACK);
        window.add(hpPanel);

        hpLabelNumber = new JLabel();
        hpLabelNumber.setForeground(Color.WHITE);
        hpLabelNumber.setFont(helpFont);
        hpPanel.add(hpLabelNumber);

        mpPanel = new JPanel();
        mpPanel.setBounds(575, 45, 125, 25);
        mpPanel.setBackground(Color.BLACK);
        window.add(mpPanel);

        mpLabelNumber = new JLabel();
        mpLabelNumber.setForeground(Color.WHITE);
        mpLabelNumber.setFont(helpFont);
        mpPanel.add(mpLabelNumber); 
    }

    // Handle user input for name in title screen
    public class KeyHandler implements KeyListener{

        @Override
        public void keyPressed(KeyEvent e) {  
        }

        @Override
        public void keyTyped(KeyEvent e) { 
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(jtf.getText().length() > 0)
                startButton.setEnabled(true);
            else
                startButton.setEnabled(false);
        }
    }
}