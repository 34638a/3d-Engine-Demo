package graphics.renderers.collections;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import settings.GraphicsSettings;
import utils.Maths;
import engine.object.Entity;
import engine.object.utils3d.Model3D;
import engine.object.utils3d.Object3D;
import graphics.renderers.MasterRenderer;
import graphics.shaders.StaticObjectShader;
import graphics.textures.ModelTexture;

public class ModelRenderer {
	
	private StaticObjectShader staticObjectShader;
	
	public boolean drawAsWireframe = false;
	
	public ModelRenderer(StaticObjectShader staticObjectShader, Matrix4f projectionMatrix) {
		
		this.staticObjectShader = staticObjectShader;
		staticObjectShader.start();
		staticObjectShader.loadProjectionMatrix(projectionMatrix);
		staticObjectShader.stop();
	}
	
	
	public void render(Map<Object3D, List<Entity>> objectMap) {
		for (Object3D object3D : objectMap.keySet()) {
			prepareObject3D(object3D);
			List<Entity> batch = objectMap.get(object3D);
			for (Entity entity : batch) {
				prepareInstance(entity);
				if (drawAsWireframe) {
					GL11.glDrawElements(GL11.GL_LINE_STRIP, object3D.getModel3D().getVerticiesCount(), GL11.GL_UNSIGNED_INT, 0);
				} else {
					GL11.glDrawElements(GL11.GL_TRIANGLES, object3D.getModel3D().getVerticiesCount(), GL11.GL_UNSIGNED_INT, 0);
				}
			}
			unbindObject3D();
		}
	}
	private void prepareObject3D(Object3D object3D) {
		Model3D model = object3D.getModel3D();
		ModelTexture modelTexture = object3D.getModelTexture();
		
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		if (modelTexture.hasTransparency()) {
			MasterRenderer.disableCulling();
		}
		
		staticObjectShader.loadFakeLightingBoolean(modelTexture.shouldUseFakeLighting());
		staticObjectShader.loadShineVariables(modelTexture.getShineDamper(), modelTexture.getReflectivity());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, modelTexture.getTextureID());
	}
	private void unbindObject3D() {
		MasterRenderer.enableCulling();
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	private void prepareInstance(Entity entity) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation().x, entity.getRotation().y, entity.getRotation().z, entity.getScale().x, entity.getScale().y, entity.getScale().z);
		staticObjectShader.loadTransformationMatrix(transformationMatrix);
	}
	
	
	/**
	 * @deprecated
	 * This method is only able to render a single instance entity and will cause lag after multiple entity render calls
	 */
	@Deprecated
	public void render(Entity entity, StaticObjectShader staticObjectShader) {
		Object3D object = entity.getObject();
		Model3D model = entity.getObject().getModel3D();
		ModelTexture modelTexture = object.getModelTexture();
		
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		//System.out.println(entity.getPosition().x + ", " + entity.getPosition().y + ", " + entity.getPosition().z + " : " + entity.getRotation().x + ", " + entity.getRotation().y + ", " + entity.getRotation().z + " : " + entity.getScale().x + ", " + entity.getScale().y + ", " + entity.getScale().z);
		//System.out.println("TEXT: " + entity.getObject().getModelTexture().getTextureID());
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation().x, entity.getRotation().y, entity.getRotation().z, entity.getScale().x, entity.getScale().y, entity.getScale().z);
		staticObjectShader.loadTransformationMatrix(transformationMatrix);
		staticObjectShader.loadShineVariables(modelTexture.getShineDamper(), modelTexture.getReflectivity());
		
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, modelTexture.getTextureID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVerticiesCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
}
