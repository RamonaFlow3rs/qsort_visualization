package visual;

import java.awt.*;
import java.awt.image.BufferedImage;



final class BufferedCanvas extends Canvas {
	
	/*---- Fields ----*/
	
	private BufferedImage buffer;
	private Graphics bufGfx;
	
	
	
	/*---- Constructors ----*/
	
	BufferedCanvas(int size) {
		this(size, size);
	}
	
	
	private BufferedCanvas(int width, int height) {
		if (width <= 0 || height <= 0)
			throw new IllegalArgumentException();
		this.setSize(width, height);
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		bufGfx = buffer.getGraphics();
	}
	
	
	
	/*---- Methods ----*/

	Graphics getBufferGraphics() {
		return bufGfx;
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {
		g.drawImage(buffer, 0, 0, this);
	}
	
}
