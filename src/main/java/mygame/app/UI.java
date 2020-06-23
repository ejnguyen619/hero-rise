package mygame.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import mygame.app.App.ChoiceHandler;

public class UI {

    JFrame window;
    JPanel titleScreenPanel, startButtonPanel, mainTextPanel, choiceButtonPanel, playerPanel;
    JLabel titleNameLabel, hpLabel, hpLabelNumber, mpLabel, mpLabelNumber;
    JButton startButton;
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 90);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    Font helpFont = new Font("Times New Roman", Font.PLAIN, 25);
    JButton[] choices = new JButton[6];
    JTextArea mainTextArea;

    public void createUI(ChoiceHandler cHandler){

        // Create window for game
        window = new JFrame();

        // Create title screen
        titleScreenPanel = new JPanel();
        titleScreenPanel.setBounds(100, 100, 600, 150);
        titleScreenPanel.setBackground(Color.BLACK);

        // Add title text and start button
        titleNameLabel = new JLabel("HERO RISE");
        titleNameLabel.setForeground(Color.WHITE);
        titleNameLabel.setFont(titleFont);

        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(300, 400, 200, 100);
        startButtonPanel.setBackground(Color.BLACK);

        startButton = new JButton("START");
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.WHITE);
        startButton.setFont(normalFont);
        startButton.setFocusPainted(false);
        startButton.addActionListener(cHandler);
        startButton.setActionCommand("start");

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

        titleScreenPanel.setVisible(false);
        startButtonPanel.setVisible(false);

        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100, 100, 600, 250);
        mainTextPanel.setBackground(Color.BLACK);
        window.add(mainTextPanel);

        mainTextArea = new JTextArea("Text Area for Hero Rise, which contains battle information.");
        mainTextArea.setBounds(100, 100, 600, 250);
        mainTextArea.setBackground(Color.BLACK);
        mainTextArea.setForeground(Color.WHITE);
        mainTextArea.setFont(normalFont);
        mainTextArea.setLineWrap(true);
        mainTextArea.setWrapStyleWord(true);
        mainTextPanel.add(mainTextArea);

        choiceButtonPanel = new JPanel();
        choiceButtonPanel.setBounds(250, 350, 300, 150);
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

        playerPanel = new JPanel();
        playerPanel.setBounds(100, 15, 600, 50);
        playerPanel.setBackground(Color.BLACK);
        playerPanel.setLayout(new GridLayout(1,4));
        window.add(playerPanel);

        hpLabel = new JLabel("HP:");
        hpLabel.setFont(normalFont);
        hpLabel.setForeground(Color.WHITE);
        playerPanel.add(hpLabel);

        hpLabelNumber = new JLabel();
        hpLabelNumber.setFont(normalFont);
        hpLabelNumber.setForeground(Color.WHITE);
        playerPanel.add(hpLabelNumber);

        mpLabel = new JLabel("MP:");
        mpLabel.setFont(normalFont);
        mpLabel.setForeground(Color.WHITE);
        playerPanel.add(mpLabel);

        mpLabelNumber = new JLabel();
        mpLabelNumber.setFont(normalFont);
        mpLabelNumber.setForeground(Color.WHITE);
        playerPanel.add(mpLabelNumber);
    }
}