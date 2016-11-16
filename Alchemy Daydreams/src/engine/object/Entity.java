package engine.object;

import org.lwjgl.util.vector.Vector3f;

import engine.object.utils3d.Object3D;

public class Entity {
	
	private Object3D object;
	private Vector3f position, rotation, scale;
	
	private Entity parent;
	
	public Entity(Object3D object, Vector3f position, Vector3f rotation, Vector3f scale) {
		this.object = object;
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
	}
	public Entity(Object3D object, Entity parent, Vector3f position, Vector3f rotation, Vector3f scale) {
		this.object = object;
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
		this.parent = parent;
	}
	
	//Movement
	public void movePosition(Vector3f position) {
		this.position.x += position.x;
		this.position.y += position.y;
		this.position.z += position.z;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public void setPositionR(Vector3f position) {
		this.position.x += position.x;
		this.position.y += position.y;
		this.position.z += position.z;
	}
	
	//Rotation
	public void moveRotation(float rotationX, float rotationY, float rotationZ) {
		this.rotation.x += rotationX;
		this.rotation.y += rotationY;
		this.rotation.z += rotationZ;
	}
	public void setRotation(float rotationX, float rotationY, float rotationZ) {
		this.rotation.x = rotationX;
		this.rotation.y = rotationY;
		this.rotation.z = rotationZ;
	}
	
	
	
	public Object3D getObject() {
		return object;
	}
	public void setObject(Object3D object) {
		this.object = object;
	}
	public Vector3f getPosition() {
		
		Vector3f pos = new Vector3f();
		Vector3f rot = new Vector3f();
		
		if (parent != null) {
			pos.x = parent.getPosition().x;
			pos.y = parent.getPosition().y;
			pos.z = parent.getPosition().z;
			rot.x = parent.getRotation().x;
			rot.y = parent.getRotation().y;
			rot.z = parent.getRotation().z;
		}
		
		
		//				FIX THIS CODE IT DOES NOT WORK PROPERLY TO GET POSITION FROM ANCHORED ROTATION
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		pos.x += (position.x*((float) (Math.cos(Math.toRadians(rot.y)))) + position.z*((float) (Math.sin(Math.toRadians(rot.y)))));
		pos.y += position.y;
		pos.z += (position.z*((float) (Math.cos(Math.toRadians(rot.y)))) - position.x*((float) (Math.sin(Math.toRadians(rot.y)))));
		
		//calculatePosition();
		
		/*
		pos.x += (position.x*((float) (Math.cos(Math.toRadians(rot.z)))) - position.y*((float) (Math.sin(Math.toRadians(rot.z)))));
		pos.y += (position.y*((float) (Math.cos(Math.toRadians(rot.z)))) + position.x*((float) (Math.sin(Math.toRadians(rot.z)))));
		pos.z += position.z;
		
		pos.x += position.x;
		pos.y += (position.y*((float) (Math.cos(Math.toRadians(rot.x)))) - position.z*((float) (Math.sin(Math.toRadians(rot.x)))));
		pos.z += (position.z*((float) (Math.cos(Math.toRadians(rot.x)))) + position.y*((float) (Math.sin(Math.toRadians(rot.x)))));
		
		pos.x /= 3;
		pos.y /= 3;
		pos.z /= 3;
		//*/
		//xa += (xMove*Math.cos(yRot) - zMove*Math.sin(yRot))*movement;
		//za += (zMove*Math.cos(yRot) + xMove*Math.sin(yRot))*movement;
		
		return pos;
	}
	/*public Vector3f calculatePosition(Vector3f relativePosition, Vector3f relativeRotation) {
		
		float magnitude = Math.sqrt((position.x ) + () + ());
		
		//Triangle 1
		
		float pos1x = position.x*Math.sin(a)
		
		//Triangle 2
		
		//Triangle 3
		
		return position;
	}//*/
	public Vector3f getScale() {
		return scale;
	}
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	public Vector3f getRotation() {
		
		Vector3f rot = new Vector3f();
		
		if (parent != null) {
			rot.x = parent.getRotation().x;
			rot.y = parent.getRotation().y;
			rot.z = parent.getRotation().z;
		}
		
		rot.x += rotation.x;
		rot.y += rotation.y;
		rot.z += rotation.z;
		
		return rot;
	}
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	
	public void rotateToFace(Entity entity) {
		
	}
}
