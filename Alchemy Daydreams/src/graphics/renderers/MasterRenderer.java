package graphics.renderers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import settings.GraphicsSettings;
import engine.object.Entity;
import engine.object.terrain.Terrain;
import engine.object.utils3d.Object3D;
import graphics.Camera;
import graphics.lights.Light;
import graphics.renderers.collections.ModelRenderer;
import graphics.renderers.collections.TerrainRenderer;
import graphics.shaders.StaticObjectShader;
import graphics.shaders.TerrainObjectShader;

public class MasterRenderer {
	
	private Vector3f backgroundColour = new Vector3f(128,128,128);
	private Matrix4f projectionMatrix;
	
	//Entity Rendering
	private StaticObjectShader staticObjectShader;
	private ModelRenderer modelRenderer;
	private Map<Object3D, List<Entity>> objectMap;
	
	//Terrain Rendering
	private TerrainRenderer terrainRenderer;
	private TerrainObjectShader terrainShader;
	private List<Terrain> terrains;
	
	//Water Rendering
	
	
	//Particle Rendering
	
	
	public MasterRenderer() {
		
		//Prepare Default information
		enableCulling();
		createProjectionMatrix();
		
		//Load Shaders
		staticObjectShader = new StaticObjectShader();
		terrainShader = new TerrainObjectShader();
		
		//Prepare Renderers
		modelRenderer = new ModelRenderer(staticObjectShader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		
		//Prepare Renderer Resources
		objectMap = new HashMap<Object3D, List<Entity>>();
		terrains = new ArrayList<Terrain>();
	}
	
	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void render(Camera camera, Light timeLight) {
		prepare();
		
		terrainShader.start();
			terrainShader.loadSkyColour(backgroundColour.x/256f, backgroundColour.y/256f, backgroundColour.z/256f);
			terrainShader.loadLight(timeLight);
			terrainShader.loadViewMatrix(camera);
			terrainRenderer.render(terrains);
		terrainShader.stop();

		staticObjectShader.start();
			staticObjectShader.loadSkyColour(backgroundColour.x/256f, backgroundColour.y/256f, backgroundColour.z/256f);
			staticObjectShader.loadLight(timeLight);
			staticObjectShader.loadViewMatrix(camera);
			modelRenderer.render(objectMap);
		staticObjectShader.stop();
		
		objectMap.clear();
		terrains.clear();
	}
	
	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}
	
	public void processEntity(Entity entity) {
		Object3D entityObjectData = entity.getObject();
		List<Entity> batch = objectMap.get(entityObjectData);
		if(batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			objectMap.put(entityObjectData, newBatch);
		}
	}

	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(backgroundColour.x/256f, backgroundColour.y/256f, backgroundColour.z/256f, 1);
	}
	
	public void cleanUp() {
		staticObjectShader.cleanUp();
		terrainShader.cleanUp();
	}
	
	private void createProjectionMatrix() {
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(GraphicsSettings.FOV/2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = GraphicsSettings.farClip - GraphicsSettings.nearClip;
        
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((GraphicsSettings.farClip + GraphicsSettings.nearClip) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * GraphicsSettings.nearClip * GraphicsSettings.farClip) / frustum_length);
        projectionMatrix.m33 = 0;
	}
}
