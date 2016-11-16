package graphics.renderers.collections;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import utils.Maths;
import engine.object.Entity;
import engine.object.terrain.Terrain;
import engine.object.utils3d.Model3D;
import engine.object.utils3d.Object3D;
import graphics.shaders.TerrainObjectShader;
import graphics.textures.ModelTexture;
import graphics.textures.TerrainTexturePack;

public class TerrainRenderer {
	
	private TerrainObjectShader terrainObjectShader;
	public boolean drawAsWireframe = false;
	
	public TerrainRenderer(TerrainObjectShader terrainObjectShader, Matrix4f projectionMatrix) {
		this.terrainObjectShader = terrainObjectShader;
		terrainObjectShader.start();
		terrainObjectShader.loadProjectionMatrix(projectionMatrix);
		terrainObjectShader.connectTextureUnits();
		terrainObjectShader.stop();
	}
	
	public void render(List<Terrain> terrains) {
		for (Terrain terrain : terrains) {
			prepareTerrain(terrain);
			loadModelMatrix(terrain);
			if (drawAsWireframe) {
				GL11.glDrawElements(GL11.GL_LINE_STRIP, terrain.getModel().getVerticiesCount(), GL11.GL_UNSIGNED_INT, 0);
			} else {
				GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVerticiesCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindObject3D();
		}
	}
	
	private void prepareTerrain(Terrain terrainObject) {
		Model3D model = terrainObject.getModel();
		
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		//This is to be changed later
		terrainObjectShader.loadShineVariables(1, 0);
		
		bindTextures(terrainObject);
	}
	private void bindTextures(Terrain terrain) {
		TerrainTexturePack texturePack = terrain.getTexturePack();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getBackgroundTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getrTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getgTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getbTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getBlendMap().getTextureID());
	}
	private void unbindObject3D() {
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	private void loadModelMatrix(Terrain terrain) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0, 0, 1, 1, 1);
		terrainObjectShader.loadTransformationMatrix(transformationMatrix);
	}
}
