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
    
    public static void main( String[] args )
    {
        // Start adventure
        App game = new App();
        game.setup();
        game.start();
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

        System.out.println("\nWelcome to the Dungeon!");

        GAME:
        while(true){
            System.out.println("----------------------------------------");

            // Determine enemy health and monster type
            enemy = enemies[rand.nextInt(enemies.length)];
            System.out.println("\t# " + enemy + " has appeared! #\n");

            int healthBonus;
            if(enemy.equals("Zombie")) healthBonus = 5;
            else if(enemy.equals("Warrior")) healthBonus = 10;
            else if(enemy.equals("Assassin")) healthBonus = -10;
            else healthBonus = 0;
            enemyHealth = 0;
            while(enemyHealth < 10) enemyHealth = rand.nextInt(maxEnemyHealth + healthBonus);
            if(healthBonus > 0) enemyHealth += healthBonus;

            // Fight information and player choices
            while(enemyHealth > 0 && health > 0){
                System.out.println("\tYour HP: " + health);
                System.out.println("\t" + enemy + "'s HP: " + enemyHealth);
                System.out.println("\n\tWhat would you like to do?");
                System.out.println("\t1. Attack");
                System.out.println("\t2. Defend");
                System.out.println("\t3. Drink health potion");
                System.out.println("\t4. Run");

                String input = sc.nextLine();

                // Damage calculation
                if(input.equals("1")){
                    damage();
                } 
                // Perform counter attack
                else if(input.equals("2")){
                    counter();
                } 
                // Consume health potion
                else if(input.equals("3")){
                    healthPotion();
                }
                // Leave current battle
                else if(input.equals("4")){
                    if(run() == true) continue GAME;
                } 
                // Error check for input
                else {
                    System.out.println("\tInvalid command!");
                }
            }

            // Player run out of health
            if(health < 1){
                System.out.println("\t> You have taken too much damage. You are too weak to go on!");
                System.out.println("You limp out of the dungeon, weak from battle.");
                break;
            }

            // Fight result
            fightResult();

            // Health potion potential drop
            healthDrop();

            // Decide action after fight conclusion
            if(nextAction() == 2) break;
        }
    }

    // Damage Calculation
    public void damage(){     

        int attackBonus = 0;
        int critBonus = 0;
        int dodgeBonus = 0;

        if(enemy.equals("Zombie")){
            attackBonus = -5;
            dodgeBonus = -5;
        } else if(enemy.equals("Warrior")){
            attackBonus = 10;
            critBonus = -10;
            dodgeBonus = -10;           
        } else if(enemy.equals("Assassin")){
            attackBonus = -5;
            critBonus = 20;
            dodgeBonus = 30;           
        }

        int damageDealt = 0;
        while(damageDealt < 1) damageDealt = rand.nextInt(attackDamage);
        int damageTaken = 0;
        while(damageTaken < 1) damageTaken = rand.nextInt(enemyAttackDamage + attackBonus);
        if(attackBonus > 0) damageTaken += attackBonus;

        // Damage inflicted to enemy calculation
        if(rand.nextInt(100) < enemyDodgeChance + dodgeBonus){
            damageDealt = 0;
            System.out.println("\t> MISS!");
        } else if(rand.nextInt(100) < criticalChance){
            damageDealt *= 1.5;
            System.out.println("\t> CRITICAL HIT!");
        }
        
        System.out.println("\t> You strike the " + enemy + " for " + damageDealt + " damage.");
        enemyHealth -= damageDealt;

        if(enemyHealth <= 0) return;

        // Damage inflicted to player calculation
        if(rand.nextInt(100) < dodgeChance){
            damageTaken = 0;
            System.out.println("\t> MISS!");
        } else if(rand.nextInt(100) < enemyCritChance + critBonus){
            damageTaken *= 1.5;
            System.out.println("\t> CRITICAL HIT!");
        }

        health -= damageTaken;
        System.out.println("\t> The " + enemy + " dealt you " + damageTaken + " damage in retaliation.");
    }

    // Counter attack
    public void counter(){
        System.out.println("\t> You have taken a defensive stance.");

        int attackBonus = 0;
        if(enemy.equals("Zombie")){
            attackBonus = -5;
        } else if(enemy.equals("Warrior")){
            attackBonus = 10;          
        } else if(enemy.equals("Assassin")){
            attackBonus = -5;          
        }

        int damageTaken = 0;
        while(damageTaken < 1) damageTaken = rand.nextInt((enemyAttackDamage + attackBonus)/2);
        health -= damageTaken;
        System.out.println("\t> The " + enemy + " dealt you " + damageTaken + " damage.");

        int damageDealt = 0;
        while(damageDealt < 1) damageDealt = rand.nextInt(attackDamage/2);
        if(rand.nextInt(100) < criticalChance){
            damageDealt *= 1.5;
            System.out.println("\t> CRITICAL HIT!");
        }
        enemyHealth -= damageDealt;
        System.out.println("\t> You strike the " + enemy + " back for " + damageDealt + " damage.");
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
            System.out.println(" # You have gained a run attempt!");
            numRunAttempts++;
            System.out.println(" # You have " + numRunAttempts + " runs remaining.");
        }
        System.out.println(" # You have " + health + " HP left. #");
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
        System.out.println("----------------------------------------");
        System.out.println("What would you like to do now?");
        System.out.println("1. Continue fighting");
        System.out.println("2. Exit dungeon");

        // Error check for input
        String input = sc.nextLine();
        while(!input.equals("1") && !input.equals("2")){
            System.out.println("Invalid command!");
            System.out.println("\nWhat would you like to do now?");
            System.out.println("1. Continue fighting");
            System.out.println("2. Exit dungeon");
            input = sc.nextLine();
        }

        // Continue or leave dungeon
        if(input.equals("1")){
            System.out.println("You continue on your adventure!");
        } else if(input.equals("2")){
            System.out.println("You exit the dungeon, successful from your adventure!");
        }
        return Integer.parseInt(input);
    }
     
    // Exit game
    public void end(){
        
        System.out.println("\nTotal Enemies Defeated: " + numEnemiesDefeated);
        System.out.println("\n#######################");
        System.out.println("# THANKS FOR PLAYING! #");
        System.out.println("#######################");

        sc.close();
        System.exit(0);
    }
}
