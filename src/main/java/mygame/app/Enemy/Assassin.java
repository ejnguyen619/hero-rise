package mygame.app.Enemy;

public class Assassin extends SuperMonster {
    
    public Assassin(){
        name = "Assassin";
        reset();
        bonusStats(-10, -5, 20, 30);     
    }
}