/*PerfectMove.java*/

package player;

/*
This is a PerfectMove object class that stores the ideal move and the corresponding score
returned from minimax search tree, meaningly the ChooseMove function.
*/
public class PerfectMove{
    public Move playmove;
    public int score;
    public int depth; //Optional: May be used for testing later on.


//This takes in zero parameters and creates a default PerfectMove to use in the game.
    public PerfectMove(){
    	playmove = null;
    	score = 0;
         
    }

//This tkaes in two parameters and creates a PerfectMove using the provided score and provided move.
    public PerfectMove(int perfectScore, Move perfectMove){
    	playmove = perfectMove;
    	score = perfectScore;
         
    }    
 
}