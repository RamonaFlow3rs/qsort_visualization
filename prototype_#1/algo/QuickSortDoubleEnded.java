
package algo;

import core.AbstractSortAlgorithm;
import core.SortAlgorithm;
import core.SortArray;


public final class QuickSortDoubleEnded extends AbstractSortAlgorithm {
	

	public static final SortAlgorithm INSTANCE = new QuickSortDoubleEnded();
	

	private QuickSortDoubleEnded() {
		super("Quick sort (двухсторонняя)");
	}

	@Override
	public void sort(SortArray array) {

	}
}
