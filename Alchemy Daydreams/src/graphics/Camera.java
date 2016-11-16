package graphics;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import engine.rpg.entity.player.Player;
import settings.ControlSettings;
import settings.GraphicsSettings;

public class Camera {
	
	private Vector3f position = new Vector3f();
	private float pitch, yaw, roll;
	
	private float moveSpeed = 0.05f;
	
	private float distanceToPlayer = 5;
	private float angleAroundPlayer = 0;
	private float yIncrease = 0.5f;
	private Player player;
	
	public Camera(Player player, float pitch) {
		this.pitch = pitch;
		this.player = player;
	}
	
	public void move() {
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horiDist = calculateHorizontalDistance();
		float vertDist = calculateVerticalDistance();
		calculateCameraPosition(horiDist, vertDist);
		this.yaw = 180 - (player.getRotation().y + angleAroundPlayer);
		//System.out.println("Dist: " + distanceToPlayerDefault + ", " + position.x + ", " + position.y + ", " + position.z + ", " + pitch);
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(float horiDist, float vertDist) {
		float theta = player.getRotation().y + angleAroundPlayer;
		float offsetX = (float) (horiDist * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horiDist * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + vertDist + yIncrease;
	}
	private float calculateHorizontalDistance() {
		return (float) (distanceToPlayer * Math.cos(Math.toRadians(pitch)));
	}
	private float calculateVerticalDistance() {
		return (float) (distanceToPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom() {
		float zoomLevel = (float) (Mouse.getDWheel() * ControlSettings.getmouseScrollSpeed());
		if (ControlSettings.getScrollWheelUpToZoomIn()) {
			zoomLevel*=-1;
		}
		distanceToPlayer += zoomLevel;
		if (distanceToPlayer < GraphicsSettings.minDistance) {
			distanceToPlayer = GraphicsSettings.minDistance;
		} else if (distanceToPlayer > GraphicsSettings.maxDistance) {
			distanceToPlayer = GraphicsSettings.maxDistance;
		}//*/
	}
	private void calculatePitch() {
		if (Mouse.isButtonDown(2)) {
			float pitchChange = Mouse.getDY() * ControlSettings.getMouseLookSensitivityVertical();
			pitch -= pitchChange;
		}
		if (pitch < GraphicsSettings.minPitch) {
			pitch = GraphicsSettings.minPitch;
		} else if (pitch > GraphicsSettings.maxPitch) {
			pitch = GraphicsSettings.maxPitch;
		}
	}
	private void calculateAngleAroundPlayer() {
		if (Mouse.isButtonDown(2)) {
			float angleChange = Mouse.getDX() * ControlSettings.getMouseLookSensitivityHorizontal();
			angleAroundPlayer -= angleChange;
		}
	}
}
