package mygame.app.Enemy;

public class Skeleton extends SuperMonster {
    
    public Skeleton(){
        name = "Skeleton";
        imagePath += "skeleton.jpg";
        reset();
        bonusStats(0,0,0,0);       
    }
}