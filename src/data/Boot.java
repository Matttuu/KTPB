package data;

import helpers.Clock;
import helpers.StateManager;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

import static helpers.Artist.*;

public class Boot {
    // Constructer / Method
    public Boot(){

        BeginSession();


       //Game game = new Game(map);
               while(!Display.isCloseRequested()){ // When we are not hitting the exit button, the game will run this.
            Clock.update();


            //game.update();
                   StateManager.update();

            Display.update();
            Display.sync(60);
        }
        Display.destroy(); // End game
    }

    public static void main(String[] args) {
        new Boot();
    }
}
