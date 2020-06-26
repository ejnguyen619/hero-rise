package mygame.app.Enemy;

public class Dragon extends SuperMonster {
    
    public Dragon(){
        name = "Dragon";
        imagePath += "dragon.png";
        reset();
        bonusStats(20, 20, 10, 10);    
    }
}