package graphics.textures;

public class ModelTexture {
	
	private int textureID;
	
	private float shineDamper = 1;
	private float reflectivity = 0;
	
	private boolean hasTransparency = false;
	private boolean useFakeLighting = false;
	
	private int numberOfRows = 1;

	public ModelTexture(int textureID) {
		this.textureID = textureID;
	}
	
	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public void setHasTransparency(boolean shouldBeTransparent) {
		hasTransparency = shouldBeTransparent;
	}
	
	public boolean hasTransparency() {
		return hasTransparency;
	}
	
	public void setUseFakeLighting(boolean hasVerticleNormals) {
		useFakeLighting = hasVerticleNormals;
	}
	
	public boolean shouldUseFakeLighting() {
		return useFakeLighting;
	}
	
	public int getTextureID() {
		return textureID;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	
}
