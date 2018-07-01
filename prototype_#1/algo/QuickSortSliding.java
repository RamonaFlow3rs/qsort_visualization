package algo;

import core.AbstractSortAlgorithm;
import core.SortAlgorithm;
import core.SortArray;


/**
 * Sorts by recursively partitioning the array around a pivot.
 * The average time complexity is in <var>O</var>(<var>n</var> log <var>n</var>).
 */
public final class QuickSortSliding extends AbstractSortAlgorithm {
	

	public static final SortAlgorithm INSTANCE = new QuickSortSliding();
	

	// Private constructor.
	private QuickSortSliding() {
		super("Quick sort (слиянием)");
	}

	@Override
	public void sort(SortArray array) {

	}
}
