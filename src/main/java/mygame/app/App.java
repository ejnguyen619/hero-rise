package mygame.app;

import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.awt.*;

public class App 
{
    // System objects
    Scanner sc;
    Random rand;

    // GUI objects
    JFrame window;
    JPanel titleScreenPanel, startButtonPanel, mainTextPanel, choiceButtonPanel, playerPanel;
    JLabel titleNameLabel, hpLabel, hpLabelNumber, mpLabel, mpLabelNumber;
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 90);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    JButton startButton, choice1, choice2, choice3, choice4, choice5, choice6;
    JTextArea mainTextArea;
    TitleScreenHandler tsHandler = new TitleScreenHandler();
    ChoiceHandler choiceHandler = new ChoiceHandler();

    // Enemy variables
    String[] enemies = {"Skeleton", "Zombie", "Warrior", "Assassin"};
    int maxEnemyHealth;
    int enemyAttackDamage;
    int enemyHealth;
    String enemy;
    int enemyCritChance;
    int enemyDodgeChance;
    int minHealth;
    int minAttack;
    int attackBonus;
    int critBonus;
    int dodgeBonus;
    int healthBonus;
    int fullEnemyHealth;
    boolean deadlyAttack;
    boolean enemyHit;
    boolean enemyCrit;

    // Player variables
    int maxHealth;
    int maxMana;
    int health;
    int mana;
    int attackDamage;
    int numHealthPotions;
    int healthPotionHealAmount;
    int healthPotionDropChance; // Percentage
    int numEnemiesDefeated;
    int numRunAttempts;
    int criticalChance;
    int dodgeChance;
    int numEtherPotions;
    int etherPotionHealAmount;
    int etherPotionDropChance;
    String name;
    int score;
    String action;
    boolean hit;
    boolean crit;
    String playerTurnOrder;
    String skill;
    
    public static void main( String[] args )
    {
        // Start adventure
        App game = new App();
    }

    public App(){
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
        startButton.addActionListener(tsHandler);

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

    public void createGameScreen(){
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

        choice1 = new JButton("Choice 1");
        choice1.setBackground(Color.BLACK);
        choice1.setForeground(Color.WHITE);
        choice1.setFont(normalFont);
        choice1.setFocusPainted(false);
        choice1.addActionListener(choiceHandler);
        choice1.setActionCommand("c1");
        choiceButtonPanel.add(choice1);

        choice2 = new JButton("Choice 2");
        choice2.setBackground(Color.BLACK);
        choice2.setForeground(Color.WHITE);
        choice2.setFont(normalFont);
        choice2.setFocusPainted(false);
        choice2.addActionListener(choiceHandler);
        choice2.setActionCommand("c2");
        choiceButtonPanel.add(choice2);

        choice3 = new JButton("Choice 3");
        choice3.setBackground(Color.BLACK);
        choice3.setForeground(Color.WHITE);
        choice3.setFont(normalFont);
        choice3.setFocusPainted(false);
        choice3.addActionListener(choiceHandler);
        choice3.setActionCommand("c3");
        choiceButtonPanel.add(choice3);

        choice4 = new JButton("Choice 4");
        choice4.setBackground(Color.BLACK);
        choice4.setForeground(Color.WHITE);
        choice4.setFont(normalFont);
        choice4.setFocusPainted(false);
        choice4.addActionListener(choiceHandler);
        choice4.setActionCommand("c4");
        choiceButtonPanel.add(choice4);

        choice5 = new JButton("Choice 5");
        choice5.setBackground(Color.BLACK);
        choice5.setForeground(Color.WHITE);
        choice5.setFont(normalFont);
        choice5.setFocusPainted(false);
        choice5.addActionListener(choiceHandler);
        choice5.setActionCommand("c5");
        choiceButtonPanel.add(choice5);

        choice6 = new JButton("Choice 6");
        choice6.setBackground(Color.BLACK);
        choice6.setForeground(Color.WHITE);
        choice6.setFont(normalFont);
        choice6.setFocusPainted(false);
        choice6.addActionListener(choiceHandler);
        choice6.setActionCommand("c6");
        choiceButtonPanel.add(choice6);

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

        playerSetup();
    }

    public void playerSetup(){
        setup();
        hpLabelNumber.setText("" + health + "/" + maxHealth);
        mpLabelNumber.setText("" + mana + "/" + maxMana);
        encounter();
    }

    public void encounter(){
        action = "encounter";
        enemyType();
        bonus();
        generateEnemyHP();
        choice1.setText("Fight");
        choice2.setText("Run");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    // Fight Screen
    public void fightInfo(){
        action = "fight";
        mainTextArea.setFont(normalFont);
        mainTextArea.setText(enemy + " HP: " + enemyHealth + "/" + fullEnemyHealth + "\n\nWhat would you like to do?");
        choice1.setText("Attack");
        choice2.setText("Skills");
        choice3.setText("Magic");
        choice4.setText("Items");
        choice5.setText("Help");
        choice6.setText("Run");
    }

    // Attack enemy
    public void attack(){
        action = "attack";
        playerTurnOrder = "first";
        choice1.setText(">");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");

        // Generate damage values for player     
        int damageDealt = damage(attackDamage, 0, 1);

        // Damage inflicted to enemy calculation
        if(dodge(enemyDodgeChance + dodgeBonus)) {
            hit = false;
            crit = false;
            damageDealt = 0;
        }
        else if(critical(criticalChance)) {
            hit = true;
            crit = true;
            damageDealt *= 1.5;
        } else {
            hit = true;
            crit = false;
        }
        damageEnemy(damageDealt);
    }

    // Skills screen
    public void skillScreen(){
        action = "skills";
        mainTextArea.setText("Guardian Strike:            5MP\nCounter Force:              5MP");
        choice1.setText("Guardian Strike");
        choice2.setText("Counter Force");
        choice3.setText("Cancel");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    // Magic screen
    public void magicScreen(){
        action = "magic";
        mainTextArea.setText("Fire:            10MP");
        choice1.setText("Fire");
        choice2.setText("Cancel");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    // Items screen
    public void itemScreen(){
        action = "items";
        mainTextArea.setText("Health potion:   " + "x" + numHealthPotions + "\nEther potion:    " + "x" + numEtherPotions);
        choice1.setText("Health Potion");
        choice2.setText("Mana Potion");
        choice3.setText("Cancel");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    // Check descriptions of skills and items
    public void help(int category){
        action = "help";
        mainTextArea.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        switch(category){
            case 0:
                mainTextArea.setText("");
                break;
            case 1:
                String guardian = "Guardian Strike:\nEnemy attacks first. Player receives half damage. Player attack hits.\n";
                String counter = "Counter Force:\nPlayer dodge rate up. Enemy attacks first. If enemy attack misses, player performs critical hit.";
                mainTextArea.setText(guardian + "\n" + counter);
                break;
            case 2:
                String health = "Health Potion:\nRecovers 30HP.\n";
                String mana = "Mana Potion:\nRecovers 25MP.";
                mainTextArea.setText(health + "\n" + mana);
                break;
            case 3:
                String fire = "Fire:\nInflicts 25 damage to enemy.";
                mainTextArea.setText(fire);
                break;
        }
    
        choice1.setText("Skills");
        choice2.setText("Items");
        choice3.setText("Magic");
        choice4.setText("Cancel");
        choice5.setText("");
        choice6.setText("");
    }

    //-------------------------------------------------------------------------------------------------------------------------------------

    // Player uses Guardian Force
    public void defend(int turn){
        action = "defend";
        choice1.setText(">");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");

        // Prepare for counter attack
        if(turn == 0){
            if(mana < 5) {
                return;
            }
            playerTurnOrder = "second";
            skill = "guardian";
            mainTextArea.setText("You used Guardian Strike!\n\nYou have taken a defensive stance.");
            mana -= 5;
            mpLabelNumber.setText("" + mana + "/" + maxMana);
        }
        // Attack after enemy attack 
        else if(turn == 1){
            int damageDealt = damage(attackDamage, 0, 1);
            hit = true;
            crit = false;
            damageEnemy(damageDealt);
            playerTurnOrder = "done";
        }
    }

    // Player uses Counter Force
    public void counter(int turn){
        action = "counter";
        choice1.setText(">");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");

        // Prepare for counter attack
        if(turn == 0){
            if(mana < 5) {
                return;
            }
            playerTurnOrder = "second";
            skill = "counter";
            mainTextArea.setText("You used Counter Force!\n\nYou have taken an evasive stance.");
            mana -= 5;
            mpLabelNumber.setText("" + mana + "/" + maxMana);
        }
        // Attack after enemy attack 
        else if(turn == 1){
            int damageDealt = damage(attackDamage, 0, 1);
            if(dodge(enemyDodgeChance + dodgeBonus)){
                 damageDealt = 0;
                 hit = false;
                 crit = false;
            }
            else if(!enemyHit || critical(criticalChance)){
                damageDealt *= 1.5;
                hit = true;
                crit = true;
            } else {
                hit = true;
                crit = false;
            }
            damageEnemy(damageDealt);
            playerTurnOrder = "done";
        }
    }

    // Casts fire spell
    public void fire(){
        if(mana < 10) {
            return;
        }

        action = "attack";
        playerTurnOrder = "first";
        choice1.setText(">");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");

        // Generate damage values for player     
        int damageDealt = 25;
        hit = true;
        crit = false;
        mana -= 10;
        mpLabelNumber.setText("" + mana + "/" + maxMana);

        // Damage inflicted to enemy calculation
        enemyHealth -= damageDealt;
        mainTextArea.setText("You cast Fire!\n\nYou strike the " + enemy + " for " + damageDealt + " damage.");
    }

    // Drink health potion to recover HP
    public void healthPotion(){
        if(health == maxHealth) {
            return;
        }
        if(numHealthPotions > 0){
            if(health > maxHealth - healthPotionHealAmount){
                health = maxHealth;
            } else {
                health += healthPotionHealAmount;
            }
            numHealthPotions--;
            mainTextArea.setText("Health potion:   " + "x" + numHealthPotions + "\nEther potion:    " + "x" + numEtherPotions);
            hpLabelNumber.setText("" + health + "/" + maxHealth);
        }
    }

    // Drink ether potion to recover MP
    public void etherPotion(){
        if(mana == maxMana) {
            return;
        }
        if(numEtherPotions > 0){
            if(mana > maxMana - etherPotionHealAmount){
                mana = maxMana;
            } else {
                mana += etherPotionHealAmount;
            }
            numEtherPotions--;
            mainTextArea.setText("Health potion:   " + "x" + numHealthPotions + "\nEther potion:    " + "x" + numEtherPotions);
            mpLabelNumber.setText("" + mana + "/" + maxMana);
        }
    }

    // Enemy action after player attack
    public void enemyAction(boolean half, int dodge, int crit){
        action = "enemy";
        choice1.setText(">");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
        
        // Generate damage values for enemy
        int damageTaken = damage(enemyAttackDamage, attackBonus, minAttack);
        
        // Damage inflicted to enemy calculation
        if(half) damageTaken /= 2;
        enemyAction(damageTaken, dodgeChance, enemyCritChance + critBonus, half);
    }

    // Checks enemy action
    public void enemyAction(int damage, int dodge, int crit, boolean half){
        // Check for super move
        if(enemy.equals("Dragon")){
            if(deadlyAttack == false){
                if(fireCharge() == false){
                    // Damage inflicted to player calculation
                    enemyAttack(damage, dodge, crit);
                }
            } else {
                enemyAttack((!half) ? 50 : 25, dodge, 0);
                deadlyAttack = false;  
            }
        } else {
            // Damage inflicted to player calculation
            enemyAttack(damage, dodge, crit);          
        }
    }

    // Player Dead Screen
    public void playerDead(){
        action = "lose";
        mainTextArea.setText("You limp out of the dungeon, weak from battle. \n\nGAME OVER!\n\nWhat would you like to do?");
        choice1.setText("Try again!");
        choice2.setText("Exit game");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
        // choice3.setVisible(false);
        // choice4.setVisible(false);
        // choice5.setVisible(false);
        // choice6.setVisible(false);
    }

    // Exit game
    public void end(){
        action = "end";
        score -= 50;
        mainTextArea.setText("Final Score: " + score + "\n\nTHANKS FOR PLAYING!");
        choice1.setText("");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
        choiceButtonPanel.setVisible(false);
    }

    // Enemy dead
    public void enemyDead(){
        action = "win";
        String output = "";

        // Score calculation
        if(enemy.equals("Dragon")) score += 200;
        else score += 100;

        numEnemiesDefeated++;
        output = (numEnemiesDefeated == 6) ? output + "\nYou have learned the magic spell Fire!" : output;
        if(numEnemiesDefeated % 5 == 0) {
            difficulty();
        }
        if(numEnemiesDefeated % 10 == 0) {
            output += "\nYour HP and MP has been completely restored!";
            health = maxHealth;
            mana = maxMana;
            hpLabelNumber.setText("" + health + "/" + maxHealth);
            mpLabelNumber.setText("" + mana + "/" + maxMana); 
        }

        mainTextArea.setText(enemy + " was defeated!\n" + output);
        choice1.setText("Result");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    // Fight result
    public void fightResult(){
        action = "result";

        String healthPotion = (healthDrop()) ? ("The " + enemy + " dropped a health potion!\n\n") : "";
        String manaPotion = (manaDrop()) ? ("The " + enemy + " dropped a mana potion!\n\n") : "";
        String potion = (!healthPotion.equals("") && !manaPotion.equals("")) ? ("The " + enemy + " dropped a health potion!\n\nThe " + enemy + " dropped a mana potion!\n\n") : (healthPotion + manaPotion);
        mainTextArea.setText(potion + "What would you like to do now? ");

        choice1.setText("Continue fighting");
        choice2.setText("Exit dungeon");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    public class TitleScreenHandler implements ActionListener{

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            createGameScreen();
        }
    }

    public class ChoiceHandler implements ActionListener{

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            String yourChoice = e.getActionCommand();
            switch(action){
                case "encounter":
                    switch(yourChoice){
                        case "c1": fightInfo(); break;
                        case "c2":
                            if(enemy != "Dragon") encounter(); 
                            break;
                    }
                    break;
                case "fight":
                    switch(yourChoice){
                        case "c1": attack(); break;
                        case "c2": skillScreen(); break;
                        case "c3":
                            if(numEnemiesDefeated >= 6) magicScreen(); 
                            break;
                        case "c4": itemScreen(); break;
                        case "c5": help(0); break;
                        case "c6": encounter(); break;
                    }
                    break;
                case "attack":
                    switch(yourChoice){
                        case "c1": 
                            if(enemyHealth < 1) enemyDead();
                            else enemyAction(false, dodgeChance, enemyCritChance + critBonus);
                            break;
                    }
                    break;
                case "skills":
                    switch(yourChoice){
                        case "c1": defend(0); break;
                        case "c2": counter(0); break;
                        case "c3": fightInfo(); break;                        
                    }
                    break;
                case "items":
                    switch(yourChoice){
                        case "c1": healthPotion(); break;
                        case "c2": etherPotion(); break;
                        case "c3": fightInfo(); break;                        
                    }
                    break; 
                case "help":
                    switch(yourChoice){
                        case "c1": help(1); break;
                        case "c2": help(2); break;
                        case "c3": help(3); break;
                        case "c4": fightInfo(); break;
                    }
                    break;
                case "magic":
                    switch(yourChoice){
                        case "c1": fire(); break;
                        case "c2": fightInfo(); break;
                    }
                    break;
                case "defend":
                    switch(yourChoice){
                        case "c1":
                            if(playerTurnOrder == "second") enemyAction(true, 0, 0);
                            else if(playerTurnOrder == "done"){
                                if(enemyHealth < 1) enemyDead();
                                else fightInfo();
                            } 
                            break;
                    }
                    break;
                case "counter":
                    switch(yourChoice){
                        case "c1":
                            if(playerTurnOrder == "second") enemyAction(false, dodgeChance*3, enemyCritChance + critBonus);
                            else if(playerTurnOrder == "done"){
                                if(enemyHealth < 1) enemyDead();
                                else fightInfo();
                            } 
                            break;
                    }
                    break;   
                case "enemy":
                    switch(yourChoice){
                        case "c1": 
                            if(health < 1) playerDead();
                            else {
                                if(playerTurnOrder == "first") fightInfo();
                                else {
                                    if(skill == "guardian") defend(1);
                                    else if(skill == "counter") counter(1);
                                }
                            }
                            break;
                    }
                    break;
                case "lose":
                    switch(yourChoice){
                        case "c1": playerSetup(); break;
                        case "c2": end(); break;
                    }
                    break;
                case "win":
                    switch(yourChoice){
                        case "c1": fightResult(); break;
                    }
                    break;
                case "result":
                    switch(yourChoice){
                        case "c1": encounter(); break;
                        case "c2": end(); break;
                    }
                    break;              
            }
        }
    }

    // Initialize variables
    public void setup(){
        // System objects
        sc = new Scanner(System.in);
        rand = new Random();

        // Enemy variables
        maxEnemyHealth = 75;
        enemyAttackDamage = 25;
        enemyCritChance = 10;
        enemyDodgeChance = 10;
        minHealth = 10;
        minAttack = 1;
        deadlyAttack = false;
        enemyHit = false;
        enemyCrit = false;

        // Player variables
        health = 100;
        attackDamage = 50;
        numHealthPotions = 3;
        healthPotionHealAmount = 30;
        healthPotionDropChance = 30; // Percentage
        numEnemiesDefeated = 0;
        numRunAttempts = 3;
        criticalChance = 15;
        dodgeChance = 15;
        mana = 50;
        maxHealth = health;
        maxMana = mana;
        numEtherPotions = 1;
        etherPotionHealAmount = 25;
        etherPotionDropChance = 20; // Percentage
        score = 0;
        hit = false;
        crit = false;
    }

    // Determine enemy health and monster type
    public void enemyType(){
        if(numEnemiesDefeated % 5 == 0 && numEnemiesDefeated > 0) enemy = "Dragon";
        else enemy = enemies[rand.nextInt(enemies.length)];
        String pronoun = (enemy == "Assassin") ? "An" : "A";
        String runAway = (enemy == "Dragon") ? "\n\nYOU CANNOT RUN AWAY FROM THIS FIGHT!" : "";
        mainTextArea.setText(pronoun + " " + enemy + " has appeared!" + runAway);
    }

    // Apply bonuses based on type of enemy
    public void bonus(){
        if(enemy.equals("Zombie")){
            healthBonus = 5;
            attackBonus = -5;
            dodgeBonus = -5;
        }
        else if(enemy.equals("Warrior")){ 
            healthBonus = 10;
            attackBonus = 10;
            critBonus = -10;
            dodgeBonus = -10;  
        }
        else if(enemy.equals("Assassin")) { 
            healthBonus = -10;
            attackBonus = -5;
            critBonus = 20;
            dodgeBonus = 30; 
        }
        else if(enemy.equals("Dragon")) { 
            healthBonus = 20;
            attackBonus = 20;
            critBonus = 10;
            dodgeBonus = 10; 
        }
        else {
            healthBonus = 0;
            attackBonus = 0;
            critBonus = 0;
            dodgeBonus = 0; 
        }
    }

    // Generate enemy health
    public void generateEnemyHP(){
        enemyHealth = 0;
        while(enemyHealth < minHealth) enemyHealth = rand.nextInt(maxEnemyHealth) + healthBonus;
        fullEnemyHealth = enemyHealth;
    }

    // Generate damage value
    public int damage(int baseDamage, int bonus, int min){
        int damage = 0;
        while(damage < min) damage = rand.nextInt(baseDamage) + bonus;
        return damage;
    }

    // Dodge calculation
    public boolean dodge (int dodgeRate){
        if(rand.nextInt(100) < dodgeRate) return true;
        else return false;
    }

    // Critical calculation
    public boolean critical (int criticalRate){
        if(rand.nextInt(100) < criticalRate) return true;
        else return false;
    }

    // Damage enemy
    public void damageEnemy(int damage){
        enemyHealth -= damage;
        String hitEnemy = (hit)  ? "HIT!" : "MISS!";
        String critEnemy = (crit && hit) ? "\nCRITICAL!\n" : "";
        mainTextArea.setText(hitEnemy + "\n" + critEnemy + "\nYou strike the " + enemy + " for " + damage + " damage.");
    }

    // Receive damage from enemy
    public void takeDamage(int damage){
        health -= damage;
        String fireBlast = (enemy == "Dragon" && deadlyAttack) ? "Dragon unleashes a deadly fire blast!\n\n" : "";
        String hitByEnemy = (enemyHit)  ? "HIT!" : "MISS!";
        String critByEnemy = (enemyCrit && enemyHit) ? "\nCRITICAL!\n" : "";
        mainTextArea.setText(fireBlast + hitByEnemy + "\n" + critByEnemy + "\nThe " + enemy + " dealt you " + damage + " damage.");
        hpLabelNumber.setText("" + health + "/" + maxHealth);
    }

    // Health potion drop
    public boolean healthDrop(){
        if(rand.nextInt(100) < healthPotionDropChance){
            numHealthPotions++;
            return true;
        }
        return false;
    }

    // Mana potion drop
    public boolean manaDrop(){
        if(rand.nextInt(100) < etherPotionDropChance){
            numEtherPotions++;
            return true;
        }
        return false;
    }

    // Increase difficulty as more enemies are defeated
    public void difficulty(){
        maxEnemyHealth += 5;
        enemyAttackDamage += 5;
        enemyCritChance += 5;
        minHealth += 5;
        minAttack += 2;
    }

    // Charge up dragon's super move
    public boolean fireCharge(){
        if(rand.nextInt(100) < 30) {
            mainTextArea.setText("Dragon is storing power!");
            deadlyAttack = true;
            return true;
        } 
        else return false;
    }

    // Enemy Damage Calculation
    public void enemyAttack(int damage, int dodge, int crit){
        if(dodge(dodge)) {
            enemyHit = false;
            enemyCrit = false;
            damage = 0;
        }
        else if(critical(crit)){
            enemyHit = true;
            enemyCrit = true;
            damage *= 1.5;
        } else {
            enemyHit = true;
            enemyCrit = false;
        }
        takeDamage(damage);         
    }

    //-------------------------------------------------------------------------------------------------------------------------------------

    // Enter username
    public void name(){
        System.out.println("\nPlease enter your name: ");
        name = sc.nextLine();
        System.out.println("Welcome to the Dungeon " + name + "!");
    }
}
