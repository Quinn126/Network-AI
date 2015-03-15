/* PlayBoard.java */
 
package player;
import list.NumberList;

//This class is meant to set up the master board where the game shall be played on.
//This utilizes a two dimensional array that will hold the possible areas on this playboard.
//There is another Chip dimension that will hold the locations of the chips placed in the game.
 
public class PlayBoard {
    private final static int BLACK = 0;
    private final static int WHITE = 1;
    final static int DIM = 8;
     
    private Chip[] chipBoard;
    private int[][] allPlacement;
    private int blackCount = 10;
    private int whiteCount = 10;
    private int CHIP_NUMBER = 20;
    private int chipCount;

    //This takes in zero parameters and constructs a board with default conditions.
    //No chips should exist on the board.
     
    public PlayBoard() {
        allPlacement = new int[DIM][DIM];
        chipBoard = new Chip[CHIP_NUMBER+1];
    }

    //This takes in three parameters to construct a board that has changes due to a move made on the board.
    
    public PlayBoard(PlayBoard theBoard, Move theMove, int thePlayer) {
        this();

        for(int x = 0; x < DIM; x++) {
            for(int y = 0; y < DIM; y++) {
                this.allPlacement[x][y] = theBoard.allPlacement[x][y];
            }
        }
        for (int x = 0; x < CHIP_NUMBER+1; x++) {
            if(theBoard.chipBoard[x] != null) {
                chipBoard[x] = new Chip(theBoard.chipBoard[x]);
            }
        }
        this.joinMove(theMove, thePlayer);
    } 


    //This evinces the various areas present on the playboard.

    public int[][] obtainPlacement(){
        return allPlacement;
    }

    //This manipuates a specific area on the playboard so a value can be applied to an xy place.
    public void applyPlacement(int locx, int locy, int number) {
        allPlacement[locx][locy] = number;
        return;
    }

    //This contains the chip objects that are placed on the playboard.

    public Chip[] obtainChips() {
        return chipBoard;
    }

    //This returns the chip situated at the specified position on the chip holder.

    public Chip obtainChip(int here) {
        return chipBoard[here];
    }

    //This returns the chip at the specified position from the playboard. 

    public int whichChip(int locx, int locy) {
        return allPlacement[locx][locy];
    }

    //This tells you how many chips can be placed on the playboard.

    public int allChips() {
        return this.CHIP_NUMBER;
    }

    //This applies the effects of a move on the playboard if it is a legal move.

    public boolean joinMove(Move playMove, int thePlayer) {
        if(!moveValidity(playMove, thePlayer)) {
            return false;
        }
        if(playMove.moveKind == Move.QUIT){
            return true;
        }

        if(playMove.moveKind == Move.ADD){
            allPlacement[playMove.x1][playMove.y1] = thePlayer;
            chipBoard[thePlayer] = new Chip(playMove.x1, playMove.y1, thePlayer%2, this);

        } else if(playMove.moveKind == Move.STEP){
            if(chipBoard[allPlacement[playMove.x2][playMove.y2]].theColor() != thePlayer%2 || allPlacement[playMove.x2][playMove.y2] == 0){
                return false;
            }

            int indexChip = this.whichChip(playMove.x2, playMove.y2);
            applyPlacement(playMove.x2, playMove.y2, 0);
            this.obtainChip(indexChip).playStep(playMove.x1, playMove.y1, this);
            this.chipBoard[indexChip] = new Chip(playMove.x1, playMove.y1, thePlayer%2, this);
            applyPlacement(playMove.x1, playMove.y1, indexChip);
        }
        return true;
    }



    /**
     * This evaluates the playboard.
     */
    public int evaluation(int playColor) {
        int eval = 0;
        int initial1 = 1;
        int initial2 = 1;
        int playerX;
        int playerY;
        int rivalX;
        int rivalY;

        if (playColor == 0) {
            initial1 += 1;
        }
        if (initial2 == 0) {
            initial2 += 1;
        }
        while (initial1 < CHIP_NUMBER || initial2 < CHIP_NUMBER) { 
            Chip playerChips = chipBoard[initial1];
            Chip rivalChips = chipBoard[initial2]; //I haven't give a full look at the chips file yet so the names could be off...
            if (playerChips == null) {
                playerX = -1;
                playerY = -1;
            } else {
                playerX = playerChips.theX();
                playerY = playerChips.theY();
            }
            if (rivalChips == null) {
                rivalX = -1;
                rivalY = -1;
            } else {
                rivalX = rivalChips.theX();
                rivalY = rivalChips.theY();
            }
            for (Coordinate c : Coordinate.values()) { //check my coordinate file for more information
                int coordx = c.theX();
                int coordy = c.theY();
                if ((playerX == 0 && coordx != 1) || (playerY == 0 && coordy != 1) || playerX == -1 || (playerX == 7 && coordx != -1) || (playerY == 7 && coordy != -1)) {
                    continue;
                } else {
                    eval += searchBoard(playerX, playerY, coordx, coordy, playColor);
                }
                if ((rivalX == 0 && coordx != 1) || (rivalY == 0 && coordy != 1) ||  rivalX == -1 || (rivalX == 7 && coordx != -1) || (rivalY == 7 && coordy != -1)) {
                    continue;
                } else {
                    eval -= searchBoard(rivalX, rivalY, coordx, coordy, (playColor + 1)%2); 
                }
            }
            initial1 += 2;
            initial2 += 2;
        }

        if (this.goalsOccupied(playColor) == true) { 
            eval += 2;
        }
        return eval;
    }

    //This evaluates using the specified coordinates a value for the direction that is being searched until it reaches the end.
    public int searchBoard(int locx, int locy, int changeX, int changeY, int playColor){
        int result = 0;
        int num = 1;

        while(8 > 1) {
            if(within(locx+num*changeX, locy+num*changeY, playColor)){
                if((playColor == 1 && (locx+num*changeX == 0 || locx+num*changeX == 7)) || (playColor == 0 && (locy+num*changeY == 0 || locy+num*changeY == 7))){
                    result += 1;

                    if(allPlacement[locx+num*changeX][locy+num*changeY] != 0) {
                        if(allPlacement[locx+num*changeX][locy+num*changeY]%2 == playColor) {
                            result += 2;
                        }
                    }
                    return result;
                }
            } else {
                return result;
            }

            if(allPlacement[locx+num*changeX][locy+num*changeY] != 0) {
                if(allPlacement[locx+num*changeX][locy+num*changeY]%2 == playColor){
                    result += 2;
                }
                return result;
            }
            num += 1; //
        }
    }

    //This checks whether the network is valid and returns true or false.
    public boolean networkValidity(int playColor, int size, int lastPath, Chip presentChip, NumberList completed) {
        int presentOne = this.whichChip(presentChip.theX(), presentChip.theY());

        if(completed.hasIt(presentOne)) {
            return false;
        } else {
            completed.joinList(presentOne);
        }

        if(presentChip.theX() == 7 || presentChip.theY() == 7) {
            if (size >= 5) {
                return true;
            }
            return false;
        }

        for(Coordinate coor : Coordinate.values()) {
            if(coor.theDirect() == lastPath) {
                continue;
            }
            int numNearby = presentChip.getNeighbors(coor.theDirect());
            if(numNearby == 0) {
                continue;
            }
            Chip afterOne = this.obtainChip(numNearby);
            if(afterOne.theX() == 0 || afterOne.theY() == 0 || afterOne.theColor() != playColor) {
                continue;
            }

            boolean legalAction = networkValidity(playColor, size+1, coor.theDirect(), afterOne, new NumberList(completed));
            if(legalAction) {
                return true;
            }
        }
        return false;
    }

    //This determines if it is still possible to add chips by checking if the turn threshold has been passed.
    public boolean betterToAdd(int playTurn) {
        if (playTurn > CHIP_NUMBER){
            return false;
        }
        return true;
    }

    //This determines if it is legal to make the move the player wants to make.
    public boolean moveValidity(Move playMove, int playTurn) {
        int playColor = playTurn%2;

        if(allPlacement[playMove.x1][playMove.y1] != 0) {
            return false;
        }
        if(!within(playMove.x1, playMove.y1, playColor) || doubleChip(playMove, playColor)){
            return false;
        }

        if(playMove.x2 != 0 && playMove.y2 != 0 && Move.STEP == playMove.moveKind){
            boolean shiftPosition = (playMove.y1 == playMove.y2) && (playMove.x2 == playMove.x1);
            if(betterToAdd(playTurn) || shiftPosition || !within(playMove.x2, playMove.y2, playColor)) {
                return false;
            }
        } else if(Move.ADD == playMove.moveKind){
            return betterToAdd(playTurn);
        }
        return true;
    }

    //This determines whether the specified chip would be out of the playboard's bounds or not.
    public boolean within(int locx, int locy, int playColor){ //check if you've named color already
        boolean secondGoal;

        if(playColor == MachinePlayer.WHITE){
            secondGoal = (locy == PlayBoard.DIM-1) || (locy == 0);
        } else {
            secondGoal = (locx == PlayBoard.DIM-1) || (locx == 0);
        }

        if(secondGoal || inBounds(locx, locy, playColor)) {
            return false;
        }
        return true;
    }

    //This determines whether the specified chip location would be at a corner or past the bounds of the board.
    public boolean inBounds(int locx, int locy, int playColor) {
        boolean goneBeyond = (locx >= DIM) || (locx < 0) || (locy >= DIM) || (locy < 0);
        boolean atEdge = (locx == DIM-1 && locy == DIM-1) || (locx == DIM-1 && locy == 0) || (locx == 0 && locy == DIM-1) || (locx == 0 && locy == 0);
        return atEdge || goneBeyond; 
    }

    //This determines whether there exists at the very least a single chip occupying each of the two goals.
    public boolean goalsOccupied(int playColor){
        boolean chipAtOne = false;
        boolean chipAtTwo = false;

        if(playColor == MachinePlayer.WHITE){
            for(int x = 1; x < 7; x++){
                if(allPlacement[0][x] != 0){
                    chipAtOne = true;
                }
                if(allPlacement[7][x] != 0){
                    chipAtTwo = true;
                }
            }
        } else {
            for(int y = 1; y < 7; y++){
                if(allPlacement[y][0] != 0){
                    chipAtOne = true;
                }
                if(allPlacement[y][7] != 0){
                    chipAtTwo = true;
                }
            }
        }
        return chipAtOne && chipAtTwo;
    }

    //This checks if there are two chips next to one another.
    public boolean doubleChip(Move playMove, int playTurn){
        int playColor = playTurn%2;
        int neighborCount = 0;
        int presentStep = 0;

        if(playMove.moveKind == Move.STEP){
            presentStep = allPlacement[playMove.x2][playMove.y2];
            allPlacement[playMove.x2][playMove.y2] = 0;
        }

        for(int x = -1; x < 2; x++){
            for(int y = -1; y < 2; y++){
                if((inBounds(playMove.x1 + x, playMove.y1 + y, playColor)) || (x == 0 && y == 0)){
                    continue;
                } else {
                    int one = allPlacement[playMove.x1 + x][playMove.y1 + y];
                    if (one%2 == playColor && one != 0){
                        neighborCount += 1;

                        if(neighborCount >= 2){
                            if(playMove.moveKind == Move.STEP){
                                allPlacement[playMove.x2][playMove.y2] = presentStep;
                            }
                            return true;
                        }

                        for(int a = -1; a < 2; a++){
                            for(int b = -1; b < 2; b++){
                                if(!(a == 0 && b == 0) && !inBounds(playMove.x1 + a + x, playMove.y1 + b + y, playColor)){
                                    int two = allPlacement[playMove.x1 + a + x][playMove.y1 + b + y];

                                    if(two%2 == playColor && two != 0){
                                        if(playMove.moveKind == Move.STEP){
                                            allPlacement[playMove.x2][playMove.y2] = presentStep;
                                        }
                                        return true;
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        if(playMove.moveKind == Move.STEP){
            allPlacement[playMove.x2][playMove.y2] = presentStep;
        }
        return false;
    }

}   