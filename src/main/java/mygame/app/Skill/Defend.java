package mygame.app.Skill;

import mygame.app.Player;

public class Defend extends Abilities {

    public Defend(){
        name = "Guardian Strike";
        description = "Enemy attacks first. Player receives half damage. Player attack hits.";
        mpCost = 5;
        key = "defend-start";
        message = "You have taken a defensive stance.";
    }

    public void useSkill(Player player){
        player.playerTurnOrder = "second";
        player.currentSkill = "guardian";
        player.setMana(-5);
        player.hit = true;
        player.crit = false;      
    } 
}