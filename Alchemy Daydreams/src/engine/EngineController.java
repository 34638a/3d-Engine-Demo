package engine;

import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import utils.PrimitiveGenerator;
import utils.ResourceFileImporter;
import engine.object.Entity;
import engine.object.terrain.Terrain;
import engine.object.terrain.TerrainController;
import engine.object.utils3d.Model3D;
import engine.object.utils3d.ModelLoader3D;
import engine.object.utils3d.Object3D;
import engine.rpg.entity.player.Player;
import graphics.Camera;
import graphics.lights.Light;
import graphics.renderers.MasterRenderer;
import graphics.renderers.collections.ModelRenderer;
import graphics.shaders.StaticObjectShader;
import graphics.textures.ModelTexture;
import graphics.windows.RenderWindow;


public class EngineController {
	
	ModelLoader3D modelLoader;
	Camera camera;
	MasterRenderer mRenderer;
	Engine engine;
	
	//Controllers
	TerrainController terrainController;
	
	
	public EngineController() {
		RenderWindow.createDisplay();
		
		mRenderer = new MasterRenderer();
		modelLoader = new ModelLoader3D();
		
		
		terrainController = new TerrainController(modelLoader);
		
		engine = new Engine();
		engine.start();
		
		displayLoop();
	}

	private void displayLoop() {
		
		//TempCode
		PrimitiveGenerator prime = new PrimitiveGenerator(modelLoader);
		
		Model3D model = modelLoader.convertDataToModel3D(ResourceFileImporter.loadObject3DData("model/stall"));
		ModelTexture texture = new ModelTexture(modelLoader.loadTexturePNG("textures/stallTexture"));
		texture.setReflectivity(0.0f);
		texture.setShineDamper(1.0f);
		texture.setHasTransparency(true);
		texture.setUseFakeLighting(true);
		Object3D object = new Object3D(model, texture);
		Player entity = new Player(object, new Vector3f(0,0,-10), new Vector3f(0,0,0), new Vector3f(0.1f,0.1f,0.1f));
		entity.setMoveSpeed(0.02f);
		//Entity entity2 = new Entity(object, entity, new Vector3f(0,0,2), new Vector3f(0,0,0), new Vector3f(1,1,1));
		
		Light light = new Light(new Vector3f(0,3000,0), new Vector3f(1,1,1));
		camera = new Camera(entity, 35);
		
		
		while(!Display.isCloseRequested()) {
			//entity.moveRotation(0,0.02f,0);
			camera.move();

			entity.move();
			//light.setPosition(new Vector3f(entity.getPosition().x, entity.getPosition().y + 1, entity.getPosition().z));
			
			List<Terrain> terrains = terrainController.getTerrainTilesToRender(entity.getPosition());
			for (Terrain terrain : terrains) {
				mRenderer.processTerrain(terrain);
			}
			mRenderer.processEntity(entity);
			mRenderer.render(camera, light);
			RenderWindow.updateDisplay();
		}
		
		//Program termination code
		engine.stop();
		modelLoader.cleanUp();
		mRenderer.cleanUp();
		RenderWindow.closeDisplay();
	}
}
