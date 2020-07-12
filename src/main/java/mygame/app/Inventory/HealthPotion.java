package mygame.app.Inventory;

import mygame.app.Player;

public class HealthPotion extends Item {

    public HealthPotion(){
        name = "Health Potion";
        description = "Recovers 30HP.";
        dropChance = 30;
        amount = 30;
        quantity = 3;
        key = "healthPotion";
    }
    
    public void useItem(Player player){
        if(player.health == player.maxHealth) return;
        if(quantity > 0){
            player.setHealth(Math.min(player.maxHealth - player.health, amount));
            quantity--;
        }     
    }
}