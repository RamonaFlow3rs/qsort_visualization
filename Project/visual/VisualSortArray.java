
package visual;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import core.AbstractSortArray;



final class VisualSortArray extends AbstractSortArray {

	/*---- Fields ----*/

	// Visual state per element: 0=active, 1=inactive, 2=comparing, 3=done
	private int[] state;

	// Graphics
	private final int scale;
	final BufferedCanvas canvas;
	private final Graphics graphics;

	// Statistics
	private volatile int comparisonCount;
	private volatile int swapCount;

	// Speed regulation
	private final double targetFrameRate = 60;
	private final double stepsPerFrame;
	private double remainingStepsAllowed;
	private long nextRepaintTime;
	private final boolean drawIncrementally;



	/*---- Constructors ----*/

	VisualSortArray(int size, int scale, double speed) {
		// Check arguments and initialize arrays
		super(size);
		if (scale <= 0 || speed <= 0 || Double.isInfinite(speed) || Double.isNaN(speed))
			throw new IllegalArgumentException();
		shuffle();
		state = new int[size];

		// Initialize various numbers
		comparisonCount = 0;
		swapCount = 0;
		stepsPerFrame = speed / targetFrameRate;
		remainingStepsAllowed = 0;
		nextRepaintTime = System.nanoTime();
		drawIncrementally = stepsPerFrame < size;

		// Initialize graphics
		this.scale = scale;
		canvas = new BufferedCanvas(size * scale);
		graphics = canvas.getBufferGraphics();
		redraw(0, values.length);
	}

	VisualSortArray(int scale, double speed, int[] values){

		super(values);
		int size = values.length;
		if (scale <= 0 || speed <= 0 || Double.isInfinite(speed) || Double.isNaN(speed))
			throw new IllegalArgumentException();
		shuffle();
		state = new int[size];

		// Initialize various numbers
		comparisonCount = 0;
		swapCount = 0;
		stepsPerFrame = speed / targetFrameRate;
		remainingStepsAllowed = 0;
		nextRepaintTime = System.nanoTime();
		drawIncrementally = stepsPerFrame < size;

		// Initialize graphics
		this.scale = scale;
		canvas = new BufferedCanvas((size) * scale);
		graphics = canvas.getBufferGraphics();
		redraw(0, size);


	}


	/*---- Methods ----*/

	/* Comparison and swapping */

	public int compare(int i, int j) {
		if (Thread.interrupted())
			throw new StopException();

		setElement(i, ElementState.COMPARING);
		setElement(j, ElementState.COMPARING);
		beforeStep();
		comparisonCount++;

		// No repaint here
		setElement(i, ElementState.ACTIVE);
		setElement(j, ElementState.ACTIVE);

		return super.compare(i, j);
	}


	public void swap(int i, int j) {
		if (Thread.interrupted())
			throw new StopException();
		super.swap(i, j);
		if (state != null) {  // If outside the constructor
			beforeStep();
			swapCount++;
			setElement(i, ElementState.ACTIVE);
			setElement(j, ElementState.ACTIVE);
		}
	}


	/* Array visualization */

	public void setElement(int index, ElementState state) {
		this.state[index] = state.ordinal();
		if (drawIncrementally)
			redraw(index, index + 1);
	}


	public void setRange(int start, int end, ElementState state) {
		Arrays.fill(this.state, start, end, state.ordinal());
		if (drawIncrementally)
			redraw(start, end);
	}


	/* After sorting */

	int getComparisonCount() {
		return comparisonCount;
	}

	int getSwapCount() {
		return swapCount;
	}


	// Checks if the array is sorted
	void assertSorted() {
		for (int i = 1; i < values.length; i++) {
			if (values[i - 1] > values[i])
				throw new AssertionError();
		}
		redraw(0, values.length);
		canvas.repaint();
	}



	private void redraw(int start, int end) {
		graphics.setColor(BACKGROUND_COLOR);

		graphics.fillRect(start * scale, 0,  (end - start ) * scale , (values.length)* scale);
		for (int i = start; i < end; i++) {
			graphics.setColor(STATE_COLORS[state[i]]);
			if (scale == 1)
				graphics.drawLine(i, values.length, i, values[i]);
			else{
				graphics.fillRect(i*scale, values.length * scale - (values[i] ) * scale, scale, (values[i]) * scale);
			}  // scale > 1


		}
	}



	private void beforeStep() {
		boolean first = true;
		while (remainingStepsAllowed < 1) {
			long currentTime;
			while (true) {
				currentTime = System.nanoTime();
				if (currentTime >= nextRepaintTime)
					break;
				long delay = nextRepaintTime - currentTime;
				try {
					Thread.sleep(delay / 1000000, (int)(delay % 1000000));
				} catch (InterruptedException e) {
					throw new StopException();
				}
			}
			if (first) {
				if (!drawIncrementally)
					redraw(0, values.length);
				canvas.repaint();
				first = false;
			}
			nextRepaintTime += Math.round(1e9 / targetFrameRate);
			if (nextRepaintTime <= currentTime)
				nextRepaintTime = currentTime + Math.round(1e9 / targetFrameRate);
			remainingStepsAllowed += stepsPerFrame;
		}
		remainingStepsAllowed--;
	}





	private static Color[] STATE_COLORS = {
			new Color(Color.DARK_GRAY.getRGB()),  // Active
			new Color(Color.LIGHT_GRAY.getRGB()),  // Inactive
			new Color(0x999CC),  // Comparing
			new Color(0x000099),  // Done
	};

	private static Color BACKGROUND_COLOR = new Color(Color.WHITE.getRGB());

}
