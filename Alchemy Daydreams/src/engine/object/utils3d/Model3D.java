package engine.object.utils3d;

public class Model3D {
	
	private int verticiesCount;
	private int vaoID;
	
	public Model3D(int vaoID, int verticiesCount) {
		this.vaoID = vaoID;
		this.verticiesCount = verticiesCount;
	}
	
	public int getVerticiesCount() {
		return verticiesCount;
	}
	public int getVAOID() {
		return vaoID;
	}
}
