

package core;

import java.util.Objects;



public abstract class AbstractSortAlgorithm implements SortAlgorithm {
	
	/*---- Fields ----*/
	
	private final String name;

	/*---- Constructors ----*/
	
	protected AbstractSortAlgorithm(String name) {
		Objects.requireNonNull(name);
		this.name = name;
	}

	/*---- Methods ----*/
	
	public final String getName() {
		return name;
	}
	
}
