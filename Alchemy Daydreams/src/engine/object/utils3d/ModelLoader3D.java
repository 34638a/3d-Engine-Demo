package engine.object.utils3d;

import java.io.FileInputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;


public class ModelLoader3D {
	
	private List<Integer> vaoList = new ArrayList<Integer>();
	private List<Integer> vboList = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	
	private final float mipMapTextureRate = 1.5f;
	
	public Model3D convertDataToModel3D(Model3DData data) {
		return loadModelToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
	}
	
	public Model3D loadModelToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);		//model vertices data
		storeDataInAttributeList(1, 2, textureCoords);	//model texture data
		storeDataInAttributeList(2, 3, normals);	//model normals data
		unbindVAO();
		return new Model3D(vaoID, indices.length);
	}
	
	public int loadTexturePNG(String filePath) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+filePath+".png"));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -mipMapTextureRate);
		} catch (Exception e) {
			System.out.println("Unable to locate the File: " + filePath);
			e.printStackTrace();
		}
		int textureID = texture.getTextureID();
		System.out.println("TextureID: " + textureID);
		textures.add(textureID);
		return textureID;
	}
	public int loadTextureJPG(String filePath) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("JPG", new FileInputStream("res/"+filePath+".jpg"));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -mipMapTextureRate);
		} catch (Exception e) {
			System.out.println("Unable to locate the File: " + filePath);
			e.printStackTrace();
		}
		int textureID = texture.getTextureID();
		System.out.println("TextureID: " + textureID);
		textures.add(textureID);
		return textureID;
	}
	
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaoList.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	public void cleanUp() {
		for (int vao : vaoList) {
			GL30.glDeleteVertexArrays(vao);
		}
		for (int vbo : vboList) {
			GL15.glDeleteBuffers(vbo);
		}
		for (int texture : textures) {
			GL11.glDeleteTextures(texture);
		}
	}
	private void storeDataInAttributeList(int attributeNumber, int coordsSize, float[] data) {
		int vboID = GL15.glGenBuffers();
		vboList.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataToFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, coordsSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	private FloatBuffer storeDataToFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private void bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers();
		vboList.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataToIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	private IntBuffer storeDataToIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
