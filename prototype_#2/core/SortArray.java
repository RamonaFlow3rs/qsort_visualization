
package core;



public interface SortArray {
    int length();
	int compare(int i, int j);
	void swap(int i, int j);
	void shuffle();
	void setElement(int index, ElementState state);
	void setRange(int start, int end, ElementState state);
	 enum ElementState {
		ACTIVE, INACTIVE, COMPARING, DONE;
	}
}
