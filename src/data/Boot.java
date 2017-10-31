package data;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

import static helpers.Artist.*;

public class Boot {
    // Constructer / Method
    public Boot(){

        BeginSession();


       Tile tile = new Tile(0,0,64,64,TileType.Grass);
       Tile tile2 = new Tile (0,64,64,64, TileType.Dirt);
        // This is where all the code for running the game is going.
        while(!Display.isCloseRequested()){ // When we are not hitting the exit button, the game will run this.

            tile.Draw();
            tile2.Draw();


            Display.update();
            Display.sync(60);
        }
        Display.destroy(); // End game
    }

    public static void main(String[] args) {
        new Boot();
    }
}
