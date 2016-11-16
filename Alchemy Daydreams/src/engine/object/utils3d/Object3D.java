package engine.object.utils3d;

import graphics.textures.ModelTexture;

public class Object3D {
	
	private Model3D model3D;
	private ModelTexture modelTexture;
	
	public Object3D(Model3D model3D, ModelTexture modelTexture) {
		this.model3D = model3D;
		this.modelTexture = modelTexture;
	}

	public Model3D getModel3D() {
		return model3D;
	}

	public ModelTexture getModelTexture() {
		return modelTexture;
	}
}
