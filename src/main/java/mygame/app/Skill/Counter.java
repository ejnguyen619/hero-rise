package mygame.app.Skill;

import mygame.app.Player;

public class Counter extends Abilities {

    public Counter(){
        name = "Counter Force";
        description = "Player dodge rate up. Enemy attacks first. If enemy attack misses, player performs critical hit.";
        mpCost = 5;
        key = "counter-start";
        message = "You have taken an evasive stance.";
    }

    public void useSkill(Player player){
        player.playerTurnOrder = "second";
        player.currentSkill = "counter";
        player.setMana(-5);
        player.dodgeChance *= 3;      
    } 
}