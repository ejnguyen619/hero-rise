package mygame.app;

import mygame.app.Enemy.*;

public class Choices {

    SuperMonster monster;
    Player player;
    App app;

    public Choices(Player player, App app){
        this.player = player;
        this.app = app;
    }
    
    // Determine monster type
    public SuperMonster enemyType(){
        if(player.numEnemiesDefeated % 5 == 0 && player.numEnemiesDefeated > 0) { 
            monster = new Dragon();
        }
        else {
            switch(App.rand.nextInt(4)) {
                case 0: monster = new Skeleton(); break;
                case 1: monster = new Zombie(); break;
                case 2: monster = new Warrior(); break;
                case 3: monster = new Assassin(); break;
            }
        }
        return monster;
    }

    // Health potion drop
    public boolean healthDrop(){
        if(App.rand.nextInt(100) < player.healthPotionDropChance) {
            player.numHealthPotions++;
            return true;
        }
        return false;
    }

    // Mana potion drop
    public boolean manaDrop(){
        if(App.rand.nextInt(100) < player.etherPotionDropChance) {
            player.numEtherPotions++;
            return true;
        }
        return false;
    }

    // Generate damage value
    public int damage(int baseDamage, int bonus, int min){
        int damage = 0;
        while(damage < min) damage = App.rand.nextInt(baseDamage) + bonus;
        return damage;
    }

    // Dodge calculation
    public boolean dodge (int dodgeRate){
        if(App.rand.nextInt(100) < dodgeRate)
            return true;
        else return false;
    }

    // Critical calculation
    public boolean critical (int criticalRate){
        if(App.rand.nextInt(100) < criticalRate)
            return true;
        else return false;
    }

    // Player attack
    public int attack(){
        player.playerTurnOrder = "first";
        int damageDealt = damage(player.attackDamage, 0, 1);

        // Damage inflicted to enemy calculation
        if(dodge(monster.enemyDodgeChance + monster.dodgeBonus)) {
            player.setHitCrit(false, false);
            damageDealt = 0;
        }
        else if(critical(player.criticalChance)) {
            player.setHitCrit(true, true);
            damageDealt *= 1.5;
        } else {
            player.setHitCrit(true, false);
        }
        return damageDealt;
    }

    // Enemy attack
    public int enemyAttack(int damage, int dodge, int crit){
        if(dodge(dodge)) {
            monster.setHitCrit(false, false);
            damage = 0;
        }
        else if(critical(crit)){
            monster.setHitCrit(true, true);
            damage *= 1.5;
        } else {
            monster.setHitCrit(true, false);
        }
        return damage;        
    }

    // Player counter attack
    public int counter(){
        int damageDealt = damage(player.attackDamage, 0, 1);

        // Damage inflicted to enemy calculation
        if(dodge(monster.enemyDodgeChance + monster.dodgeBonus)){
            player.setHitCrit(false, false);
            damageDealt = 0;
        }
        else if(!monster.enemyHit || critical(player.criticalChance)){
            player.setHitCrit(true, true);
            damageDealt *= 1.5;
        } else {
            player.setHitCrit(true, false);
        }
        player.dodgeChance /= 3;
        return damageDealt;       
    }

    // Charge up dragon's super move
    public boolean fireCharge(){
        if(App.rand.nextInt(100) < 30) {
            return true;
        } 
        else return false;
    }

    // Dragon action
    public int dragonAction(int damage, int dodge, int crit, boolean half){
        if(fireCharge() == false){
            // Damage inflicted to player calculation
            damage = enemyAttack(damage, dodge, crit);
        } else {
            monster.deadlyAttack = true;
        }
        return damage;
    }

    // Dragon uses Fire Blast
    public int fireBlast(int damage, int dodge, int crit, boolean half){
        damage = enemyAttack((!half) ? 50 : 25, dodge, 0);
        monster.deadlyAttack = false;
        return damage; 
    }

    // Checks enemy action
    public int enemyAction(int damage, int dodge, int crit, boolean half){
        if(monster.name.equals("Dragon")){
            // Check for super move
            damage = (monster.deadlyAttack == false) ? dragonAction(damage, dodge, crit, half) : fireBlast(damage, dodge, crit, half);
        } else {
            damage = enemyAttack(damage, dodge, crit);          
        }
        return damage;
    }

    // Determine next position after enemy attack
    public String enemyNextPosition(){
        if(player.health < 1) return "playerDead";
        if(player.playerTurnOrder == "first") return "fightInfo";
        if(player.skill == "guardian") return "defend-end";
        if(player.skill == "counter") return "counter-end";
        return "";
    }
}