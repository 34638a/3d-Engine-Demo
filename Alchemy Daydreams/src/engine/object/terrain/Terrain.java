package engine.object.terrain;

import java.awt.image.BufferedImage;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import settings.GraphicsSettings;
import utils.Maths;
import engine.object.utils3d.Model3D;
import engine.object.utils3d.ModelLoader3D;
import engine.object.utils3d.Object3D;
import graphics.textures.ModelTexture;
import graphics.textures.TerrainTexture;
import graphics.textures.TerrainTexturePack;

public class Terrain {
	
	public static final int VERTEX_COUNT = 128;
	private static final int MAX_HEIGHT = 10;
	private static final int MAX_PIXEL_COLOUR = 256*256*256;
	
	private float x, z;
	private Model3D model;
	private TerrainTexturePack textures;
	private TerrainTexture blendMap;
	
	public float[][] heights;
	
	public Terrain(int gridX, int gridZ, ModelLoader3D modelLoader, TerrainTexturePack textures, TerrainTexture blendMap, BufferedImage heightMap) {
		this.textures = textures;
		this.blendMap = blendMap;
		this.x = gridX * GraphicsSettings.terrainSize;
		this.z = gridZ * GraphicsSettings.terrainSize;
		
		this.model = generateTerrain(gridX, gridZ, modelLoader, heightMap);
	}
	
	public float getX() {
		return x;
	}
	public float getZ() {
		return z;
	}
	public Model3D getModel() {
		return model;
	}
	public TerrainTexturePack getTexturePack() {
		return textures;
	}
	public TerrainTexture getBlendMap() {
		return blendMap;
	}
	
	public float getHeightOfTerrain(float worldX, float worldZ) {
		
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		float gridSquareSize = GraphicsSettings.terrainSize / (float) heights.length - 1;
		
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		if (gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) {
			return 0;
		}
		
		float xCoord = (terrainX % gridSquareSize)/gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize)/gridSquareSize;
		
		float cal;
		if (xCoord <= (1-zCoord)) {
			cal = Maths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(0, heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		} else {
			cal = Maths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1, heights[gridX + 1][gridZ + 1], 1), new Vector3f(0, heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}
		
		return cal;
	}

	private Model3D generateTerrain(int gridPosX, int gridPosY, ModelLoader3D modelLoader, BufferedImage image){
		
		BufferedImage hMap = calculateImageProportion(gridPosX, gridPosY, image);
		
		heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * GraphicsSettings.terrainSize;
				float height = calculateTerrainVertexHeight(j, i, hMap);
				heights[j][i] = height;
				vertices[vertexPointer*3+1] = height;
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * GraphicsSettings.terrainSize;
				normals[vertexPointer*3] = 0;
				normals[vertexPointer*3+1] = 1;
				normals[vertexPointer*3+2] = 0;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return modelLoader.loadModelToVAO(vertices, textureCoords, normals, indices);
	}
	
	public float calculateTerrainVertexHeight(int xPos, int zPos, BufferedImage image) {
		
		if (xPos < 0 || xPos >= image.getWidth() || zPos < 0 || zPos >= image.getHeight()) {
			return 0;
		}
		
		float height = image.getRGB(xPos, zPos);
		height += MAX_PIXEL_COLOUR/2f;
		height /= MAX_PIXEL_COLOUR/2f;
		height *= MAX_HEIGHT;
		return height;
	}
	
	public BufferedImage calculateImageProportion(int posX, int posY, BufferedImage image) {
		BufferedImage img;
		try {
			img = image.getSubimage(posX*(VERTEX_COUNT-1), posY*(VERTEX_COUNT-1), VERTEX_COUNT, VERTEX_COUNT);
		} catch(Exception e) {
			img = new BufferedImage(VERTEX_COUNT, VERTEX_COUNT, BufferedImage.TYPE_INT_RGB);
		}
		return img;
	}
}
