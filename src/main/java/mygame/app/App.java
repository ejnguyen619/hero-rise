package mygame.app;

import java.util.Random;
import java.util.Scanner;

public class App 
{
    // System objects
    Scanner sc;
    Random rand;

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

    // Player variables
    int health;
    int attackDamage;
    int numHealthPotions;
    int healthPotionHealAmount;
    int healthPotionDropChance; // Percentage
    int numEnemiesDefeated;
    int numRunAttempts;
    int criticalChance;
    int dodgeChance;
    String name;
    
    public static void main( String[] args )
    {
        // Start adventure
        App game = new App();
        while(true){
            game.setup();
            game.start();
            if(game.gameOver() == 2) break;
        }
        game.end();
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
                // Defend from attack
                else if(input.equals("2")){
                    defend();
                } 
                // Counter attack
                else if(input.equals("3")){
                    counter();
                }
                // Consume health potion
                else if(input.equals("4")){
                    healthPotion();
                }
                // Leave current battle
                else if(input.equals("5")){
                    if(run() == true) continue GAME;
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

            // Health potion potential drop
            healthDrop();

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
        enemy = enemies[rand.nextInt(enemies.length)];
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
    }

    // Fight screen
    public void fightInfo(){
        System.out.println("\tYour HP: " + health);
        System.out.println("\t" + enemy + "'s HP: " + enemyHealth);
        System.out.println("\n\tWhat would you like to do?");
        System.out.println("\t1. Attack");
        System.out.println("\t2. Defend");
        System.out.println("\t3. Counter");
        System.out.println("\t4. Drink health potion");
        System.out.println("\t5. Run");
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
            System.out.println("You limp out of the dungeon, weak from battle.");
            return false;
        }
        else return true; 
    }

    // Check if enemy has no health
    public boolean enemyAlive(){
        if(enemyHealth < 1) {
            return false;
        }
        else return true; 
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

        // Damage inflicted to player calculation
        if(dodge(dodgeChance)) damageTaken = 0;
        else if(critical(enemyCritChance + critBonus)) damageTaken *= 1.5;
        takeDamage(damageTaken);
    }

    // Defend attack
    public void defend(){
        System.out.println("\t> You have taken a defensive stance.");

        // Generate damage values for player and enemy     
        int damageDealt = damage(attackDamage, 0, 1);
        int damageTaken = damage(enemyAttackDamage, attackBonus, minAttack)/2;

        // Damage inflicted to player calculation
        takeDamage(damageTaken);

        if(!playerAlive()) return;

        // Damage inflicted to enemy calculation
        damageEnemy(damageDealt);
    }

    // Dodge and counter
    public void counter(){
        System.out.println("\t> You have taken an evasive stance.");

        // Generate damage values for player and enemy     
        int damageDealt = damage(attackDamage, 0, 1);
        int damageTaken = damage(enemyAttackDamage, attackBonus, minAttack);

        // Damage inflicted to player calculation
        if(dodge(dodgeChance*3)) damageTaken = 0;
        else if(critical(enemyCritChance + critBonus)) damageTaken *= 1.5;
        takeDamage(damageTaken);

        if(!playerAlive()) return;

        // Damage inflicted to enemy calculation
        if(dodge(enemyDodgeChance + dodgeBonus)) damageDealt = 0;
        else if(damageTaken == 0) {
            damageDealt *= 1.5;
            System.out.println("\t> CRITICAL HIT!");
        } else if(critical(criticalChance)) damageDealt *= 1.5;
        damageEnemy(damageDealt);
    }

    // Drink health potion
    public void healthPotion(){
        if(health == 100) {
            System.out.println("\t> You are in max health!");
            return;
        }
        if(numHealthPotions > 0){
            int difference = 0;
            if(health > 100 - healthPotionHealAmount){
                difference = 100 - health;
                health = 100;
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

    // Run away from battle
    public boolean run(){
        if(numRunAttempts> 0){
            numRunAttempts--;
            System.out.println("\t> You ran away from the " + enemy + "!");
            System.out.println("\t> You have " + numRunAttempts + " runs remaining.");
            return true;
        } else {
            System.out.println("\t> You have no runs! Defeat enemies to get more!");
            return false;
        }
    }

    // Fight result
    public void fightResult(){
        numEnemiesDefeated++;
        System.out.println("----------------------------------------");
        System.out.println(" # " + enemy + " was defeated! # ");
        System.out.print(" # You have defeated " + numEnemiesDefeated + " "); 
        System.out.println((numEnemiesDefeated == 1) ? "enemy so far! # " : "enemies so far! # ");
        if(numEnemiesDefeated % 5 == 0) {
            System.out.println(" # You have gained a run attempt! #");
            numRunAttempts++;
            System.out.println(" # You have " + numRunAttempts + " runs remaining. #");
            difficulty();
        }
        System.out.println(" # You have " + health + " HP left. #");
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
            System.out.println(" # The " + enemy + " dropped a health potion! #");
            System.out.println(" # You now have " + numHealthPotions + " health potion(s). # ");
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
        System.out.println("\nTotal Enemies " + name + " Defeated: " + numEnemiesDefeated);
        System.out.println("\n#######################");
        System.out.println("# THANKS FOR PLAYING! #");
        System.out.println("#######################");

        sc.close();
        System.exit(0);
    }
}
