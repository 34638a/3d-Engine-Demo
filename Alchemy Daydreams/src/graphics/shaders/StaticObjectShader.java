package graphics.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import utils.Maths;
import graphics.Camera;
import graphics.lights.Light;
import graphics.shaders.utils.ShaderUtils;

public class StaticObjectShader extends ShaderUtils{
	
	private static final String VERTEX_FILE = "src/graphics/shaders/shaders/vertexShader_entity.txt";
	private static final String FRAGMENT_FILE = "src/graphics/shaders/shaders/fragmentShader_entity.txt";
	
	private int location_transformMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColour;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_fakeLighting;
	private int location_skyColour;
	private int location_numberOfRows;
	private int location_offset;
	
	public StaticObjectShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	public void getAllUniforrmLocations() {
		location_transformMatrix 	= super.getUniformLocation("transformMatrix");
		location_projectionMatrix 	= super.getUniformLocation("projectionMatrix");
		location_viewMatrix 		= super.getUniformLocation("viewMatrix");
		location_lightPosition 		= super.getUniformLocation("lightPosition");
		location_lightColour 		= super.getUniformLocation("lightColour");
		location_shineDamper 		= super.getUniformLocation("shineDamper");
		location_reflectivity 		= super.getUniformLocation("reflectivity");
		location_fakeLighting 		= super.getUniformLocation("useFakeLighting");
		location_skyColour 			= super.getUniformLocation("skyColour");
		location_numberOfRows 		= super.getUniformLocation("numberOfRows");
		location_offset 			= super.getUniformLocation("offset");
	}

	public void loadTextureNumberOfRows(float numberOfRows) {
		super.loadFloatToUniform(location_numberOfRows, numberOfRows);
	}
	
	public void loadTextureOffset(float offsetX, float offsetY) {
		super.loadVector2fToUniform(location_offset, new Vector2f(offsetX, offsetY));
	}
	
	public void loadSkyColour(float r, float g, float b) {
		super.loadVector3fToUniform(location_skyColour, new Vector3f(r,g,b));
	}
	
	public void loadFakeLightingBoolean(boolean shouldUseFakeLighting) {
		super.loadBooleanToUniform(location_fakeLighting, shouldUseFakeLighting);
	}
	
	public void loadShineVariables(float shineDamper, float reflectivity) {
		super.loadFloatToUniform(location_shineDamper, shineDamper);
		super.loadFloatToUniform(location_reflectivity, reflectivity);
	}
	
	public void loadLight(Light light) {
		super.loadVector3fToUniform(location_lightPosition, light.getPosition());
		super.loadVector3fToUniform(location_lightColour, light.getColour());
	}
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrixToUniform(location_transformMatrix, matrix);
	}
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrixToUniform(location_projectionMatrix, matrix);
	}
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrixToUniform(location_viewMatrix, viewMatrix);
	}
}
