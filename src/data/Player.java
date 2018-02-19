package data;

import helpers.Clock;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

import static helpers.Artist.*;

public class Player {

    private TileGrid grid;
    private TileType[] types; // Array of all the tiles we got in game, Grass,Dirt,Water
    private int index;
    private WaveManager waveManager;
    private ArrayList<TowerCannon> towerList;
    private boolean leftMouseButtonDown;

    public Player(TileGrid grid, WaveManager waveManager){
        this.grid = grid;
        this.types = new TileType[3];
        this.types[0] = TileType.Grass;
        this.types[1] = TileType.Dirt;
        this.types[2] = TileType.Water;
        this.index = 0; // Default tile is 0, which is Grass
        this.waveManager = waveManager;
        this.towerList = new ArrayList<TowerCannon>();
        this.leftMouseButtonDown = false;
    }



    public void update() {

        for (TowerCannon t : towerList)
            t.update();


        // Handle Mouse Input
        if (Mouse.isButtonDown(0) && !leftMouseButtonDown) { // 0 is left click, 1 is right click
            towerList.add(new TowerCannon(QuickLoad("cannonbase"), grid.GetTile(Mouse.getX()/ 64, (HEIGHT - Mouse.getY() -1) / 64), 10, waveManager.getCurrentWave().getEnemyList()));
            // setTile();


        }
        leftMouseButtonDown = Mouse.isButtonDown(0);

        // Handle Keyboard Input
        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
               // System.out.println("right");  // Test if work
                Clock.ChangeMultiplier(0.2f);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
                // System.out.println("right");  // Test if work
                Clock.ChangeMultiplier(-0.2f);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_T && Keyboard.getEventKeyState()){
                towerList.add(new TowerCannon(QuickLoad("cannonbase"), grid.GetTile(18,9), 10, waveManager.getCurrentWave().getEnemyList()));
            }
        }
    }



}
