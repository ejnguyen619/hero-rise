package mygame.app.Inventory;

import mygame.app.Player;

public class ManaPotion extends Item {
    
    public ManaPotion(){
        name = "Mana Potion";
        description = "Recovers 25MP.";
        dropChance = 20;
        amount = 25;
        quantity = 1;
        key = "etherPotion";
    }
    
    public void useItem(Player player){
        if(player.mana == player.maxMana) return;
        if(quantity > 0){
            player.setMana(Math.min(player.maxMana - player.mana, amount));
            quantity--;
        }      
    }
}