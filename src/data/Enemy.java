package data;

import org.newdawn.slick.opengl.Texture;
import java.util.ArrayList;
import static helpers.Artist.*;
import static helpers.Clock.*;

public class Enemy {
    private int width, height, health, currentCheckpoint;
    private float speed, x, y;
    private Texture texture;
    private Tile startTile;
    private boolean first = true, alive = true; // First time this is being run // Small fix
    private TileGrid grid;
    private ArrayList<Checkpoint> checkpoints;
    private int[] directions;

    public Enemy(Texture texture, Tile startTile, TileGrid grid, int width, int height, float speed){
        this.texture = texture;
        this.startTile = startTile;
        this.x = startTile.getX();
        this.y = startTile.getY();
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.grid = grid;
        this.checkpoints = new ArrayList<Checkpoint>();
        this.directions = new int[2];
        // X Direction
        this.directions[0] = 0;
        // Y Direction
        this.directions[1] = 0;
        directions = FindNextD(startTile);
        this.currentCheckpoint = 0;
        PopulateCheckpointList(); // This is going through and fills the entire checkpoint with each turn in the maze,
                                  // so the 'AI' knows what to do
    }

    public void Update() {
        if (first)
            first = false;
        else {

            // When we reach the next checkpoint, it'll parse on the next direction by upping the currentCheckpoint by 1
            if (CheckpointReached()){

                // The +1, makes sure we check if the next checkpoint exists before we move onto the next one.
                if (currentCheckpoint + 1 == checkpoints.size())
                    Die(); // Tells our enemies to die when they reached end of the maze
                     // System.out.println("Enemy Reached End of Maze"); // Debugging purposes.
                else
                    currentCheckpoint++;

            } else{
                x += Delta() * checkpoints.get(currentCheckpoint).getxDirection() * speed;
                y += Delta() * checkpoints.get(currentCheckpoint).getyDirection() * speed;
            }
        }
    }

    private boolean CheckpointReached(){
        // If enemy reached checkpoint, pass them on to the next checkpoint.
        boolean reached = false;
        Tile t = checkpoints.get(currentCheckpoint).getTile();
        // x & y, are our current enemy position, so were checking if the x position is greater than the next position.
        // Check if poisition reached tile within variance of 3 (arbitrary)
        if (x > t.getX() + - 3 &&
                x < t.getX() + 3 &&
                y > t.getY() - 3  &&
                y < t.getY() + 3 ) {
            reached = true;
            x = t.getX();
            y = t.getY();
        }

        return reached;
    }

    private void PopulateCheckpointList(){
        // Adding first checkpoint which is based on our start tile
        // Taking our arraylist of checkpoints of all the turns in the map, and then were adding a new one, where we use
        // 'FindNextC', and sending the start tile and direction to 'FindNextD'.

        checkpoints.add(FindNextC(startTile, directions = FindNextD(startTile)));

        int counter = 0;
        // As long as we continue to loop, it'll keep searching until it find what were looking for we set it to false and stop the loop.
        boolean cont = true;
        while (cont){
            int[] currentD = FindNextD(checkpoints.get(counter).getTile());
            // Check if a next direction/checkpoint exists, end after 20 checkpoints (arbitrary)
            if (currentD[0] == 2 || counter == 20) {
                cont = false;
            } else {
                checkpoints.add(FindNextC(checkpoints.get(counter).getTile(),
                        directions = FindNextD(checkpoints.get(counter).getTile())));
            }
            counter++;
        }
    }

    private Checkpoint FindNextC(Tile s, int[] dir) {
        Tile next = null;
        Checkpoint c = null;

        // Boolean to decide if next checkpoint is found
        boolean found = false;

        // Integer to increment each loop
        int counter = 1;

        while(!found){
         if (s.getXPlace()+ dir[0] * counter == grid.getTilesWide() ||
                s.getYPlace() + dir[1] == grid.getTilesHigh() ||
                s.getType() !=
                     grid.GetTile(s.getXPlace()+ dir[0] * counter,
                    s.getYPlace() + dir[1] * counter).getType()){
                found = true;
                // Move counter back 1 to find tile before new tiletype.
                counter -= 1;
                next  = grid.GetTile(s.getXPlace()+ dir[0] * counter,
                        s.getYPlace() + dir[1] * counter);
            }
            counter++;
        }

        c = new Checkpoint(next, dir[0], dir[1]);
        return c;
    }

    // Pass the tile where the enemies are right now, and it will return a set of integers.
    private int[] FindNextD(Tile s) {
        int[] dir = new int[2];
        Tile u = grid.GetTile(s.getXPlace(), s.getYPlace() -1); // Tile u is Up.
        Tile r = grid.GetTile(s.getXPlace() +1, s.getYPlace()); // Tile r is Right.
        Tile d = grid.GetTile(s.getXPlace(), s.getYPlace() +1); // Tile d is Down.
        Tile l = grid.GetTile(s.getXPlace() - 1, s.getYPlace()); // Tile l is Left.
    // If the tile above the enemy is the same as the enemies so if we can go up and our direction is not currently down
        // it'll go up.
        if (s.getType() == u.getType() &&  directions[1] != 1 ) {
            dir[0] = 0;
            dir[1] = -1;
        }else if (s.getType() == r.getType()  &&  directions[0] != -1 ) {
            dir[0] = 1;
            dir[1] = 0;
        }else if (s.getType() == d.getType()  &&  directions[1] != -1 ) {
            dir[0] = 0;
            dir[1] = 1;
        }else if (s.getType() == l.getType()  &&  directions[0] != 1 ) {
            dir[0] = -1;
            dir[1] = 0;
        }else {
            dir[0] = 2;
            dir[1] = 2;

        }
        return dir;
    }

    private void Die() {
        alive = false;
    }

    /*private boolean pathContinues(){
        boolean answer = true;

        Tile myTile = grid.GetTile((int) (x / 64), (int) (y / 64)); // Our actual x might be something like 700, but since our tiles are 64 we divide x with 64.
        Tile nextTile = grid.GetTile((int) (x / 64) +1, (int) (y / 64)); // 1 tile over to the right is the next tile

        if(myTile.getType() != nextTile.getType())
            answer = false;

        return answer;
    }*/

    public void Draw() {
        DrawQuadTex(texture, x, y, width, height);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Tile getStartTile() {
        return startTile;
    }

    public void setStartTile(Tile startTile) {
        this.startTile = startTile;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public TileGrid getTileGrid(){
        return grid;
    }

    public boolean isAlive(){
        return alive;
    }
}
