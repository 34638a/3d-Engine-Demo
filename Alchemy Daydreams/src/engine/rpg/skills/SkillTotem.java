package engine.rpg.skills;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

public class SkillTotem {
	
	private boolean forbiden = false;
	private boolean unlocked = false;
	private boolean completed = false;

	private Vector3f colour;
	private String totemName;
	private String totemLore;
	
	
	List<SkillTotem> prerequisites;
	
	public SkillTotem(Vector3f colour, String totemName, String totemLore) {
		this.colour = colour;
		this.totemName = totemName;
		this.totemLore = totemLore;
		prerequisites = new ArrayList<SkillTotem>();
	}
	
	public void addPrerequisite(SkillTotem skillTotem) {
		prerequisites.add(skillTotem);
	}
	
	public boolean isForbiden() {
		return forbiden;
	}
	public boolean isUnlocked() {
		return unlocked;
	}
	public boolean isCompleted() {
		return completed;
	}
	
	public void setForbiden(boolean status) {
		forbiden = status;
	}
	public void setUnlocked(boolean status) {
		unlocked = status;
	}
	public void setCompleted(boolean status) {
		completed = status;
	}
	
	public Vector3f getColour() {
		return colour;
	}
	public String getTotemName() {
		return totemName;
	}
	public String getTotemLore() {
		return totemLore;
	}
	
	public void checkUnlocked() {
		if (!unlocked) {
			for (SkillTotem skill : prerequisites) {
				if (!skill.isCompleted()) {
					return;
				}
			}
			unlocked = true;
		}
	}
}
