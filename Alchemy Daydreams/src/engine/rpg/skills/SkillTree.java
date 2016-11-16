package engine.rpg.skills;

import java.util.ArrayList;
import java.util.List;

public abstract class SkillTree {
	
	List<SkillTotem> skillTotems;
	
	public void SkillTree(){
		skillTotems = new ArrayList<SkillTotem>();
		generateTree();
	}
	
	public abstract void generateTree();
	
	public void addSkillTotem(SkillTotem totem) {
		skillTotems.add(totem);
	}
	
	public SkillTotem getTotem(int totemID) {
		return skillTotems.get(totemID);
	}
	public SkillTotem getTotem(String totemID) {
		for (int i = 0; i < skillTotems.size(); i++) {
			if (skillTotems.get(i).getTotemName().toUpperCase().equals(totemID)) {
				return skillTotems.get(i);
			}
		}
		return null;
	}
}
