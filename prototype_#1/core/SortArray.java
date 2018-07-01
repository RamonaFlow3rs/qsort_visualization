

package core;


public interface SortArray {
	

	public int length();
	

	public void swap(int i, int j);
	

	public void shuffle();
	


	public enum ElementState {
		ACTIVE, INACTIVE, COMPARING, DONE;
	}
	
}
