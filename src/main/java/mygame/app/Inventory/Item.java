package mygame.app.Inventory;

import mygame.app.App;
import mygame.app.Player;

public class Item {
    public String name;
    public String description;
    public int dropChance;
    int amount;
    public int quantity;
    public String key;

    public void useItem(Player player){
        
    }

    public boolean dropItem(){
        if(App.rand.nextInt(100) < dropChance) {
            quantity++;
            return true;
        }       
        return false;
    }
}
