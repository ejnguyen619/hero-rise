package mygame.app;

import java.util.Random;
import java.util.Scanner;

import java.awt.event.*;

public class App 
{
    ChoiceHandler choiceHandler = new ChoiceHandler();
    KeyHandler keyHandler = new KeyHandler();
    Layout layout = new Layout();
    String[] nextPosition = new String[6];
    Audio audio = new Audio();
    Screen screen = new Screen(this,layout, audio);

    // System objects
    public static Random rand = new Random();
    Scanner sc = new Scanner(System.in);
    
    public static void main( String[] args )
    {
        // Start adventure
        new App();
    }

    public App(){
        layout.createStartScreen(choiceHandler, keyHandler);
        screen.startScreen();
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
                case "start": layout.createGameScreen(choiceHandler); screen.defaultSetup(); break;
                case "c1": screen.selectPosition(nextPosition[0]); break;
                case "c2": screen.selectPosition(nextPosition[1]); break;
                case "c3": screen.selectPosition(nextPosition[2]); break;
                case "c4": screen.selectPosition(nextPosition[3]); break;
                case "c5": screen.selectPosition(nextPosition[4]); break;
                case "c6": screen.selectPosition(nextPosition[5]); break;
            }
        }
    }

    // Handle user input for name in title screen
    public class KeyHandler implements KeyListener{

        @Override
        public void keyPressed(KeyEvent e) {  
        }

        @Override
        public void keyTyped(KeyEvent e) { 
        }

        @Override
        public void keyReleased(KeyEvent e) {
            layout.name();
        }
    }
}
