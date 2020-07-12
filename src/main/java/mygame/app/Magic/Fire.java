package mygame.app.Magic;

import mygame.app.Player;

public class Fire extends Spell{

    public Fire(){
        name = "Fire";
        description = "Inflicts 25 damage to enemy.";
        damage = 25;
        mpCost = 10;
        key = "fire";
    }

    public void useMagic(Player player){
        player.playerTurnOrder = "first";
        player.hit = true;
        player.crit = false;
        player.setMana(-10);       
    }
    
}