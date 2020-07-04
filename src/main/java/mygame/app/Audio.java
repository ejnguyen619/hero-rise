package mygame.app;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {
    
    Clip clip;
    String path = ".//res//Music//";
    boolean active = false;
        
    public void setFile(String soundFileName){

        try {
            File file = new File(soundFileName);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        }
        catch(Exception e){

        }
    }

    public void play(){
        if(!active){
            active = true;
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        if(active){
            active = false;
            clip.stop();
            clip.close();
        }
    }

    public void playClip(String path, String loop){
        stop();
        setFile(path);
        play();
        if(loop == "loop") loop();
    }
}