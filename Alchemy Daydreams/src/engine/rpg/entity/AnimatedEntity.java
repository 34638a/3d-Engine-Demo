package engine.rpg.entity;

import org.lwjgl.util.vector.Vector3f;

import engine.object.Entity;
import engine.object.utils3d.Object3D;

public abstract class AnimatedEntity extends Entity{

	
	int mx = 0, my = 0, mz = 0;
	private float moveSpeed = 0.0f;
	
	//Constructors
	public AnimatedEntity(Object3D object, Vector3f position,
			Vector3f rotation, Vector3f scale) {
		super(object, position, rotation, scale);
	}
	public AnimatedEntity(Object3D object, Entity entity, Vector3f position,
			Vector3f rotation, Vector3f scale) {
		super(object, position, rotation, scale);
	}
	
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	
	public abstract void move();
	public void processMoveCommands() {
		float posx = 0, posy = 0, posz = 0;
		
		//posx = (float) (mx*Math.cos(Math.toRadians(getRotation().y) + mz*Math.sin(Math.toRadians(getRotation().y)))*moveSpeed);
		//posz = (float) (mz*Math.cos(Math.toRadians(getRotation().y) - mx*Math.sin(Math.toRadians(getRotation().y)))*moveSpeed);
		
		//posx = (float) (mx*(Math.sin(Math.toRadians(getRotation().y)*moveSpeed)));
		//posz = (float) (mz*(Math.cos(Math.toRadians(getRotation().y)*moveSpeed)));
		
		posx = (float) (mx * moveSpeed * Math.sin(Math.toRadians(getRotation().y)));
		posy = (float) (my * moveSpeed);
		posz = (float) (mx * moveSpeed * Math.cos(Math.toRadians(getRotation().y)));
		
		setPositionR(new Vector3f(posx, posy, posz));
		//System.out.println("M: " + mx + ", " + mz + " : POS: " + posx + ", " + posz + " : CAL: " + (mx*Math.cos(Math.toRadians(getRotation().y) + mz*Math.sin(Math.toRadians(getRotation().y)))) + ", " + (mz*Math.cos(Math.toRadians(getRotation().y) - mx*Math.sin(Math.toRadians(getRotation().y)))));
		mx = 0;
		my = 0;
		mz = 0;
	}
	
	public void moveForwards() {
		if (mx != -1) {
			mx++;
		}
	}
	public void moveBackwards() {
		if (mx != 1) {
			mx--;
		}
	}
	public void moveRight() {
		if (mz != 1) {
			mz++;
		}
	}
	public void moveLeft() {
		if (mz != -1) {
			mz--;
		}
	}
	public void moveUp() {
		if (my != 1) {
			my++;
		}
	}
	public void moveDown() {
		if (my != -1) {
			my--;
		}
	}
}
