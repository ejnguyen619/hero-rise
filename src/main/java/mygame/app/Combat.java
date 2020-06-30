package mygame.app;

import javax.swing.ImageIcon;

import mygame.app.Enemy.*;

public class Combat {
    
    App app;
    UI ui;
    Player player = new Player();
    SuperMonster monster;
    Audio audio;

    public Combat(App app, UI ui, Audio audio){
        this.app = app;
        this.ui = ui;
        this.audio = audio;
    }

    public void defaultSetup(){
        player.reset();
        ui.healthBar.setValue(player.health);
        ui.manaBar.setValue(player.mana);
        ui.hpLabelNumber.setText(player.health + "/" + player.maxHealth);
        ui.mpLabelNumber.setText(player.mana + "/" + player.maxMana);
        encounter();
    }

    public void selectPosition(String nextPosition){
        switch(nextPosition){
            case "fightInfo": fightInfo(); break;
            case "encounter": encounter(); break;
            case "attack": attack(); break;
            case "skillsScreen": skillScreen(); break;
            case "magicScreen": magicScreen(); break;
            case "itemScreen": itemScreen(); break;
            case "helpScreen": help(0); break;
            case "enemyAction": enemyAction(false, player.dodgeChance, monster.enemyCritChance + monster.critBonus); break;
            case "defend-start": defend(0); break;
            case "defend-enemyAction": enemyAction(true, 0, 0); break;
            case "defend-end": defend(1); break;
            case "counter-start": counter(0); break;
            case "counter-enemyAction": enemyAction(false, player.dodgeChance*4, monster.enemyCritChance + monster.critBonus); break;
            case "counter-end": counter(1); break;
            case "fire": fire(); break;
            case "healthPotion": healthPotion(); break;
            case "etherPotion": etherPotion(); break;
            case "help-skills": help(1); break;
            case "help-magic": help(2); break;
            case "help-items": help(3); break;
            case "enemyDead": enemyDead(); break;
            case "fightResult": fightResult(); break;
            case "playerDead": playerDead(); break;
            case "reset": defaultSetup(); break;
            case "end": end(); break; 
        }
    }

    public void encounter(){
        enemyType();
        generateEnemyHP();
        buttonChoice("Fight", "Run", "", "", "", "");
        nextPosition("fightInfo", (monster.name != "Dragon") ? "encounter" : "", "", "", "", "");
    }

    // Fight Screen
    public void fightInfo(){
        ui.mainTextArea.setFont(ui.normalFont);
        ui.mainTextArea.setText(monster.name + " HP: " + monster.enemyHealth + "/" + monster.fullEnemyHealth + "\n\nWhat would you like to do?");
        buttonChoice("Attack", "Skills", "Magic", "Items", "Help", "Run");
        nextPosition("attack", "skillsScreen", (player.numEnemiesDefeated >= 6) ? "magicScreen" : "", "itemScreen", "helpScreen", (monster.name != "Dragon") ? "encounter" : "");        
    }

    // Attack enemy
    public void attack(){
        player.playerTurnOrder = "first";
        buttonChoice(">", "", "", "", "", "");

        // Generate damage values for player     
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
        damageEnemy(damageDealt);
        nextPosition((monster.enemyHealth < 1) ? "enemyDead" : "enemyAction", "", "", "", "", "");
    }

    // Skills screen
    public void skillScreen(){
        ui.mainTextArea.setText("Guardian Strike:            5MP\nCounter Force:              5MP");
        buttonChoice("Guardian Strike", "Counter Force", "Cancel", "", "", "");
        nextPosition("defend-start", "counter-start", "fightInfo", "", "", "");
    }

    // Magic screen
    public void magicScreen(){
        ui.mainTextArea.setText("Fire:            10MP");
        buttonChoice("Fire", "Cancel", "", "", "", "");
        nextPosition("fire", "fightInfo", "", "", "", "");
    }

    // Items screen
    public void itemScreen(){
        ui.mainTextArea.setText("Health potion:   " + "x" + player.numHealthPotions + "\nEther potion:    " + "x" + player.numEtherPotions);
        buttonChoice("Health Potion", "Mana Potion", "Cancel", "", "", "");
        nextPosition("healthPotion", "etherPotion", "fightInfo", "", "", "");
    }

    // Check descriptions of skills and items
    public void help(int category){
        ui.mainTextArea.setFont(ui.helpFont);

        // Toggle information based on category
        switch(category){
            case 0:
                ui.mainTextArea.setText("");
                break;
            case 1:
                String guardian = "Guardian Strike:\nEnemy attacks first. Player receives half damage. Player attack hits.\n";
                String counter = "Counter Force:\nPlayer dodge rate up. Enemy attacks first. If enemy attack misses, player performs critical hit.";
                ui.mainTextArea.setText(guardian + "\n" + counter);
                break;
            case 2:
                String health = "Health Potion:\nRecovers 30HP.\n";
                String mana = "Mana Potion:\nRecovers 25MP.";
                ui.mainTextArea.setText(health + "\n" + mana);
                break;
            case 3:
                String fire = "Fire:\nInflicts 25 damage to enemy.";
                ui.mainTextArea.setText(fire);
                break;
        }
    
        buttonChoice("Skills", "Items", "Magic", "Cancel", "", "");
        nextPosition("help-skills", "help-items", "help-magic", "fightInfo", "", "");
    }

    // Player uses Guardian Force
    public void defend(int turn){
        buttonChoice(">", "", "", "", "", "");

        // Prepare for counter attack
        if(turn == 0){
            if(player.mana < 5) return;
            player.defend();
            ui.mainTextArea.setText("You used Guardian Strike!\n\nYou have taken a defensive stance.");
            ui.manaBar.setValue(player.mana);
            ui.mpLabelNumber.setText(player.mana + "/" + player.maxMana);
            nextPosition("defend-enemyAction", "", "", "", "", "");
        }
        // Attack after enemy attack 
        else if(turn == 1){
            int damageDealt = damage(player.attackDamage, 0, 1);
            damageEnemy(damageDealt);
            nextPosition((monster.enemyHealth < 1) ? "enemyDead" : "fightInfo", "", "", "", "", "");
        }
    }

    // Player uses Counter Force
    public void counter(int turn){
        buttonChoice(">", "", "", "", "", "");

        // Prepare for counter attack
        if(turn == 0){
            if(player.mana < 5) return;
            player.counter();
            ui.mainTextArea.setText("You used Counter Force!\n\nYou have taken an evasive stance.");
            ui.manaBar.setValue(player.mana);
            ui.mpLabelNumber.setText(player.mana + "/" + player.maxMana);
            nextPosition("counter-enemyAction", "", "", "", "", "");
        }
        // Attack after enemy attack 
        else if(turn == 1){
            int damageDealt = damage(player.attackDamage, 0, 1);
            if(dodge(monster.enemyDodgeChance + monster.dodgeBonus)){
                damageDealt = 0;
                player.setHitCrit(false, false);
            }
            else if(!monster.enemyHit || critical(player.criticalChance)){
                damageDealt *= 1.5;
                player.setHitCrit(true, true);
            } else {
                player.setHitCrit(true, false);
            }
            damageEnemy(damageDealt);
            player.dodgeChance /= 3;
            nextPosition((monster.enemyHealth < 1) ? "enemyDead" : "fightInfo", "", "", "", "", "");
        }
    }

    // Casts fire spell
    public void fire(){
        if(player.mana < 10) return;
        
        buttonChoice(">", "", "", "", "", "");

        // Generate damage values for player     
        int damageDealt = 25;
        player.fire();
        ui.manaBar.setValue(player.mana);
        ui.mpLabelNumber.setText(player.mana + "/" + player.maxMana);

        // Damage inflicted to enemy calculation
        monster.setHealth(-damageDealt);
        ui.mainTextArea.setText("You cast Fire!\n\nYou strike the " + monster.name + " for " + damageDealt + " damage.");
        nextPosition((monster.enemyHealth < 1) ? "enemyDead" : "enemyAction", "", "", "", "", "");
    }

    // Drink health potion to recover HP
    public void healthPotion(){
        if(player.healthPotion()){
            ui.mainTextArea.setText("Health potion:   " + "x" + player.numHealthPotions + "\nEther potion:    " + "x" + player.numEtherPotions);
            ui.healthBar.setValue(player.health);
            ui.hpLabelNumber.setText(player.health + "/" + player.maxHealth);
        }
    }

    // Drink ether potion to recover MP
    public void etherPotion(){
        if(player.etherPotion()){
            ui.mainTextArea.setText("Health potion:   " + "x" + player.numHealthPotions + "\nEther potion:    " + "x" + player.numEtherPotions);
            ui.manaBar.setValue(player.mana);
            ui.mpLabelNumber.setText(player.mana + "/" + player.maxMana);
        }
    }

    // Enemy action after player attack
    public void enemyAction(boolean half, int dodge, int crit){
        buttonChoice(">", "", "", "", "", "");
        
        // Generate damage values for enemy
        int damageTaken = damage(monster.enemyAttackDamage, monster.attackBonus, monster.minAttack);
        
        // Damage inflicted to enemy calculation
        if(half) damageTaken /= 2;
        enemyAction(damageTaken, player.dodgeChance, monster.enemyCritChance + monster.critBonus, half);
        String nextPosition1 = "";

        // nextPosition depend on player health and player's last move used
        if(player.health < 1) nextPosition1 = "playerDead";
        else {
            if(player.playerTurnOrder == "first") nextPosition1 = "fightInfo";
            else {
                if(player.skill == "guardian") nextPosition1 = "defend-end";
                else if(player.skill == "counter") nextPosition1 = "counter-end";
            }           
        }
        nextPosition(nextPosition1, "", "", "", "", "");
    }

    // Checks enemy action
    public void enemyAction(int damage, int dodge, int crit, boolean half){
        // Check for super move
        if(monster.name.equals("Dragon")){
            if(monster.deadlyAttack == false){
                if(fireCharge() == false){
                    // Damage inflicted to player calculation
                    enemyAttack(damage, dodge, crit);
                }
            } else {
                enemyAttack((!half) ? 50 : 25, dodge, 0);
                monster.deadlyAttack = false;  
            }
        } else {
            // Damage inflicted to player calculation
            enemyAttack(damage, dodge, crit);          
        }
    }

    // Player Dead Screen
    public void playerDead(){
        player.score -= 50;
        ui.mainTextArea.setText("You limp out of the dungeon, weak from battle. \n\nGAME OVER!\n\nWhat would you like to do?");
        buttonChoice("Try again!", "Exit game", "", "", "", "");
        nextPosition("reset", "end", "", "", "", "");
        audio.stop();
        audio.setFile(audio.path + "lose.wav");
        audio.play();
    }

    // Exit game
    public void end(){
        ui.mainTextArea.setText(ui.jtf.getText() + "'s Final Score: " + player.score);
        ui.image = new ImageIcon(".//res//Images//end.jpg");
        ui.imageLabel.setIcon(ui.image);
        buttonChoice("", "", "", "", "", "");
        nextPosition("", "", "", "", "", "");
        ui.choiceButtonPanel.setVisible(false);
        audio.stop();
        audio.setFile(audio.path + "credits.wav");
        audio.play();
        audio.loop();
    }

    // Enemy dead
    public void enemyDead(){
        String output = "";

        // Score calculation
        if(monster.name.equals("Dragon")) player.score += 200;
        else player.score += 100;

        player.numEnemiesDefeated++;
        output = (player.numEnemiesDefeated == 6) ? output + "\nYou have learned the magic spell Fire!" : output;

        if(player.numEnemiesDefeated % 10 == 0) {
            output += "\nYour HP and MP has been completely restored!";
            player.setHealth(player.maxHealth - player.health);
            player.setMana(player.maxMana - player.mana);
            ui.healthBar.setValue(player.health);
            ui.manaBar.setValue(player.mana);
            ui.hpLabelNumber.setText(player.health + "/" + player.maxHealth);
            ui.mpLabelNumber.setText(player.mana + "/" + player.maxMana); 
        }

        ui.mainTextArea.setText(monster.name + " was defeated!\n" + output);
        buttonChoice("Result", "", "", "", "", "");
        nextPosition("fightResult", "", "", "", "", "");
        audio.stop();
        audio.setFile(audio.path + "win.wav");
        audio.play();
    }

    // Fight result
    public void fightResult(){
        String healthPotion = (healthDrop()) ? ("The " + monster.name + " dropped a health potion!\n\n") : "";
        String manaPotion = (manaDrop()) ? ("The " + monster.name + " dropped a mana potion!\n\n") : "";
        String potion = (!healthPotion.equals("") && !manaPotion.equals("")) ? ("The " + monster.name + " dropped a health potion!\n\nThe " + monster.name + " dropped a mana potion!\n\n") : (healthPotion + manaPotion);
        ui.mainTextArea.setText(potion + "What would you like to do now? ");

        buttonChoice("Continue fighting", "Exit dungeon", "", "", "", "");
        nextPosition("encounter", "end", "", "", "", "");
    }

    // Determine enemy health and monster type
    public void enemyType(){
        String path;
        if(player.numEnemiesDefeated % 5 == 0 && player.numEnemiesDefeated > 0) { 
            monster = new Dragon();
            path = audio.path + "battle-boss.wav";
        }
        else {
            switch(app.rand.nextInt(4)){
                case 0: monster = new Skeleton(); break;
                case 1: monster = new Zombie(); break;
                case 2: monster = new Warrior(); break;
                case 3: monster = new Assassin(); break;
            }
            path = audio.path + "battle.wav";
        }
        audio.stop();
        audio.setFile(path);
        audio.play();
        audio.loop();
        ui.image = new ImageIcon(monster.imagePath);
        ui.imageLabel.setIcon(ui.image);
        monster.difficulty(player.numEnemiesDefeated);
        String pronoun = (monster.name == "Assassin") ? "An" : "A";
        String runAway = (monster.name == "Dragon") ? "\n\nYOU CANNOT RUN AWAY FROM THIS FIGHT!" : "";
        ui.mainTextArea.setText(pronoun + " " + monster.name + " has appeared!" + runAway);
    }

    // Generate enemy health
    public void generateEnemyHP(){
        while(true){
            monster.enemyHealth = app.rand.nextInt(monster.maxEnemyHealth) + monster.healthBonus;
            if(monster.enemyHealth >= monster.minHealth && monster.enemyHealth <= monster.maxEnemyHealth && monster.enemyHealth > 0) break;
        }
        monster.fullEnemyHealth = monster.enemyHealth;
    }

    // Generate damage value
    public int damage(int baseDamage, int bonus, int min){
        int damage = 0;
        while(damage < min) damage = app.rand.nextInt(baseDamage) + bonus;
        return damage;
    }

    // Dodge calculation
    public boolean dodge (int dodgeRate){
        if(app.rand.nextInt(100) < dodgeRate) return true;
        else return false;
    }

    // Critical calculation
    public boolean critical (int criticalRate){
        if(app.rand.nextInt(100) < criticalRate) return true;
        else return false;
    }

    // Damage enemy
    public void damageEnemy(int damage){
        monster.setHealth(-damage);
        String hitEnemy = (player.hit)  ? "HIT!" : "MISS!";
        String critEnemy = (player.crit && player.hit) ? "\nCRITICAL!\n" : "";
        ui.mainTextArea.setText(hitEnemy + "\n" + critEnemy + "\nYou strike the " + monster.name + " for " + damage + " damage.");
    }

    // Receive damage from enemy
    public void takeDamage(int damage){
        player.setHealth(-damage);
        String fireBlast = (monster.name == "Dragon" && monster.deadlyAttack) ? "Dragon unleashes a deadly fire blast!\n\n" : "";
        String hitByEnemy = (monster.enemyHit)  ? "HIT!" : "MISS!";
        String critByEnemy = (monster.enemyCrit && monster.enemyHit) ? "\nCRITICAL!\n" : "";
        ui.mainTextArea.setText(fireBlast + hitByEnemy + "\n" + critByEnemy + "\nThe " + monster.name + " dealt you " + damage + " damage.");
        ui.healthBar.setValue(player.health);
        ui.hpLabelNumber.setText(player.health + "/" + player.maxHealth);
    }

    // Health potion drop
    public boolean healthDrop(){
        if(app.rand.nextInt(100) < player.healthPotionDropChance){
            player.numHealthPotions++;
            return true;
        }
        return false;
    }

    // Mana potion drop
    public boolean manaDrop(){
        if(app.rand.nextInt(100) < player.etherPotionDropChance){
            player.numEtherPotions++;
            return true;
        }
        return false;
    }

    // Charge up dragon's super move
    public boolean fireCharge(){
        if(app.rand.nextInt(100) < 30) {
            ui.mainTextArea.setText("Dragon is storing power!");
            monster.deadlyAttack = true;
            return true;
        } 
        else return false;
    }

    // Enemy Damage Calculation
    public void enemyAttack(int damage, int dodge, int crit){
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
        takeDamage(damage);         
    }

    // Print button choices
    public void buttonChoice(String a, String b, String c, String d, String e, String f){
        String[] text = {a, b, c, d, e, f};
        for(int i = 0; i < ui.choices.length; i++){
            ui.choices[i].setText(text[i]);
        }
    }

    // Determine destination of choices of current screen
    public void nextPosition(String pos1, String pos2, String pos3, String pos4, String pos5, String pos6){
        String[] text = {pos1, pos2, pos3, pos4, pos5, pos6};
        for(int i = 0; i < ui.choices.length; i++){
            app.nextPosition[i] = text[i];
        }
    }
}