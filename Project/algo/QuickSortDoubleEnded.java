package algo;

import core.AbstractSortAlgorithm;
import core.SortAlgorithm;
import core.SortArray;



public final class QuickSortDoubleEnded extends AbstractSortAlgorithm {

	// The singleton instance.
	public static final SortAlgorithm INSTANCE = new QuickSortDoubleEnded();


	public void sort(SortArray array) {
		sort(array, 0, array.length());
	}


	private static void sort(SortArray array, int start, int end) {
		if (start == end)
			return;

		array.setRange(start, end, SortArray.ElementState.ACTIVE);
		int left = start;
		int right = end - 1;
		int pivot = left;  // Do not change this!

		while (left != right) {
			// Swap the pivot, which is on the left, with something on the right that needs to go on the left
			while (array.compare(right, pivot) >= 0 && left != right) {
				array.setElement(right, SortArray.ElementState.INACTIVE);
				right--;
			}
			if (left != right) {
				array.swap(left, right);
				pivot = right;
				array.setElement(left, SortArray.ElementState.INACTIVE);
				left++;
			}

			// Swap the pivot, which is on the right, with something on the left that needs to go on the right
			while (array.compare(left, pivot) <= 0 && left != right) {
				array.setElement(left, SortArray.ElementState.INACTIVE);
				left++;
			}
			if (left != right) {
				array.swap(right, left);
				pivot = left;
				array.setElement(right, SortArray.ElementState.INACTIVE);
				right--;
			}
		}

		array.setElement(pivot, SortArray.ElementState.DONE);
		sort(array, start, pivot);
		sort(array, pivot + 1, end);
	}


	// Private constructor.
	private QuickSortDoubleEnded() {
		super("Quick sort (двухсторонняя)");
	}

}
