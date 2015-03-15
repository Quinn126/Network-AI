/* PieceList.java */

package list;
import player.Chip;

/*
* Piecelist is a subclass of the Playlist class that primarily manipulates the Chips used in the game.
*/

public class PieceList extends PlayList {
	private Chip[] pieceList;
	private int capacity;

	/*
	* Piecelist takes in zero parameters and constructs an array to hold the Chip at default size that is to be added.
	*/
	public PieceList() {
		this(10);
	}

	/*
	* Piecelist takes in one parameter and constructs an array to hold the size of the Chip that is to be added.
	*/
	public PieceList(int capacity) {
		pieceList = new Chip[capacity];
		capacity = 0;
	}

	/*
	* capacity takes in zero parameters and directly returns the size of the array.
	*/
	public int capacity() {
		return capacity;
	}

	/*
	* itemAtindex takes in one parameter and returns the Chip at the provided index in the array.
	*/
	public Chip itemAtIndex(int index) {
		return pieceList[index];
	}

	/*
	* Piecelist takes in one parameter and adds the provided chip to the back of the array.
	*/
	public void joinList(Chip playChip) {
		this.joinList(this.capacity(), playChip);
	}

	/*
	* Piecelist takes in two parameters and adds the provided chip to provided index in the array.
	*/
	public void joinList(int index, Chip playChip) {
		if (pieceList.length == capacity) {
			Chip[] chipArray = new Chip[2 * pieceList.length];
			for (int x = 0; x < pieceList.length; x++) {
				chipArray[x] = pieceList[x];
			}
			pieceList = chipArray;
		}
		if (capacity < index) {
			throw new IndexOutOfBoundsException();
		}
		if (pieceList[index] == null) {
			capacity++;
		}
		pieceList[index] = playChip;
	}
}
