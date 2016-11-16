package engine.rpg.entity.player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import settings.GraphicsSettings;
import engine.object.Entity;
import engine.object.utils3d.Object3D;
import engine.rpg.entity.AnimatedEntity;

public class Player extends AnimatedEntity{
	

	private float moveSpeed = 0.05f;
	private float rotationSpeed = 0.2f;
	
	public Player(Object3D object, Vector3f position, Vector3f rotation, Vector3f scale) {
		super(object, position, rotation, scale);
	}
	
	/*
	public void move() {
		float posx = 0, posy = 0, posz = 0;
		int x = 0, y = 0, z = 0;
		//xa += (xMove*Math.cos(yaw) - zMove*Math.sin(yaw))*movement;
		//za += (zMove*Math.cos(yRot) + xMove*Math.sin(yRot))*movement;
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			z--;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			z++;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			x--;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			x++;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			y--;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			y++;
		}
		
		posx = (float) ((x*Math.cos(Math.toRadians(getRotation().y)) - z*Math.sin(Math.toRadians(getRotation().y)))*moveSpeed);
		posz = (float) ((z*Math.cos(Math.toRadians(getRotation().y)) + x*Math.sin(Math.toRadians(getRotation().y)))*moveSpeed);
		posy = y*moveSpeed;
		getPosition().x += posx;
		getPosition().y += posy;
		getPosition().z += posz;
	}//*/
	
	public void checkInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			moveForwards();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			moveBackwards();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			moveLeft();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			moveRight();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			moveUp();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			moveDown();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			moveRotation(0, rotationSpeed, 0);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			moveRotation(0, -rotationSpeed, 0);
		}
	}

	@Override
	public void move() {
		checkInput();
		processMoveCommands();
	}
}
