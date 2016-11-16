package engine.object.terrain;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector3f;

import engine.object.utils3d.ModelLoader3D;
import graphics.textures.TerrainTexture;
import graphics.textures.TerrainTexturePack;
import settings.GraphicsSettings;

public class TerrainController {
	
	private int x = 0, y = 0;
	private ModelLoader3D model3DLoader;
	
	BufferedImage heightMap;
	
	private TerrainTexturePack terrainTexturePack;
	private TerrainTexture blendMap;
	
	List<Terrain> terrainTiles;
	List<Terrain> renderTiles;
	
	public TerrainController(ModelLoader3D model3DLoader) {
		
		try {
			heightMap = ImageIO.read(this.getClass().getResourceAsStream("/textures/maps/heightMap.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		terrainTiles = new ArrayList<Terrain>();
		this.model3DLoader = model3DLoader;
		this.terrainTexturePack = new TerrainTexturePack(
				new TerrainTexture(model3DLoader.loadTextureJPG("textures/terrain/grass")),
				new TerrainTexture(model3DLoader.loadTextureJPG("textures/terrain/pebbles")),
				new TerrainTexture(model3DLoader.loadTextureJPG("textures/terrain/darkMarble")),
				new TerrainTexture(model3DLoader.loadTextureJPG("textures/terrain/road"))
		);
		this.blendMap = new TerrainTexture(model3DLoader.loadTexturePNG("textures/maps/blendMap"));
	}
	
	public List<Terrain> getTerrainTilesToRender(Vector3f playerPosition) {
		
		int xTile = (int) ((playerPosition.x - (playerPosition.x%GraphicsSettings.terrainSize))/GraphicsSettings.terrainSize);
		int yTile = (int) ((playerPosition.z - (playerPosition.z%GraphicsSettings.terrainSize))/GraphicsSettings.terrainSize);
		
		if (x == xTile && y == yTile && renderTiles != null) {
			return renderTiles;
		}
		
		System.out.println(xTile + ", " + yTile);
		
		renderTiles = new ArrayList<Terrain>();
		
		int x1 = -GraphicsSettings.terrainRenderDistance, y1;
		while(true) {
			y1 = -GraphicsSettings.terrainRenderDistance;
			while(true) {
				renderTiles.add(new Terrain(x1 + xTile, y1 + yTile, model3DLoader, terrainTexturePack, blendMap, heightMap));
				if (y1 == GraphicsSettings.terrainRenderDistance) {
					break;
				}
				y1++;
			}
			if (x1 == GraphicsSettings.terrainRenderDistance) {
				break;
			}
			x1++;
		}
		
		x = xTile;
		y = yTile;
		
		return renderTiles;
	}
}
