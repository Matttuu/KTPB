package data;

import helpers.Clock;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

import static helpers.Artist.*;

public class Boot {
    // Constructer / Method
    public Boot(){

        BeginSession();

        int[][] map = {

                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,1,1,0,1,1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,1,1,0,1,1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,1,2,2,2,2,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},

        };

        TileGrid grid = new TileGrid(map);
        grid.SetTile(3, 4, grid.GetTile(2,4).getType());
        Enemy e = new Enemy(QuickLoad("UFO64"), grid.GetTile(10,10),64,64, 3);
        // This is where all the code for running the game is going.
        while(!Display.isCloseRequested()){ // When we are not hitting the exit button, the game will run this.
            Clock.update();
            e.Update();

            grid.Draw();
            e.Draw();


            Display.update();
            Display.sync(60);
        }
        Display.destroy(); // End game
    }

    public static void main(String[] args) {
        new Boot();
    }
}
