package mygame.app.Enemy;

public class Warrior extends SuperMonster {

    public Warrior(){
        name = "Warrior";
        imagePath += "warrior.png";
        reset();
        bonusStats(10, 10, -10, -10);       
    }
}