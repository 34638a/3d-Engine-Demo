package settings;

public class ControlSettings {
	
	private static boolean scrollWheelUpToZoomIn = true;
	private static float mouseScrollSpeed = 0.01f;
	private static float mouseLookSensitivityVertical = 0.1f;
	private static float mouseLookSensitivityHorizontal = 0.3f;
	
	public static void setScrollWheelYpToZoomIn(boolean value) {
		scrollWheelUpToZoomIn = value;
	}
	public static boolean getScrollWheelUpToZoomIn() {
		return scrollWheelUpToZoomIn;
	}
	
	public static void setmouseScrollSpeed(float value) {
		mouseScrollSpeed = value;
	}
	public static float getmouseScrollSpeed() {
		return mouseScrollSpeed;
	}
	
	public static void setMouseLookSensitivityVertical(float value) {
		mouseLookSensitivityVertical = value;
	}
	public static float getMouseLookSensitivityVertical() {
		return mouseLookSensitivityVertical;
	}

	public static void setMouseLookSensitivityHorizontal(float value) {
		mouseLookSensitivityHorizontal = value;
	}
	public static float getMouseLookSensitivityHorizontal() {
		return mouseLookSensitivityHorizontal;
	}
}
