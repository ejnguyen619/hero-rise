package mygame.app;

import mygame.app.Inventory.*;
import mygame.app.Magic.*;
import mygame.app.Skill.*;

public class Player {

    Item[] items = new Item[100];
    Spell[] magic = new Spell[10];
    Abilities[] skills = new Abilities[10];
    public int maxHealth;
    public int maxMana;
    public int health;
    public int mana;
    int attackDamage;
    int numEnemiesDefeated;
    int criticalChance;
    public int dodgeChance;
    int score;
    public boolean hit;
    public boolean crit;
    public String playerTurnOrder;
    public String currentSkill;

    // INITIALIZATION METHODS --------------------------------------------------------------------------------------------------------

    public Player(){
        reset();
        setSubScreen();
    }

    public void setSubScreen(){
        items[0] = new HealthPotion();
        items[1] = new ManaPotion();
        magic[0] = new Fire();
        skills[0] = new Defend();
        skills[1] = new Counter();
    }

    // BASE METHODS --------------------------------------------------------------------------------------------------------

    // Initial Player Stats
    public void reset(){
        health = 100;
        attackDamage = 50;
        numEnemiesDefeated = 0;
        criticalChance = 15;
        dodgeChance = 15;
        mana = 50;
        maxHealth = health;
        maxMana = mana;
        score = 0;
        hit = false;
        crit = false;     
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

    // Count number of chars in a string
    public int countChar(String string){
        int count = 0;
        for(int i = 0; i < string.length(); i++){
            if(String.valueOf(string.charAt(i)) != "") count++;
        }
        return count;
    }

    // Print out white spaces
    public String whiteSpace(int spaces){
        String output = "";
        for(int i = 0; i < spaces; i++){
            output += " ";
        }
        return output;
    }

    // ACCESS SCREEN METHODS --------------------------------------------------------------------------------------------------------

    // Select method to print out button text
    public String getName(String screen, int position){
        String output = "";
        switch(screen){
            case "Skill": output = showSkillName(position); break;
            case "Magic": output = showMagicName(position); break;
            case "Item": output = showItemName(position); break;
        }
        return output;
    }

    // Select method to determine location of button choice
    public String getKey(String screen, int position){
        String output = "";
        switch(screen){
            case "Skill": output = showSkillKey(position); break;
            case "Magic": output = showMagicKey(position); break;
            case "Item": output = showItemKey(position); break;
        }
        return output;
    }

    // ITEM METHODS --------------------------------------------------------------------------------------------------------

    // Find item in inventory
    public int checkItem(String name){
        int item = -1;
        for(int i = 0; i < items.length; i++){
            if(items[i].name == name) {
                item = i;
                break;
            }
            if(items[i+1] == null) break;
        }
        return item;
    }

    // Display Item info for help screen
    public String displayItemInfo(){
        String output = "";
        for(int i = 0; i < items.length; i++){
            output += items[i].name + ":\n" + items[i].description + ((items[i+1] != null) ? "\n\n" : "");
            if(items[i+1] == null) break;
        }
        return output;
    }

    // Display item count for item screen
    public String displayItemCount(){
        String output = "";
        for(int i = 0; i < items.length; i++){
            output += items[i].name + ":   x" + items[i].quantity + ((items[i+1] != null) ? "\n" : "");
            if(items[i+1] == null) break;
        }
        return output;       
    }

    public void addItem(int i){
        if(items[i].quantity < 99) items[i].quantity++;
    }

    public void useItem(int i){
        items[i].useItem(this);       
    }

    public String showItemName(int position){
        if(items[position] == null) return "";
        return items[position].name;
    }

    public String showItemKey(int position){
        if(items[position] == null) return "";
        return items[position].key;
    }

    // MAGIC METHODS --------------------------------------------------------------------------------------------------------

    // Find magic spell in inventory
    public int checkSpell(String name){
        int spell = -1;
        for(int i = 0; i < items.length; i++){
            if(magic[i].name == name) {
                spell = i;
                break;
            }
            if(magic[i+1] == null) break;
        }
        return spell;
    }

    // Display Magic info for help screen
    public String displayMagicHelp(){
        String output = "";
        for(int i = 0; i < magic.length; i++){
            output += magic[i].name + ":\n" + magic[i].description + ((magic[i+1] != null) ? "\n\n" : "");
            if(magic[i+1] == null) break;
        }
        return output;
    }

    // Display Magic info for magic screen
    public String displayMagicInfo(){
        String output = "";
        for(int i = 0; i < magic.length; i++){
            output += magic[i].name + ":             " + magic[i].mpCost + "MP" + ((magic[i+1] != null) ? "\n" : "");
            if(magic[i+1] == null) break;
        }
        return output;       
    }

    public void useMagic(int i){
        magic[i].useMagic(this);       
    }

    public String showMagicName(int position){
        if(magic[position] == null) return "";
        return magic[position].name;
    }

    public String showMagicKey(int position){
        if(magic[position] == null) return "";
        return magic[position].key;
    }

    // SKILL METHODS --------------------------------------------------------------------------------------------------------

    // Find skill in inventory
    public int checkSkill(String name){
        int skill = -1;
        for(int i = 0; i < items.length; i++){
            if(skills[i].name == name) {
                skill = i;
                break;
            }
            if(skills[i+1] == null) break;
        }
        return skill;
    }

    // Display Skill info for help screen
    public String displaySkillHelp(){
        String output = "";
        for(int i = 0; i < skills.length; i++){
            output += skills[i].name + ":\n" + skills[i].description + ((skills[i+1] != null) ? "\n\n" : "");
            if(skills[i+1] == null) break;
        }
        return output;
    }

    // Display Skill info for magic screen
    public String displaySkillInfo(){
        String output = "";
        for(int i = 0; i < skills.length; i++){
            output += skills[i].name + ":            " + skills[i].mpCost + "MP" + ((skills[i+1] != null) ? "\n" : "");
            if(skills[i+1] == null) break;
        }
        return output;       
    }

    public void useSkill(int i){
        skills[i].useSkill(this);       
    }

    public String showSkillName(int position){
        if(skills[position] == null) return "";
        return skills[position].name;
    }

    public String showSkillKey(int position){
        if(skills[position] == null) return "";
        return skills[position].key;
    }
}