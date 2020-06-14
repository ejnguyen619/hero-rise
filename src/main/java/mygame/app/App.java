package mygame.app;

import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;

public class App 
{
    // System objects
    Scanner sc;
    Random rand;

    // GUI objects
    JFrame window;
    JPanel titleScreenPanel, startButtonPanel;
    JLabel titleNameLabel;
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 90);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    JButton startButton;

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
    
    public static void main( String[] args )
    {
        // Start adventure
        App game = new App();
        // while(true){
        //     game.setup();
        //     game.start();
        //     if(game.gameOver() == 2) break;
        // }
        // game.end();
    }

    public App(){
        // Create window for game
        window = new JFrame();

        // Create title screen
        titleScreenPanel = new JPanel();
        titleScreenPanel.setBounds(100, 100, 600, 150);
        titleScreenPanel.setBackground(Color.BLACK);

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

        titleScreenPanel.add(titleNameLabel);
        startButtonPanel.add(startButton);

        window.add(titleScreenPanel);
        window.add(startButtonPanel);

        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);
        window.setVisible(true);
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
        etherPotionDropChance = 20;
        score = 0;
    }

    // Game text output
    public void start(){

        // Enter username
        name();

        GAME:
        while(true){
            System.out.println("----------------------------------------");

            // Determine enemy health and monster type
            enemyType();

            // Apply bonus stats to modifier based on type of enemy
            bonus();

            // Generate enemy HP
            generateEnemyHP();

            // Fight information and player choices
            while(enemyHealth > 0 && health > 0){
                fightInfo();

                String input = sc.nextLine();

                // Damage calculation
                if(input.equals("1")){
                    attack();
                }
                // Opens skills page
                else if(input.equals("2")){
                    skillScreen();
                }
                // Opens magic page
                else if(input.equals("3")){
                    magicScreen();
                } 
                // Opens items page
                else if(input.equals("4")){
                    itemScreen();
                }
                // Check descriptions of skills and items
                else if(input.equals("5")){
                    help();
                }
                // Leave current battle
                else if(input.equals("6")){
                    if(run()) continue GAME;
                }                 
                // Error check for input
                else {
                    System.out.println("\tInvalid command!");
                }
            }

            // Player run out of health
            if(playerAlive() == false) break;

            // Fight result
            fightResult();

            // Items drop
            healthDrop();
            manaDrop();

            // Decide action after fight conclusion
            if(nextAction() == 2) break;
        }
    }

    // Enter username
    public void name(){
        System.out.println("\nPlease enter your name: ");
        name = sc.nextLine();
        System.out.println("Welcome to the Dungeon " + name + "!");
    }

    // Determine enemy health and monster type
    public void enemyType(){
        if(numEnemiesDefeated % 5 == 0 && numEnemiesDefeated > 0) enemy = "Dragon";
        else enemy = enemies[rand.nextInt(enemies.length)];
        System.out.println("\t# " + enemy + " has appeared! #\n");
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

    // Fight screen
    public void fightInfo(){
        System.out.println("\t" + name);
        System.out.println("\tHP: " + health + "/" + maxHealth);
        System.out.println("\tMP: " + mana + "/" + maxMana);
        System.out.println("\n\t" + enemy);
        System.out.println("\tHP: " + enemyHealth + "/" + fullEnemyHealth);
        System.out.println("\n\tWhat would you like to do?");
        System.out.println("\t1. Attack");
        System.out.println("\t2. Skills");
        System.out.println("\t3. Magic");
        System.out.println("\t4. Items");
        System.out.println("\t5. Help");
        System.out.println("\t6. Run");
    }

    // Skills screen
    public void skillScreen(){
        String input;
        while(true) {
            System.out.println("----------------------------------------");
            System.out.println("\tSkills:");
            System.out.println("\t1. Guardian Strike   5MP");
            System.out.println("\t2. Counter Force     5MP");
            System.out.println("\t3. Cancel");
            input = sc.nextLine();
            if(input.equals("1")){
                defend();
                break;
            } else if(input.equals("2")){
                counter();
                break;   
            } else if(input.equals("3")){
                System.out.println("----------------------------------------");
                break;   
            }  
            else {
                System.out.println("Invalid command!");
            }
        }
    }

    // Magic screen
    public void magicScreen(){
        if(numEnemiesDefeated < 6){
            System.out.println("You do not have any magic!");
            return;
        }
        String input;
        while(true) {
            System.out.println("----------------------------------------");
            System.out.println("\tSpells:");
            System.out.println("\t1. Fire:            10MP");
            System.out.println("\t2. Cancel");
            input = sc.nextLine();
            if(input.equals("1")){
                fire();
                break;
            } else if(input.equals("2")){
                System.out.println("----------------------------------------");
                break;   
            } 
            else {
                System.out.println("Invalid command!");
            }
        }
    }

    // Items screen
    public void itemScreen(){
        String input;
        while(true) {
            System.out.println("----------------------------------------");
            System.out.println("\tItems:");
            System.out.println("\t1. Health potion:   " + "x" + numHealthPotions);
            System.out.println("\t2. Ether potion:    " + "x" + numEtherPotions);
            System.out.println("\t3. Cancel");
            input = sc.nextLine();
            if(input.equals("1")){
                healthPotion();
                break;
            } else if(input.equals("2")){
                etherPotion();
                break;
            }    
            else if(input.equals("3")){
                System.out.println("----------------------------------------");
                break;   
            } 
            else {
                System.out.println("Invalid command!");
            }
        }
    }

    // Check descriptions of skills and items
    public void help(){
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\tSkills:");
        System.out.println("\t1. Guardian Strike:");
        System.out.println("\t   Enemy attacks first. Player receives half damage. Player attack hits.");
        System.out.println("\t2. Counter Force:");
        System.out.println("\t   Player dodge rate up. Enemy attacks first. If enemy attack misses, player performs critical hit.");
        System.out.println("\n\tItems:");
        System.out.println("\t1. Health Potion:");
        System.out.println("\t   Recovers 30HP.");
        System.out.println("\t2. Mana Potion:");
        System.out.println("\t   Recovers 25MP.");
        System.out.println("\n\tMagic:");
        System.out.println("\t1. Fire:");
        System.out.println("\t   Inflicts 25 damage to enemy.");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
    }

    // Generate damage value
    public int damage(int baseDamage, int bonus, int min){
        int damage = 0;
        while(damage < min) damage = rand.nextInt(baseDamage) + bonus;
        return damage;
    }

    // Dodge calculation
    public boolean dodge (int dodgeRate){
        if(rand.nextInt(100) < dodgeRate) {
            System.out.println("\t> MISS!");
            return true;
        } 
        else return false;
    }

    // Critical calculation
    public boolean critical (int criticalRate){
        if(rand.nextInt(100) < criticalRate) {
            System.out.println("\t> CRITICAL HIT!");
            return true;
        } 
        else return false;
    }

    // Damage enemy
    public void damageEnemy(int damage){
        System.out.println("\t> You strike the " + enemy + " for " + damage + " damage.");
        enemyHealth -= damage;
    }

    // Receive damage from enemy
    public void takeDamage(int damage){
        System.out.println("\t> The " + enemy + " dealt you " + damage + " damage.");
        health -= damage;
    }

    // Check if player has no health
    public boolean playerAlive(){
        if(health < 1) {
            System.out.println("\t> You have taken too much damage. You are too weak to go on!");
            System.out.println("----------------------------------------");
            System.out.println("You limp out of the dungeon, weak from battle.");
            score -= 50;
            return false;
        }
        else return true; 
    }

    // Check if enemy has no health
    public boolean enemyAlive(){
        if(enemyHealth < 1) {
            System.out.println("----------------------------------------");
            System.out.println(" # " + enemy + " was defeated! # ");
            return false;
        }
        else return true; 
    }

    // Charge up dragon's super move
    public boolean fireCharge(){
        if(rand.nextInt(100) < 30) {
            System.out.println("\t> Dragon is storing power!");
            deadlyAttack = true;
            return true;
        } 
        else return false;
    }

    // Dragon uses super move
    public void fireBlast(int damage){
        // Damage inflicted to player calculation
        takeDamage(damage);
        deadlyAttack = false;       
    }

    // Checks enemy action
    public void enemyAction(int damage, int dodge, int crit, boolean half){
        // Check for super move
        if(enemy.equals("Dragon")){
            if(deadlyAttack == false){
                if(fireCharge() == false){
                    // Damage inflicted to player calculation
                    if(dodge(dodge)) damage = 0;
                    else if(critical(crit)) damage *= 1.5;
                    takeDamage(damage);
                }
            } else {
                System.out.println("\t> Dragon unleashes a deadly fire blast!");
                if(dodge(dodge)) damage = 0;
                else fireBlast((!half) ? 50 : 25);
            }
        } else {
            // Damage inflicted to player calculation
            if(dodge(dodge)) damage = 0;
            else if(critical(crit)) damage *= 1.5;
            takeDamage(damage);           
        }
    }

    // Attack enemy
    public void attack(){
        // Generate damage values for player and enemy     
        int damageDealt = damage(attackDamage, 0, 1);
        int damageTaken = damage(enemyAttackDamage, attackBonus, minAttack);

        // Damage inflicted to enemy calculation
        if(dodge(enemyDodgeChance + dodgeBonus)) damageDealt = 0;
        else if(critical(criticalChance)) damageDealt *= 1.5;
        damageEnemy(damageDealt);

        if(!enemyAlive()) return;

        enemyAction(damageTaken, dodgeChance, enemyCritChance + critBonus, false);
    }

    // Player uses Guardian Force
    public void defend(){
        if(mana < 5) {
            System.out.println("\t> Insufficient mana! ");
            return;
        }
        System.out.println("\t> You used Guardian Strike! ");
        System.out.println("\t> You have taken a defensive stance.");
        System.out.println("\t> Your defense goes up!");
        mana -= 5;

        // Generate damage values for player and enemy     
        int damageDealt = damage(attackDamage, 0, 1);
        int damageTaken = damage(enemyAttackDamage, attackBonus, minAttack)/2;

        enemyAction(damageTaken, 0, 0, true);

        if(!playerAlive()) return;

        // Damage inflicted to enemy calculation
        damageEnemy(damageDealt);
    }

    // Player uses Counter Force
    public void counter(){
        if(mana < 5) {
            System.out.println("\t> Insufficient mana! ");
            return;
        }
        System.out.println("\t> You used Counter Force! ");
        System.out.println("\t> You have taken an evasive stance.");
        System.out.println("\t> Your critical rate goes up!");
        mana -= 5;

        // Generate damage values for player and enemy     
        int damageDealt = damage(attackDamage, 0, 1);
        int damageTaken = damage(enemyAttackDamage, attackBonus, minAttack);

        enemyAction(damageTaken, dodgeChance*3, enemyCritChance + critBonus, false);

        if(!playerAlive()) return;

        // Damage inflicted to enemy calculation
        if(dodge(enemyDodgeChance + dodgeBonus)) damageDealt = 0;
        else if(damageTaken == 0) {
            damageDealt *= 1.5;
            System.out.println("\t> CRITICAL HIT!");
        } else if(critical(criticalChance)) damageDealt *= 1.5;
        damageEnemy(damageDealt);
    }

    // Casts fire spell
    public void fire(){
        if(mana < 10) {
            System.out.println("\t> Insufficient mana! ");
            return;
        }
        System.out.println("\t> You cast Fire! ");
        mana -= 10;

        // Generate damage values for player and enemy     
        int damageDealt = 25;
        int damageTaken = damage(enemyAttackDamage, attackBonus, minAttack);

        // Damage inflicted to enemy calculation
        damageEnemy(damageDealt);

        if(!enemyAlive()) return;

        enemyAction(damageTaken, dodgeChance, enemyCritChance + critBonus, false);      
    }

    // Drink health potion
    public void healthPotion(){
        if(health == maxHealth) {
            System.out.println("\t> You have max HP!");
            return;
        }
        if(numHealthPotions > 0){
            int difference = 0;
            if(health > maxHealth - healthPotionHealAmount){
                difference = maxHealth - health;
                health = maxHealth;
            } else {
                health += healthPotionHealAmount;
            }
            numHealthPotions--;
            System.out.println("\t> You drink a health potion, healing yourself for " + ((difference > 0) ? difference : healthPotionHealAmount) + ".");
            System.out.println("\t> You now have " + health + " HP.");
            System.out.println("\t> You have " + numHealthPotions + " health potions left.");
        } else {
            System.out.println("\t> You have no health potions! Defeat enemies for a chance to get one!");
        }
    }

    // Drink ether potion
    public void etherPotion(){
        if(mana == maxMana) {
            System.out.println("\t> You have max MP!");
            return;
        }
        if(numEtherPotions > 0){
            int difference = 0;
            if(mana > maxMana - etherPotionHealAmount){
                difference = maxMana - mana;
                mana = maxMana;
            } else {
                mana += etherPotionHealAmount;
            }
            numEtherPotions--;
            System.out.println("\t> You drink a mana potion, recovering " + ((difference > 0) ? difference : etherPotionHealAmount) + " mana.");
            System.out.println("\t> You now have " + mana + " MP.");
            System.out.println("\t> You have " + numEtherPotions + " mana potions left.");
        } else {
            System.out.println("\t> You have no mana potions! Defeat enemies for a chance to get one!");
        }
    }

    // Run away from battle
    public boolean run(){
        String input;
        boolean runAway = false;
        if(enemy.equals("Dragon")){
            System.out.println("You cannot run away from this battle!");
            return runAway;
        }
        if(numRunAttempts > 0){
            while(true){
                System.out.println("\tYou have " + numRunAttempts + " runs remaining.");
                System.out.println("\tAre you sure that you want to run away from this battle?");
                System.out.println("\t1. Yes");
                System.out.println("\t2. No");
                input = sc.nextLine();
                if(input.equals("1")){
                    numRunAttempts--;
                    System.out.println("\t> You ran away from the " + enemy + "!");
                    System.out.println("\t> You have " + numRunAttempts + " runs remaining.");
                    runAway = true;
                    break;
                } else if(input.equals("2")){
                    System.out.println("----------------------------------------");
                    break;   
                } 
                else {
                    System.out.println("Invalid command!");
                }
            }
        } else {
            System.out.println("\tYou have no runs! Defeat enemies to get more!");
        }
        return runAway;
    }

    // Fight result
    public void fightResult(){
        numEnemiesDefeated++;
        System.out.print(" # You have defeated " + numEnemiesDefeated + " "); 
        System.out.println((numEnemiesDefeated == 1) ? "enemy so far! # " : "enemies so far! # ");

        // Score calculation
        if(enemy.equals("Dragon")) score += 200;
        else score += 100;

        if(numEnemiesDefeated == 6){
            System.out.println(" # The dragon's aura filled you with power beyond your understanding. # ");
            System.out.println(" # You have gained the ability to use magic! # ");
            System.out.println(" # You have learned the magic spell Fire! # ");
        }
        System.out.println(" # You have " + health + " HP left. #");
        if(numEnemiesDefeated % 5 == 0) {
            System.out.println("\n # You have gained a run attempt! #");
            numRunAttempts++;
            System.out.println(" # You have " + numRunAttempts + " runs remaining. #");
            difficulty();
        }
        if(numEnemiesDefeated % 10 == 0) {
            System.out.println(" # Your HP and MP has been completely restored! #");
            health = maxHealth;
            mana = maxMana;
        }
    }

    // Increase difficulty as more enemies are defeated
    public void difficulty(){
        System.out.println(" # The enemies are getting stronger. Beware! #");
        maxEnemyHealth += 5;
        enemyAttackDamage += 5;
        enemyCritChance += 5;
        minHealth += 5;
        minAttack += 2;
    }

    // Health potion drop
    public void healthDrop(){
        if(rand.nextInt(100) < healthPotionDropChance){
            numHealthPotions++;
            System.out.println("\n # The " + enemy + " dropped a health potion! #");
            System.out.println(" # You now have " + numHealthPotions + " health potion(s). # ");
        }
    }

    // Mana potion drop
    public void manaDrop(){
        if(rand.nextInt(100) < etherPotionDropChance){
            numEtherPotions++;
            System.out.println("\n # The " + enemy + " dropped a mana potion! #");
            System.out.println(" # You now have " + numEtherPotions + " mana potion(s). # ");
        }
    } 

    // After combat options
    public int nextAction(){

        // Error check for input
        String input;
        while(true){
            System.out.println("----------------------------------------");
            System.out.println("What would you like to do now?");
            System.out.println("1. Continue fighting");
            System.out.println("2. Exit dungeon");
            input = sc.nextLine();
            if(input.equals("1")){
                System.out.println("You continue on your adventure!");
                break;
            } else if(input.equals("2")){
                System.out.println("----------------------------------------");
                System.out.println("You exit the dungeon, successful from your adventure!");
                break;   
            } else {
                System.out.println("Invalid command!");
            }
        }
        return Integer.parseInt(input);
    }
     
    // Game over screen
    public int gameOver(){

        // Error check for input
        System.out.println("\nTotal Enemies " + name + " Defeated: " + numEnemiesDefeated);
        System.out.println("Your total score is " + score);
        String input;
        while(true){
            System.out.println("\nGAME OVER! What would you like to do?");
            System.out.println("1. Try again");
            System.out.println("2. Exit game");
            input = sc.nextLine();
            if(input.equals("1")){
                System.out.println("It's go time!");
                break;
            } else if(input.equals("2")){
                break;   
            } else {
                System.out.println("Invalid command!");
            }
        }
        return Integer.parseInt(input);
    }

    // Exit game
    public void end(){
        System.out.println("\n#######################");
        System.out.println("# THANKS FOR PLAYING! #");
        System.out.println("#######################");

        sc.close();
        System.exit(0);
    }
}
