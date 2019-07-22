import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame implements MouseMotionListener{
	
	
	public static final int WIDTH = 360;
	public static final int HEIGHT = 640;
	
	public static final int ITERATION = 100;
	public static final int SCALE = 150;
	
	private BufferedImage buffer;
	
	private int mouseX = 50, mouseY = 50;
	
	private File fileImage;
	
	int i = 0;
	
	public Mandelbrot() {
		super("Mandelbrot Set");
		
		buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		fileImage = new File("Mandelbrot.png");
		
		renderMandelbrotSet();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		pack();
		addMouseMotionListener(this);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(buffer, 0, 0, null);
		try {
			ImageIO.write(buffer, "png", fileImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void renderMandelbrotSet() {
		
		for(int y = 0; y < HEIGHT; y++) {
			for(int x = 0; x < WIDTH; x++) {
				int color = calculatePoint((-y + HEIGHT/2f - 100)/SCALE,(x - WIDTH/2f)/SCALE);
				buffer.setRGB(x, y, color);
			}
		}
		
	}
	
	public int calculatePoint(float x, float y) {
		float cx = x;
		float cy = y;
		
		int i = 0;
		for(i = 0; i < ITERATION; i++) {
			float nx = x*x - y*y + cx;
			float ny = 2*x*y + cy;
			
			x = nx;
			y = ny;
			
			if(x*x + y*y > 4) break;
			
		}
		
		if(i == ITERATION) return 0x05668D;
		return Color.HSBtoRGB((float)i/ITERATION + (float)(mouseX + mouseY)*360/1000, .28f, .76f);
	}
	
	public static void main(String[] args) {
		new Mandelbrot();
	}


	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		renderMandelbrotSet();
		repaint();
	}
    
}