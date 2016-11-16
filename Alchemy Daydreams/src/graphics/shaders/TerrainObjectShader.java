package graphics.shaders;

import graphics.Camera;
import graphics.lights.Light;
import graphics.shaders.utils.ShaderUtils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import utils.Maths;

public class TerrainObjectShader extends ShaderUtils{
	
	private static final String VERTEX_FILE = "src/graphics/shaders/shaders/vertexShader_terrain.txt";
	private static final String FRAGMENT_FILE = "src/graphics/shaders/shaders/fragmentShader_terrain.txt";
	
	private int location_transformMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColour;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColour;
	private int location_backgroundTexture;
	private int location_rTexture;
	private int location_gTexture;
	private int location_bTexture;
	private int location_blendTexture;
	
	public TerrainObjectShader() {
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
		location_skyColour 			= super.getUniformLocation("skyColour");
		location_backgroundTexture	= super.getUniformLocation("backgroundTexture");
		location_rTexture			= super.getUniformLocation("rTexture");
		location_gTexture			= super.getUniformLocation("gTexture");
		location_bTexture			= super.getUniformLocation("bTexture");
		location_blendTexture		= super.getUniformLocation("blendTexture");
	}
	
	public void connectTextureUnits() {
		super.loadIntToUniform(location_blendTexture, 0);
		super.loadIntToUniform(location_rTexture, 1);
		super.loadIntToUniform(location_gTexture, 2);
		super.loadIntToUniform(location_bTexture, 3);
		super.loadIntToUniform(location_blendTexture, 4);
	}
	
	public void loadSkyColour(float r, float g, float b) {
		super.loadVector3fToUniform(location_skyColour, new Vector3f(r,g,b));
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

