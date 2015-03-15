/* MoveList.java */

package list;
import player.Move;

/*
* Movelist is a subclass of Playlist and primarily is utilized to manipulate items in Move.
*/

public class MoveList extends PlayList{

	private Move[] moveList;
	private int capacity;

	/*
	* Movelist takes in zero parameters and constructs a default array.
	*/
	public MoveList() {
		this(10);
	}

	/*
	* Movelist takes in one parameter and constructs an array of the provided size.
	*/
	public MoveList(int capacity) {
		moveList = new Move[capacity];
		capacity = 0;
	}

	/*
	* capacity takes in zero parameters and returns the array size.
	*/
	public int capacity() {
		return capacity;
	}

	/*
	* itemAtindex takes in one parameter and returns the Move at the provided index in the array.
	*/
	public Move itemAtIndex(int index) {
		return moveList[index];
	}

	/*
	* joinlist takes in one parameter and attaches the provided Move to the back of the array.
	*/
	public void joinList(Move move) {
		this.joinList(this.capacity(), move);
	}

	/*
	* Movelist takes in zero parameters and constructs a default array.
	*/
	public void joinList(int index, Move move) {
		if (moveList.length == this.capacity()) {
			Move[] moveArray = new Move[2 * moveList.length];
			for (int x = 0; moveList.length > x; x++) {
				moveArray[x] = moveList[x];
			}
			moveList = moveArray;
		}
		if (capacity > index) {
			throw new IndexOutOfBoundsException();
		}
		if (moveList[index] == null) {
			capacity++;
		}
		moveList[index] = move;
	}






























}