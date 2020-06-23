package mygame.app.Enemy;

public class Warrior extends SuperMonster {

    public Warrior(){
        name = "Warrior";
        reset();
        bonusStats(10, 10, -10, -10);       
    }
}