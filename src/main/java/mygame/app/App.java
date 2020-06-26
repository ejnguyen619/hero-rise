package mygame.app;

import java.util.Random;
import java.util.Scanner;

import java.awt.event.*;

public class App 
{
    ChoiceHandler choiceHandler = new ChoiceHandler();
    UI ui = new UI();
    String[] nextPosition = new String[6];
    Audio audio = new Audio();
    Combat combat = new Combat(this,ui, audio);

    // System objects
    Random rand = new Random();
    Scanner sc = new Scanner(System.in);
    
    public static void main( String[] args )
    {
        // Start adventure
        new App();
    }

    public App(){
        ui.createUI(choiceHandler);
        audio.setFile(audio.path + "opening.wav");
        audio.play();
        audio.loop();
    }

    // Execute function based on nextPosition
    public class ChoiceHandler implements ActionListener {

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e){
            String yourChoice = e.getActionCommand();
            switch(yourChoice){
                case "start": ui.createGameScreen(choiceHandler); combat.defaultSetup(); break;
                case "c1": combat.selectPosition(nextPosition[0]); break;
                case "c2": combat.selectPosition(nextPosition[1]); break;
                case "c3": combat.selectPosition(nextPosition[2]); break;
                case "c4": combat.selectPosition(nextPosition[3]); break;
                case "c5": combat.selectPosition(nextPosition[4]); break;
                case "c6": combat.selectPosition(nextPosition[5]); break;
            }
        }
    }
}
