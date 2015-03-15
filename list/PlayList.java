/* PlayList.java */

package list;

/*
* Playlist is utilized as a superclass array/info-carrier in order to manipulate data in the game.
* The lists' purposes include the validity of the array being used and adds/removes parts that are invalid 
* as well as maintaining the current status of important objects used in the game.
*/

public class PlayList {
	private Object[] playList;
	private int capacity;

	/*
	* This takes in no parameters to initialize a list.
	*/
	public PlayList() {
		this(10);
	}

	/*
	* This takes in one parameter that tells the list how big it should
	* be to hold the expected amount of items.
	*/
	public PlayList(int capacity) {
		playList = new Object[capacity];
		capacity = 0;
	}

	/*
	* This takes in no parameters and returns the list capacity directly.
	*/
	public int capacity() {
		return capacity;
	}

	/*
	* This takes in one parameter and uses the given index to locate the desired item.
	*/
	public Object itemAtIndex(int index) {
		return playList[index];
	}

	/*
	* This takes in no parameters and checks whether a list
	* is holding anything or if it is holding nothing at all.
	*/
	public boolean emptyList() {
		if (capacity == 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	* This takes in one parameter and attaches the given item to the list.
	*/
	public void joinList(Object item) {
		this.joinList(this.capacity(), item);
	}

	/*
	* This takes in two parameters and attaches the given item to the appropriate index in the list.
	* This also checks to make sure the capacity is appropriate for the list.
	*/
	public void joinList(int index, Object item) {
		if (index >= capacity) {
			throw new IndexOutOfBoundsException();
		}
		if (playList.length == this.capacity()) {
			Object[] tempList = new Object[2 * playList.length];
			for (int x = 0; x < playList.length; x++) {
				tempList[x] = playList[x];
				playList = tempList;
			}
		}
		if (playList[index] == null) {
			capacity++;
		}
		playList[index] = item;
	}

	/*
	* This takes in one parameter and deletes the item located at the index in the list.
	*/
	public void purgeList(int index) {
		if (playList[index] == null || playList.length < index) {
			System.out.println("Index " + index + " is empty.");
		} else {
			playList[index] = null;
			capacity--;
		}
	}

}