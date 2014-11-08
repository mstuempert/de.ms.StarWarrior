package de.ms.sw;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class StarWarrior {
	
	private static boolean exit = false;
	
	private static final int FRAMES_PER_SEC = 50;

	public static void main(String[] args) {

		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = environment.getDefaultScreenDevice();
		GraphicsConfiguration configuration = device.getDefaultConfiguration();
		
		Frame frame = new Frame(configuration);
		frame.setUndecorated(true);
		frame.setIgnoreRepaint(true);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					exit = true;
				}
			}
		});
		
		Universe universe = null;
		
		try {
			
			device.setFullScreenWindow(frame);
			frame.createBufferStrategy(2);
			BufferStrategy bufferStrategy = frame.getBufferStrategy();
			
			universe = new Universe(frame);
			
			long delayMillis = 1000/FRAMES_PER_SEC;
			
			while (!exit) {
				
				long startMillis = System.currentTimeMillis();
				
				universe.nextStep();
				
				Graphics graphics = bufferStrategy.getDrawGraphics();
				
				if (!bufferStrategy.contentsLost()) {
				
					universe.render(graphics);
					graphics.dispose();
					bufferStrategy.show();
					
				}
				
				long stopMillis = System.currentTimeMillis();
				
				long waitMillis = delayMillis-(stopMillis-startMillis);
				
				try {
					if (waitMillis > 0) {
						Thread.sleep(waitMillis);
					}
				} catch (InterruptedException e) {
					
				}
			}
			
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			if (universe != null) {
				universe.destroy();
			}
			device.setFullScreenWindow(null);
		}
		
		System.exit(0);
		
	}

}
