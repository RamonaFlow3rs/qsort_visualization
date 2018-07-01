

package visual;

import core.AbstractSortArray;

import java.awt.*;



final class VisualSortArray extends AbstractSortArray {
	
	/*---- Fields ----*/
	
	// Visual state per element: 0=active, 1=inactive, 2=comparing, 3=done
	private int[] state;
	
	// Graphics
	private final int scale;
	public final BufferedCanvas canvas;
	private final Graphics graphics;

	
	/*---- Constructors ----*/
	
	public VisualSortArray(int size, int scale, double speed) {

		super(size);
		if (scale <= 0 || speed <= 0 || Double.isInfinite(speed) || Double.isNaN(speed))
			throw new IllegalArgumentException();
		shuffle();
		state = new int[size];


		this.scale = scale;
		canvas = new BufferedCanvas(size * scale);
		graphics = canvas.getBufferGraphics();
		redraw(0, values.length);
	}
	
	
	
	/*---- Methods ----*/
	

	private void redraw(int start, int end) {
		graphics.setColor(BACKGROUND_COLOR);

		graphics.fillRect(start * scale, 0,  (end - start) * scale , values.length * scale);
		for (int i = start; i < end; i++) {
			graphics.setColor(STATE_COLORS[state[i]]);
			if (scale == 1)
				graphics.drawLine(i, values.length, i, values[i]);
			else  // scale > 1
				graphics.fillRect(i*scale, values.length * scale - (values[i] + 1) * scale, scale, (values[i] + 1) * scale);
		}
	}
	

	/*---- Color constants ----*/
	
	private static Color[] STATE_COLORS = {

		new Color(Color.BLUE.getRGB()),  // Active
		new Color(Color.LIGHT_GRAY.getRGB()),  // Inactive
		new Color(Color.YELLOW.getRGB()),  // Comparing
		new Color(Color.GREEN.getRGB()),  // Done
	};
	
	private static Color BACKGROUND_COLOR = new Color(Color.WHITE.getRGB());



}
