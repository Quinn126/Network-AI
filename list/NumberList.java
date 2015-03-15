/* NumberList.java */

package list;

/*
* Numberlist is a subclass of Playlist and primarily is utilized to manipulate Integers.
*/

public class NumberList {

	private Integer[] numberList;
	private int capacity;

	/*
	* Numberlist takes in zero parameters to construct a default array.
	*/
	public NumberList() {
		this(10);
	}

	/*
	* Numberlist takes in one parameter that determines the size of the array to be constructed.
	*/
	public NumberList(int capacity) {
		numberList = new Integer[capacity];
		capacity = 0;
	}

	/*
	* Numberlist takes in one parameter in order to generate an identical copy of another array.
	*/
	public NumberList(NumberList theList) {
		int x = 0;
		numberList = new Integer[theList.numberList.length];
		while (theList.capacity() > x) {
			this.joinList(x, theList.itemAtIndex(x));
			x++;
		}
		capacity = theList.capacity();
	}

	/*
	* capacity takes in zero parameters and directly returns the array size.
	*/
	public int capacity() {
		return capacity;
	}

	/*
	* itemAtindex takes in one parameter and returns the number at the provided index.
	*/
	public Integer itemAtIndex(int index) {
		return numberList[index]; //
	}

	/*
	* purgelist takes in one parameter and terminates the item provided in the array.
	*/
	public void purgeList(Integer item) {
		for (int x = 0; x < this.capacity(); x++) {
			if (this.itemAtIndex(x) == item) {
				this.joinList(x, null);
			}
		}
	}

	/*
	* hasIt takes in one parameter and determines whether the array has the provided item inside it.
	*/
	public boolean hasIt(Integer item) {
		for (int x = 0; x < this.capacity(); x++) {
			if (this.itemAtIndex(x) == item) {
				return true;
			}
		}
		return false;
	}

	/*
	* joinlist takes in one parameter and attaches the given i to an index in the array.
	*/
	public void joinList(Integer i) {
		this.joinList(this.capacity(), i);
	}

	/*
	* Numberlist takes in one parameter in order to generate an identical copy of another array.
	*/
	public void joinList(int index, Integer i) {
		if (numberList.length == this.capacity()) {
			Integer[] intArray = new Integer[2 * numberList.length];
			for (int x = 0; numberList.length > x; x++) {
				intArray[x] = numberList[x];
			}
			numberList = intArray;
		}
		if (capacity < index) {
			throw new IndexOutOfBoundsException();
		}
		if (numberList[index] == null) {
			capacity++;
		}
		numberList[index] = i;
	}
}
