package mygame.app.Enemy;

public class Zombie extends SuperMonster {

    public Zombie(){
        name = "Zombie";
        reset();
        bonusStats(5, -5, 0, -5);       
    }
}