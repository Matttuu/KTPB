package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import static helpers.Artist.*;

public class Player {

    private TileGrid grid;
    private TileType[] types; // Array of all the tiles we got in game, Grass,Dirt,Water
    private int index;

    public Player(TileGrid grid){
        this.grid = grid;
        this.types = new TileType[3];
        this.types[0] = TileType.Grass;
        this.types[1] = TileType.Dirt;
        this.types[2] = TileType.Water;
        this.index = 0; // Default tile is 0, which is Grass
    }

    public void SetTile(){
        grid.SetTile((int)Math.floor(Mouse.getX() / 64),
                (int)Math.floor((HEIGHT - Mouse.getY() - 1) / 64), types[index]);
    }

    public void Update() {
        if (Mouse.isButtonDown(0)) // 0 is left click, 1 is right click
            SetTile();

        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
               // System.out.println("right");  // Test if work
                MoveIndex();
            }
        }
    }

    private void MoveIndex() {
        index++;
        if (index > types.length - 1){
            index = 0;
        }
    }

}
