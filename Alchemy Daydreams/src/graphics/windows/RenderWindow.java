package graphics.windows;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import settings.GraphicsSettings;

public class RenderWindow {
	
	
	public static void createDisplay() {
		
		ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(GraphicsSettings.width, GraphicsSettings.height));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle(GraphicsSettings.name);
		} catch (LWJGLException e) {
			System.out.println("Failed to create display");
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, GraphicsSettings.width, GraphicsSettings.height);
	}
	public static void updateDisplay() {
		Display.sync(GraphicsSettings.FPS);
		Display.update();
	}
	public static void closeDisplay() {
		Display.destroy();
	}
}
