package mygame.app.Enemy;

public class Zombie extends SuperMonster {

    public Zombie(){
        name = "Zombie";
        imagePath += "zombie.jpg";
        reset();
        bonusStats(5, -5, 0, -5);       
    }
}