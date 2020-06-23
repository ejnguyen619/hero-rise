package mygame.app.Enemy;

public class SuperMonster {

    public String name;
    public int maxEnemyHealth;
    public int enemyAttackDamage;
    public int enemyHealth;
    public int enemyCritChance;
    public int enemyDodgeChance;
    public int minHealth;
    public int minAttack;
    public int attackBonus;
    public int critBonus;
    public int dodgeBonus;
    public int healthBonus;
    public int fullEnemyHealth;
    public boolean deadlyAttack;
    public boolean enemyHit;
    public boolean enemyCrit;

    // Initial Enemy Stats
    public void reset(){
        maxEnemyHealth = 75;
        enemyAttackDamage = 25;
        enemyCritChance = 10;
        enemyDodgeChance = 10;
        minHealth = 10;
        minAttack = 1;
        deadlyAttack = false;
        enemyHit = false;
        enemyCrit = false;
    }

    // Assign extra stats based on type of enemy
    public void bonusStats(int health, int attack, int crit, int dodge){
        healthBonus = health;
        attackBonus = attack;
        critBonus = crit;
        dodgeBonus = dodge;
    }

    // Increase difficulty as more enemies are defeated
    public void difficulty(int numberEnemies){
        int set = numberEnemies;
        while(set % 5 == 0 && numberEnemies > 0){
            maxEnemyHealth += 5;
            enemyAttackDamage += 5;
            enemyCritChance += 5;
            minHealth += 5;
            minAttack += 2;
            set -= 5;
        }
    }

    // Set hit and crit parameters
    public void setHitCrit(boolean hit, boolean crit){
        setHit(hit);
        setCrit(crit);
    }

    // Set hit parameters
    public void setHit(boolean hit){
        enemyHit = hit;
    }

    // Set crit parameters
    public void setCrit(boolean crit){
        enemyCrit = crit;
    }

    // Set health parameters
    public void setHealth(int value){
        enemyHealth += value;
    }
}