

package visual;

import java.awt.*;
import java.awt.image.BufferedImage;



final class BufferedCanvas extends Canvas {
	
	/*---- Fields ----*/
	
	private BufferedImage buffer;
	private Graphics bufGfx;
//	public volatile int synchronizer;
	
	
	
	/*---- Constructors ----*/
	
	public BufferedCanvas(int size) {
		this(size, size);
	}
	
	
	public BufferedCanvas(int width, int height) {
		if (width <= 0 || height <= 0)
			throw new IllegalArgumentException();
		this.setSize(width, height);
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		bufGfx = buffer.getGraphics();
	}
	
	
	
	/*---- Methods ----*/
	
	// Returns the graphics object for the off-screen image, not null.
	public Graphics getBufferGraphics() {
		return bufGfx;
	}
	
	
	// Called by the AWT event loop, not by user code.
	public void update(Graphics g) {
		paint(g);
	}
	
	
	// Called by the AWT event loop, not by user code.
	public void paint(Graphics g) {
		g.drawImage(buffer, 0, 0, this);
	}
	
}
