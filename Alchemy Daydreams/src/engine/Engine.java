package engine;

import java.util.ArrayList;
import java.util.List;

import engine.object.EngineObject;
import engine.object.Rendering;
import engine.object.Ticking;
import settings.EngineSettings;


public class Engine implements Runnable, Ticking, Rendering{
	
	boolean running = false;
	List<Ticking> tickables;
	List<Rendering> renderables;
	List<EngineObject> engineObjects;
	
	public Engine() {
		tickables = new ArrayList<Ticking>();
		renderables = new ArrayList<Rendering>();
		engineObjects = new ArrayList<EngineObject>();
	}
	
	public void addEngineObject(EngineObject engineObject) {
		addTickingObject(engineObject);
		addRenderingObject(engineObject);
		engineObjects.add(engineObject);
		System.out.println("Added Object \"" + engineObject.getObjectName() + "\" to raster");
	}
	public void addTickingObject(Ticking ticker) {
		tickables.add(ticker);
	}
	public void addRenderingObject(Rendering renderer) {
		renderables.add(renderer);
	}
	
	public void removeEngineObject(EngineObject engineObject) {
		removeTickingObject(engineObject);
		removeRenderingObject(engineObject);
		engineObjects.remove(engineObject);
		System.out.println("Removed Object \"" + engineObject.getObjectName() + "\" from raster");
	}
	public void removeTickingObject(Ticking ticker) {
		tickables.remove(ticker);
	}
	public void removeRenderingObject(Rendering renderer) {
		renderables.remove(renderer);
	}
	
	public EngineObject getEngineObject(String id) {
		EngineObject ret = null;
		for (EngineObject engineObject : engineObjects) {
			if (engineObject.getObjectID().equals(id)) {
				ret = engineObject;
				break;
			}
		}
		return ret;
	}
	
	public void start() {
		new Thread(this).start();
	}
	public void stop() {
		running = false;
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/((double) EngineSettings.TPS);
		int ticks = 0;
		int frames = 0;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		running = true;
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
			}
			frames++;
			render();
			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				//System.out.println(frames + ", " + ticks);
				frames = 0;
				ticks = 0;
			}
			
		}
	}

	@Override
	public void tick() {
		for (Ticking tickable : tickables) {
			tickable.tick();
		}
	}

	@Override
	public void render() {
		for (Rendering renderable : renderables) {
			renderable.render();
		}
	}
}
