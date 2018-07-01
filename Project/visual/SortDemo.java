package visual;

import algo.QuickSortDoubleEnded;
import algo.QuickSortSliding;
import core.SortAlgorithm;

import java.util.Arrays;



public final class SortDemo {
	

	public static void main(String[] args) {

		SortAlgorithm[] algos = {
			QuickSortDoubleEnded.INSTANCE,
			QuickSortSliding.INSTANCE
		};
		new LaunchFrame(Arrays.asList(algos));
	}
	
}
