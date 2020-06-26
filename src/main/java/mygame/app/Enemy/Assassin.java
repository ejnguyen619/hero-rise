package mygame.app.Enemy;

public class Assassin extends SuperMonster {
    
    public Assassin(){
        name = "Assassin";
        imagePath += "assassin.jpg";
        reset();
        bonusStats(-10, -5, 20, 30);     
    }
}