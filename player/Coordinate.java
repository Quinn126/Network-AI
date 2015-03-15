/* Coordinate.java */

//This is a simple file that sets up the coordinates/direction for use in the game.

package player;

public enum Coordinate { 

  N(0, 0, -1), W(6, -1, 0), E(2, 1, 0), S(4, 0, 1), NW(7, -1, -1), NE(1, 1, -1), SW(5, -1, 1), SE(3, 1, 1);

  private int dcoord;
  private int xcoord;
  private int ycoord;

  /*
  *This takes in three parameters and returns provided values. 
  *The values include the x position and y position and the direction position for returned object.
  *@param: xcoord - x coordinate location, ycoord, y coordinate location, dcoord - direction index  
  */
  Coordinate(int dcoordinate, int xcoordinate, int ycoordinate) {
    xcoord = xcoordinate;
    ycoord = ycoordinate;
    dcoord = dcoordinate;
  }

  /*
  *This takes in no parameters and directly returns the index.
  */
  public int theDirect() {
    return dcoord;
  }

  /*
  *This takes in no parameters and directly returns "the x" coordinate.
  */
  public int theX() {
    return xcoord;
  }

  /*
  *This takes in no parameters and directly returns "the y" coordinate.
  */
  public int theY() {
    return ycoord;
  }
}