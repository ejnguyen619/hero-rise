package mygame.app;

import java.util.Random;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        // System objects
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        // Enemy variables
        String[] enemies = {"Skeleton", "Zombie", "Warrior", "Assassin"};
        int maxEnemyHealth = 75;
        int enemyAttackDamage = 25;

        // Player variables
        int health = 100;
        int attackDamage = 50;
        int numHealthPotions = 3;
        int healthPotionHealAmount = 30;
        int healthPotionDropChance = 50; // Percentage
        int numEnemiesDefeated = 0;
        int numRunAttempts = 3;

        // Game text output
        boolean running = true;
        System.out.println("\nWelcome to the Dungeon!");

        GAME:
        while(running){
            System.out.println("----------------------------------------");

            int enemyHealth = rand.nextInt(maxEnemyHealth);
            while(enemyHealth == 0){
                enemyHealth = rand.nextInt(maxEnemyHealth);
            }

            String enemy = enemies[rand.nextInt(enemies.length)];
            System.out.println("\t# " + enemy + " has appeared! #\n");

            // Fight information and player choices
            while(enemyHealth > 0){
                System.out.println("\tYour HP: " + health);
                System.out.println("\t" + enemy + "'s HP: " + enemyHealth);
                System.out.println("\n\tWhat would you like to do?");
                System.out.println("\t1. Attack");
                System.out.println("\t2. Drink health potion");
                System.out.println("\t3. Run");

                String input = sc.nextLine();

                // Damage calculation
                if(input.equals("1")){
                   int damageDealt = rand.nextInt(attackDamage);
                   int damageTaken = rand.nextInt(enemyAttackDamage);

                   enemyHealth -= damageDealt;
                   health -= damageTaken;

                   System.out.println("\t> You strike the " + enemy + " for " + damageDealt + " damage.");
                   System.out.println("\t> You receive " + damageTaken + " in retaliation.");

                   if(health < 1){
                       System.out.println("\t> You have taken too much damage. You are too weak to go on!");
                       break;
                   }
                } 
                // Consume health potion
                else if(input.equals("2")){
                    if(numHealthPotions > 0){
                        health += healthPotionHealAmount;
                        numHealthPotions--;
                        System.out.println("\t> You drink a health potion, healing yourself for " + healthPotionHealAmount + ".");
                        System.out.println("\t> You now have " + health + " HP.");
                        System.out.println("\t> You have " + numHealthPotions + " health potions left.");
                    } else {
                        System.out.println("\t> You have no health potions! Defeat enemies for a chance to get one!");
                    }
                } 
                // Leave current battle
                else if(input.equals("3")){
                    if(numRunAttempts> 0){
                       numRunAttempts--;
                       System.out.println("\t> You ran away from the " + enemy + "!");
                       System.out.println("\t> You have " + numRunAttempts + " runs remaining.");
                       continue GAME;
                    } else {
                        System.out.println("\t> You have no runs! Defeat enemies to get more!");
                    }
                } 
                // Error check for input
                else {
                    System.out.println("\tInvalid command!");
                }
            }

            // Player run out of health
            if(health < 1){
                System.out.println("You limp out of the dungeon, weak from battle.");
                break;
            }

            // Fight result
            numEnemiesDefeated++;
            System.out.println("----------------------------------------");
            System.out.println(" # " + enemy + " was defeated! # ");
            System.out.print(" # You have defeated " + numEnemiesDefeated + " "); 
            System.out.println((numEnemiesDefeated == 1) ? "enemy so far! # " : "enemies so far! # ");
            if(numEnemiesDefeated % 5 == 0) System.out.println(" # You have gained a run attempt!");
            System.out.println(" # You have " + health + " HP left. #");

            // Health potion potential drop
            if(rand.nextInt(100) < healthPotionDropChance){
                numHealthPotions++;
                System.out.println(" # The " + enemy + " dropped a health potion! #");
                System.out.println(" # You now have " + numHealthPotions + " health potion(s). # ");
            }

            // Decide action after fight conclusion
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
                break;
            }
        }

        // Exit game
        System.out.println("\nTotal Enemies Defeated: " + numEnemiesDefeated);
        System.out.println("\n#######################");
        System.out.println("# THANKS FOR PLAYING! #");
        System.out.println("#######################");

        sc.close();
    }
}
