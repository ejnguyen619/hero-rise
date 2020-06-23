package mygame.app;

public class Player {

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
    boolean hit;
    boolean crit;
    String playerTurnOrder;
    String skill;

    public Player(){
        reset();
    }

    // Initial Player Stats
    public void reset(){
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

    // Drink health potion to recover HP
    public boolean healthPotion(){
        if(health == maxHealth) return false;
        if(numHealthPotions > 0){
            if(health > maxHealth - healthPotionHealAmount){
                setHealth(maxHealth - health);
            } else {
                setHealth(healthPotionHealAmount);
            }
            numHealthPotions--;
        }
        return true;
    }

    // Drink ether potion to recover MP
    public boolean etherPotion(){
        if(mana == maxMana) return false;
        if(numEtherPotions > 0){
            if(mana > maxMana - etherPotionHealAmount){
                setMana(maxMana - mana);
            } else {
                setMana(etherPotionHealAmount);
            }
            numEtherPotions--;
        }
        return true;
    }

    // Prepare Guardian Strike
    public void defend(){
        playerTurnOrder = "second";
        skill = "guardian";
        setMana(-5);
        hit = true;
        crit = false;
    }

    // Prepare Counter Force
    public void counter(){
        playerTurnOrder = "second";
        skill = "counter";
        setMana(-5);
        dodgeChance *= 3;
    }

    // Prepare Fire
    public void fire(){
        playerTurnOrder = "first";
        hit = true;
        crit = false;
        setMana(-10);
    }

    // Set hit and crit parameters
    public void setHitCrit(boolean hit, boolean crit){
        setHit(hit);
        setCrit(crit);
    }

    // Set hit parameters
    public void setHit(boolean hit){
        this.hit = hit;
    }

    // Set crit parameters
    public void setCrit(boolean crit){
        this.crit = crit;
    }

    // Set health parameters
    public void setHealth(int value){
        health += value;
    }

    // Set mana parameters
    public void setMana(int value){
        mana += value;
    }
}