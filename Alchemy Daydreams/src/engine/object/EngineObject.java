package engine.object;

import java.awt.Graphics;

public abstract class EngineObject implements Ticking, Rendering{
	
	private int x , y, x2, y2;
	public boolean fowards = false;
	public boolean backwards = false;
	public boolean left = false;
	public boolean right = false;
	private int moveSpeed;
	
	private String objectName = "";
	private String objectID = "";
	
	public EngineObject(int x, int y, int moveSpeed, String objectName, String objectID) {
		this.x = x;
		this.y = y;
		this.moveSpeed = moveSpeed;
		this.objectName = objectName;
		this.objectID = objectID;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public void moveObject(int x, int y) {
		this.x += x;
		this.y += y;
	}
	public void setObjectPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void tickDrawnObject() {
		//animation.animate();
		if (fowards) {
			y2-=moveSpeed;
		}
		if (backwards) {
			y2+=moveSpeed;
		}
		if (left) {
			x2-=moveSpeed;
		}
		if (right) {
			x2+=moveSpeed;
		}
		if (x2 != 0 || y2 != 0) {
			moveObject(x2, y2);
		}
		
		x2 = 0;
		y2 = 0;
	}
	
	public void renderDrawnObject(Graphics g, int worldX, int worldY, int renderTiles) {
		/*
		if (x > worldX && y > worldY && x < (worldX + renderTiles) && y < (worldX + renderTiles)) {
			g.drawImage(animation.getCurrentSprite().getImage(), x - worldX, y - worldY, null);
		}//*/
	}

	public String getObjectName() {
		return objectName;
	}

	public String getObjectID() {
		return objectID;
	}
}
