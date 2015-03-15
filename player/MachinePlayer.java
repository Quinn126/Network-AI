/* MachinePlayer.java */

package player;
import list.NumberList;
import list.MoveList;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {
  final static int BLACK = 0;
  final static int WHITE = 1;
  private PlayBoard playboard;
  private int playcolor;
  private int currentplayer = 1;
  private int playdepth;

  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
    playboard = new PlayBoard();
    playcolor = color;
    playdepth = 3; //placeholder value for now
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
    playcolor = color;

    if (searchDepth >= 1) {
      playdepth = searchDepth;
    } else {
      playdepth = 1;
    }
    playboard = new PlayBoard();
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
    PerfectMove perfectmove = chooseMove(currentplayer, -1000000, 1000000, playdepth);
    Move currentmove = perfectmove.playmove; //

    if (currentmove == null) {
      currentmove = new Move();
      currentmove.moveKind = Move.QUIT;
    }
    playboard = new PlayBoard(playboard, currentmove, currentplayer);
    currentplayer++;
    return currentmove;
  } 

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {
    if (!(playboard.moveValidity(m, currentplayer))) {
      return false;
    } else {
      playboard = new PlayBoard(playboard, m, currentplayer);
      currentplayer++;
      return true;
    }
  }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) {
    if (!(playboard.moveValidity(m, currentplayer))) {
      return false;
    } else {
      playboard = new PlayBoard(playboard, m, currentplayer);
      currentplayer++;
      return true;
    }
  }

  //This allows you to obtain the master board directly if you need it (Optional use).
  public PlayBoard needPlayBoard() {
    return playboard;
  }

  //This allows you to get the preferable moves for the game.
  public MoveList obtainMoves(int count) {
    MoveList array = new MoveList();

    for (int x = 0; x < PlayBoard.DIM; x++) {
      for (int y = 0; y < PlayBoard.DIM; y++) {
        if (playboard.betterToAdd(currentplayer)) {
          Move boardMove = new Move();
          boardMove.moveKind = Move.ADD;
          boardMove.x1 = x;
          boardMove.y1 = y;
          array.joinList(boardMove);
        } else {
          int moveCount = count;

          if (moveCount == 0){
            moveCount = 2;
          }

          while(playboard.allChips() > moveCount) {
            Chip placeHolder = playboard.obtainChip(moveCount);
            Move tempMove = new Move();
            tempMove.moveKind = Move.STEP;
            tempMove.x1 = x;
            tempMove.x2 = placeHolder.theX();
            tempMove.y1 = y;
            tempMove.y2 = placeHolder.theY();
            array.joinList(tempMove);

            moveCount += 2;
          }
        }
      }
    }
    return array;
  }

  //This takes in four parameters and utilizes alpha-beta pruning as well as Minimax search 
  //to find the appropriate fit for PerfectMove.
  public PerfectMove chooseMove(int pruningPlayer, int alpha, int beta, int pruningDepth) 
  {
    PerfectMove favorable = new PerfectMove();
    PerfectMove result = new PerfectMove();
    int count = pruningPlayer%2;

    if (playcolor == count) {
      favorable.score = alpha;
    } else {
      favorable.score = beta;
    }
    MoveList pruning = this.obtainMoves(count);
    int x = 0;

    while(pruning.capacity() > x) {
      Move pruningMove = pruning.itemAtIndex(x);
      if (playboard.moveValidity(pruningMove, pruningPlayer)) {
        PlayBoard presentBoard = playboard;
        PlayBoard effectedBoard = new PlayBoard(playboard, pruningMove, pruningPlayer);
        playboard = effectedBoard;
        int numberChip;

        if (MachinePlayer.BLACK == count) {
          numberChip = 2;
        } else{
          numberChip = 1;
        }

        if (playboard.goalsOccupied(count)) {
          while (numberChip < 21) {
            NumberList numList = new NumberList();
            Chip presentChip = playboard.obtainChip(numberChip);

            if (presentChip != null && (presentChip.theX() == 0 || presentChip.theY() == 0)) {
              if(playboard.networkValidity(count, 0, -1, presentChip, numList)) {
                if(playcolor == count) {
                  favorable.score = -1*(pruningPlayer*100) + 100000;
                } else {
                  favorable.score = (pruningPlayer*100) - 100000;
                }
                favorable.playmove = pruningMove;
                return favorable;
              }
            }
            numberChip += 2;
          }
        }
        if (pruningDepth == 1) {
          result.playmove = pruningMove;
          result.score = playboard.evaluation(playcolor);
        } else {
          result = chooseMove(pruningPlayer+1, alpha, beta, pruningDepth-1);
        }
        playboard = presentBoard;

        if (playcolor == count && favorable.score < result.score) {
          favorable.score = result.score;
          favorable.playmove = pruningMove;
          alpha = result.score;
        } else if((playcolor+1)%2 == count && result.score < favorable.score){
          favorable.score = result.score;
          favorable.playmove = pruningMove;
          beta = result.score;

        }
        if (alpha >= beta){
          return favorable;
        }
      }
      x++;
    }
    return favorable;
  }




}