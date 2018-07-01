

package core;

import java.util.Random;

public abstract class AbstractSortArray implements SortArray {

	/*---- Fields ----*/
	public static final Random random = new Random();
	protected int[] values;

	/*---- Constructors ----*/

	public AbstractSortArray(int size) {
		if (size < 0)
			throw new IllegalArgumentException();

		// Initialize in order: [0, 1, 2, ..., size-1]
		values = new int[size];
		for (int i = 0; i < values.length; i++)
			values[i] = random.nextInt(values.length);
	}

	public AbstractSortArray(int[] values) {
		this.values = values;
	}

	/*---- Methods ----*/

	/* Field getters */

	public int length() {
		return values.length;
	}


	/* Comparison and swapping */

	public int compare(int i, int j) {
		if (i < 0 || j < 0 || i >= values.length || j >= values.length)
			throw new IndexOutOfBoundsException();
		return Integer.compare(values[i], values[j]);
	}


	public void swap(int i, int j) {
		if (i < 0 || j < 0 || i >= values.length || j >= values.length)
			throw new IndexOutOfBoundsException();
		int temp = values[i];
		values[i] = values[j];
		values[j] = temp;
	}


	public boolean compareAndSwap(int i, int j) {
		if (compare(j, i) < 0) {
			swap(i, j);
			return true;
		} else
			return false;
	}


	public void shuffle() {
		for (int i = length() - 1; i > 0; i--)
			swap(i, random.nextInt(i + 1));
	}



	public void setElement(int index, ElementState state) {}


	public void setRange(int start, int end, ElementState state) {}






}
