
package core;

import java.util.Random;


public abstract class AbstractSortArray implements SortArray {
	
	/*---- Fields ----*/
	
	protected int[] values;
	private static final Random random = new Random();
	
	
	/*---- Constructors ----*/
	
	public AbstractSortArray(int size) {
		if (size < 0)
			throw new IllegalArgumentException();

		// Initialize in order: [0, 1, 2, ..., size-1]
		values = new int[size];
		for (int i = 0; i < values.length; i++)
			values[i] = i;
	}
	

	/*---- Methods ----*/

	
	public int length() {
		return values.length;
	}
	
@Override
	public void swap(int i, int j) {
		if (i < 0 || j < 0 || i >= values.length || j >= values.length)
			throw new IndexOutOfBoundsException();
		int temp = values[i];
		values[i] = values[j];
		values[j] = temp;
	}

@Override
	public void shuffle() {
		for (int i = length() - 1; i > 0; i--)
			swap(i, random.nextInt(i + 1));
	}

	
}
