package mygame.app;

import javax.swing.ImageIcon;

import mygame.app.Enemy.*;

public class Screen {
    
    App app;
    Layout layout;
    Player player = new Player();
    Choices choices = new Choices(player, app);
    SuperMonster monster;
    Audio audio;

    public Screen(App app, Layout layout, Audio audio){
        this.app = app;
        this.layout = layout;
        this.audio = audio;
    }

    // DIsplay text in Start Screen
    public void startScreen(){
        layout.titleNameLabel.setText("HERO RISE");
        layout.msgLabel.setText("Please enter your name");
        layout.startButton.setText("START");
    }

    // Setup for Game Screen
    public void defaultSetup(){
        player.reset();
        layout.hpLabel.setText("HP:");
        layout.mpLabel.setText("MP:");
        setPlayerHealth();
        setPlayerMana();
        encounter();
    }

    // SHORT METHODS --------------------------------------------------------------------------------------------------------

    // Update player health after action
    public void setPlayerHealth(){
        layout.healthBar.setValue(player.health);
        layout.hpLabelNumber.setText(player.health + "/" + player.maxHealth);
    }

    // Update player mana after action
    public void setPlayerMana(){
        layout.manaBar.setValue(player.mana);
        layout.mpLabelNumber.setText(player.mana + "/" + player.maxMana);
    }

    // Damage enemy
    public void damageEnemy(int damage){
        monster.setHealth(-damage);
        String hitEnemy = (player.hit)  ? "HIT!" : "MISS!";
        String critEnemy = (player.crit && player.hit) ? "\nCRITICAL!\n" : "";
        layout.mainTextArea.setText(hitEnemy + "\n" + critEnemy + "\nYou strike the " + monster.name + " for " + damage + " damage.");
    }

    // Receive damage from enemy
    public void takeDamage(int damage){
        player.setHealth(-damage);
        String fireBlast = (monster.name == "Dragon" && monster.deadlyAttack) ? "Dragon unleashes a deadly fire blast!\n\n" : "";
        String hitByEnemy = (monster.enemyHit)  ? "HIT!" : "MISS!";
        String critByEnemy = (monster.enemyCrit && monster.enemyHit) ? "\nCRITICAL!\n" : "";
        layout.mainTextArea.setText(fireBlast + hitByEnemy + "\n" + critByEnemy + "\nThe " + monster.name + " dealt you " + damage + " damage.");
        setPlayerHealth();
    }

    // Print button choices
    public void buttonChoice(String a, String b, String c, String d, String e, String f){
        String[] text = {a, b, c, d, e, f};
        for(int i = 0; i < layout.choices.length; i++){
            layout.choices[i].setText(text[i]);
        }
    }

    // Determine destination of choices of current screen
    public void nextPosition(String pos1, String pos2, String pos3, String pos4, String pos5, String pos6){
        String[] text = {pos1, pos2, pos3, pos4, pos5, pos6};
        for(int i = 0; i < layout.choices.length; i++){
            app.nextPosition[i] = text[i];
        }
    }

    // Print button choices based on subscreen
    public void buttonChoice(String screen){
        int x = 0;
        for(int i = 0; i < layout.choices.length; i++){
            String button = player.getName(screen, i);
            if(button == "") {
                layout.choices[i].setText("Cancel");
                x = i + 1;
                break;
            }
            layout.choices[i].setText(button);
        }
        for(int i = x; i < layout.choices.length; i++){
            layout.choices[i].setText("");
        }
    }

    // Determine destination of choices of subscreen
    public void nextPosition(String screen){
        int x = 0;
        for(int i = 0; i < layout.choices.length; i++){
            String nextPos = player.getKey(screen, i);
            if(nextPos == "") {
                app.nextPosition[i] = "fightInfo";
                x = i + 1;
                break;
            }
            app.nextPosition[i] = nextPos;
        }
        for(int i = x; i < layout.choices.length; i++){
            app.nextPosition[i] = "";
        }
    }

    // CHOICE METHODS --------------------------------------------------------------------------------------------------------

    // Determine action based on next position
    public void selectPosition(String nextPosition){
        switch(nextPosition){
            case "encounter": encounter(); break;
            case "fightInfo": fightInfo(); break;
            case "attack": attack(); break;
            case "skillsScreen": skillScreen(); break;
            case "magicScreen": magicScreen(); break;
            case "itemScreen": itemScreen(); break;
            case "helpScreen": help(0); break;
            case "enemyAction": enemyAction(false, player.dodgeChance, monster.enemyCritChance + monster.critBonus); break;
            case "defend-start": useSkill("Guardian Strike"); break;
            case "defend-enemyAction": enemyAction(true, 0, 0); break;
            case "defend-end": skillEffect("Guardian Strike"); break;
            case "counter-start": useSkill("Counter Force"); break;
            case "counter-enemyAction": enemyAction(false, player.dodgeChance*4, monster.enemyCritChance + monster.critBonus); break;
            case "counter-end": skillEffect("Counter Force"); break;
            case "fire": useMagic("Fire"); break;
            case "healthPotion": useItem("Health Potion"); break;
            case "etherPotion": useItem("Mana Potion"); break;
            case "help-skills": help(1); break;
            case "help-items": help(2); break;
            case "help-magic": help(3); break;
            case "enemyDead": enemyDead(); break;
            case "fightResult": fightResult(); break;
            case "playerDead": playerDead(); break;
            case "reset": defaultSetup(); break;
            case "end": end(); break; 
        }
    }

    // Encounter enemy
    public void encounter(){
        // Create new enemy
        monster = choices.enemyType();
        monster.difficulty(player.numEnemiesDefeated);
        monster.generateEnemyHP();

        // Set audio based on type of enemy
        String path = audio.path + ((monster.name == "Dragon") ? "battle-boss.wav" : "battle.wav");
        audio.playClip(path, "loop");

        // Set image of enemy
        layout.image = new ImageIcon(monster.imagePath);
        layout.imageLabel.setIcon(layout.image);
        
        // Display text
        String pronoun = (monster.name == "Assassin") ? "An" : "A";
        String runAway = (monster.name == "Dragon") ? "\n\nYOU CANNOT RUN AWAY FROM THIS FIGHT!" : "";
        layout.mainTextArea.setText(pronoun + " " + monster.name + " has appeared!" + runAway);

        // Set choices
        buttonChoice("Fight", "Run", "", "", "", "");
        nextPosition("fightInfo", (monster.name != "Dragon") ? "encounter" : "", "", "", "", "");
    }

    // Fight Screen
    public void fightInfo(){
        layout.mainTextArea.setFont(layout.normalFont);
        layout.mainTextArea.setText(monster.name + " HP: " + monster.enemyHealth + "/" + monster.fullEnemyHealth + "\n\nWhat would you like to do?");
        buttonChoice("Attack", "Skills", "Magic", "Items", "Help", "Run");
        nextPosition("attack", "skillsScreen", (player.numEnemiesDefeated >= 6) ? "magicScreen" : "", "itemScreen", "helpScreen", (monster.name != "Dragon") ? "encounter" : "");        
    }

    // Skills screen
    public void skillScreen(){
        layout.mainTextArea.setText(player.displaySkillInfo());
        buttonChoice("Skill");
        nextPosition("Skill"); 
    }

    // Magic screen
    public void magicScreen(){
        layout.mainTextArea.setText(player.displayMagicInfo());
        buttonChoice("Magic");
        nextPosition("Magic");       
    }

    // Items screen
    public void itemScreen(){
        layout.mainTextArea.setText(player.displayItemCount());
        buttonChoice("Item");
        nextPosition("Item");
    }

    // Check descriptions of skills and items
    public void help(int category){
        layout.mainTextArea.setFont(layout.helpFont);

        // Toggle information based on category
        switch(category){
            case 0:
                layout.mainTextArea.setText("");
                break;
            case 1:
                layout.mainTextArea.setText(player.displaySkillHelp());
                break;
            case 2:
                layout.mainTextArea.setText(player.displayItemInfo());
                break;
            case 3:
                layout.mainTextArea.setText(player.displayMagicHelp());
                break;
        }
    
        buttonChoice("Skills", "Items", "Magic", "Cancel", "", "");
        nextPosition("help-skills", "help-items", "help-magic", "fightInfo", "", "");
    }

    // Use item
    public void useItem(String name){
        int position = player.checkItem(name);
        if(position == -1) return;
        player.useItem(position);
        layout.mainTextArea.setText(player.displayItemCount());
        if(name == "Mana Potion") setPlayerMana();
        else if(name == "Health Potion") setPlayerHealth();
    }

    // Attack enemy
    public void attack(){
        player.playerTurnOrder = "first";
        int damageDealt = choices.damage(player.attackDamage, 0, 1);
        damageDealt = choices.attack(damageDealt);
        damageEnemy(damageDealt);
        buttonChoice(">", "", "", "", "", "");
        nextPosition((monster.enemyHealth < 1) ? "enemyDead" : "enemyAction", "", "", "", "", "");
    }

    // Use skill screen
    public void useSkill(String name){
        int position = player.checkSkill(name);
        if(position == -1) return;
        if(player.mana < player.skills[position].mpCost) return;
        player.useSkill(position);
        layout.mainTextArea.setText("You used " + name + "!\n\n" + player.skills[position].message);
        setPlayerMana();

        // set button text and location based on skill used
        switch(name){
            case "Guardian Strike": 
                buttonChoice(">", "", "", "", "", ""); 
                nextPosition("defend-enemyAction", "", "", "", "", ""); 
                break;
            case "Counter Force":
                buttonChoice(">", "", "", "", "", ""); 
                nextPosition("counter-enemyAction", "", "", "", "", "");             
                break;
        }  
    }

    // Skill Aftermath screen
    public void skillEffect(String name){
        int damageDealt;
        switch(name){
            case "Guardian Strike": 
                damageDealt = choices.damage(player.attackDamage, 0, 1);
                damageEnemy(damageDealt);
                buttonChoice(">", "", "", "", "", ""); 
                nextPosition((monster.enemyHealth < 1) ? "enemyDead" : "fightInfo", "", "", "", "", "");
                player.currentSkill = "";
                break;
            case "Counter Force":
                damageDealt = choices.counter();
                damageEnemy(damageDealt);
                buttonChoice(">", "", "", "", "", ""); 
                nextPosition((monster.enemyHealth < 1) ? "enemyDead" : "fightInfo", "", "", "", "", "");
                player.currentSkill = "";             
                break;
        }   
    }

    // Casts spell
    public void useMagic(String name){
        int position = player.checkSpell(name);

        if(position == -1) return;
        if(player.mana < player.magic[position].mpCost) return;

        // Generate damage values for player     
        int damageDealt = player.magic[position].damage;
        player.useMagic(position);
        setPlayerMana();

        // Damage inflicted to enemy calculation
        monster.setHealth(-damageDealt);
        layout.mainTextArea.setText("You cast " + name + "!\n\nYou strike the " + monster.name + " for " + damageDealt + " damage.");
        buttonChoice(">", "", "", "", "", "");
        nextPosition((monster.enemyHealth < 1) ? "enemyDead" : "enemyAction", "", "", "", "", "");
    }

    // Player Dead Screen
    public void playerDead(){
        choices.updateScore();
        layout.mainTextArea.setText("You limp out of the dungeon, weak from battle. \n\nGAME OVER!\n\nWhat would you like to do?");
        buttonChoice("Try again!", "Exit game", "", "", "", "");
        nextPosition("reset", "end", "", "", "", "");
        audio.playClip(audio.path + "lose.wav", "noloop");
    }

    // Exit game
    public void end(){
        layout.mainTextArea.setText(layout.jtf.getText() + "'s Final Score: " + player.score);
        layout.image = new ImageIcon(".//res//Images//end.jpg");
        layout.imageLabel.setIcon(layout.image);
        buttonChoice("", "", "", "", "", "");
        nextPosition("", "", "", "", "", "");
        layout.choiceButtonPanel.setVisible(false);
        audio.playClip(audio.path + "credits.wav", "loop");
    }

    // Enemy dead
    public void enemyDead(){

        // Score calculation
        choices.updateScore();

        String output = "";
        player.numEnemiesDefeated++;
        output = (player.numEnemiesDefeated == 6) ? output + "\nYou have learned the magic spell Fire!" : output;

        if(player.numEnemiesDefeated % 10 == 0) {
            output += "\nYour HP and MP has been completely restored!";
            player.setHealth(player.maxHealth - player.health);
            player.setMana(player.maxMana - player.mana);
            setPlayerHealth();
            setPlayerMana(); 
        }

        layout.mainTextArea.setText(monster.name + " was defeated!\n" + output);
        buttonChoice("Result", "", "", "", "", "");
        nextPosition("fightResult", "", "", "", "", "");
        audio.playClip(audio.path + "win.wav", "noloop");
    }

    // Fight result
    public void fightResult(){
        String healthPotion = (player.items[player.checkItem("Health Potion")].dropItem()) ? ("The " + monster.name + " dropped a health potion!\n\n") : "";
        String manaPotion = (player.items[player.checkItem("Mana Potion")].dropItem()) ? ("The " + monster.name + " dropped a mana potion!\n\n") : "";
        String potion = (!healthPotion.equals("") && !manaPotion.equals("")) ? ("The " + monster.name + " dropped a health potion!\n\nThe " + monster.name + " dropped a mana potion!\n\n") : (healthPotion + manaPotion);
        layout.mainTextArea.setText(potion + "What would you like to do now? ");

        buttonChoice("Continue fighting", "Exit dungeon", "", "", "", "");
        nextPosition("encounter", "end", "", "", "", "");
    }

    // Enemy action after player attack
    public void enemyAction(boolean half, int dodge, int crit){
        
        // Generate damage values for enemy
        int damageTaken = choices.damage(monster.enemyAttackDamage, monster.attackBonus, monster.minAttack);
        if(half) damageTaken /= 2;
        damageTaken = choices.enemyAction(damageTaken, player.dodgeChance, monster.enemyCritChance + monster.critBonus, half);

        // Display text based on enemy action
        if(monster.deadlyAttack) layout.mainTextArea.setText("Dragon is storing power!");
        else takeDamage(damageTaken);

        // nextPosition depend on player health and player's last move used
        String nextPosition1 = choices.enemyNextPosition();
        buttonChoice(">", "", "", "", "", "");
        nextPosition(nextPosition1, "", "", "", "", "");
    }
}