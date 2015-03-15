/*Chip.java*/

package player;
 
public class Chip{
/* A chip class illustrates all the chips that'll be used in the game;
  the space used to store the chips;
  fields are chip's location, neighbors and color. Chips have a color of black or white.  
  This will monitor the various playing chips we place on the playboard.
  We keep track of what connections each chip has when in play. */
    final static int DEFAULT = 8;
    private int[] neighbors;
    private int color;
    private int x;
    private int y;
 
 
 
// This takes in no parameters and is a constructor to prepare an empty space for a chip.
    public Chip() {
          color = 0; //
          neighbors = new int[8];
      
    }
//This takes in four parameters and is a constructor to prepare a chip with the provided information
//The xy location on the board, color, and playboard being utilized are all provided.
    public Chip(int locx, int locy, int color, PlayBoard playBoard) {
      x = locx;
      y = locy;
      this.color = color;
      neighbors = new int[8]; 
      turnRevision(locx, locy, playBoard); //Be sure to make sure all information is up to date.
    }
//This takes in one parameter and is a constructor to prepare a chip with the provided chip by creating an identical copy to use.
    public Chip(Chip playChip) {
      if (playChip == null) { //Check if the playchip has anything that isn't null.
        return;
      }
      neighbors = new int[8];

      for (int chess = 0; neighbors.length > chess; chess++){
        neighbors[chess] = playChip.neighbors[chess];
      }
      this.color = playChip.color;
      this.x = playChip.x;
      this.y = playChip.y;
    }



//These take in no parameters and are getters for invariant fields.
//The first one directly returns the x value, the second one directly returns the y value, and
//the third one directly returns the color value.
    public int theX(){
      return x;
    }
    public int theY(){
      return y;
    }
    public int theColor(){
        return color;
    }


//This takes in three parameters and makes sure that when a chip is placed at a new area of playboard,
//the previous area receives updated information.
    public void turnRevision(int locx, int locy, PlayBoard playBoard){

      for (Coordinate coord : Coordinate.values()) {
        boolean beyondParameters = true;
        int otherx = coord.theX();
        int othery = coord.theY();
        int chess = 1;

        while (!playBoard.inBounds(locx + otherx*chess, locy + othery*chess, color)) {
          int tester = playBoard.whichChip(locx + otherx*chess, locy + othery*chess);
          
          if (tester != 0) {
            neighbors[coord.theDirect()] = tester;
            Chip connect = playBoard.obtainChip(tester);
            connect.neighbors[(coord.theDirect() + 4)%8] = playBoard.whichChip(locx, locy);
            beyondParameters = false;
            break;
          }
          chess++;
        }
        if (beyondParameters) {
          neighbors[coord.theDirect()] = 0;
        }
      }
    }

//playStep allows for a step to happen that can move chips from one location to another or add a chip to another location.
//This also involves updating the neighbors around each chip.
    public void playStep(int locx, int locy, PlayBoard playBoard){

      for (int chess = 0; chess <= 7; chess++) {
        int count = this.getNeighbors(chess);
        if (count != 0) {
          playBoard.obtainChip(count).makeNeighbors((chess+4)%8, this.getNeighbors((chess+4)%8));
        }
      }
    }

//get_neighbors takes in one parameter and is a getter for the neighbors.
    public int getNeighbors(int locx){
      return neighbors[locx];
    }

//make_neighbors takes in two parameters and functions by constructing a new neighbor for the chip to acknowledge.
    public void makeNeighbors(int locx, int locy){
      neighbors[locx] = locy;
    }

 
}