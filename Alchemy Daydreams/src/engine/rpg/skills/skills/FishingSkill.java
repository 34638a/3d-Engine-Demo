package engine.rpg.skills.skills;

import engine.rpg.skills.SkillTotem;
import engine.rpg.skills.SkillTree;

public class FishingSkill extends SkillTree{

	private SkillTotem basicFishing; 
	
	
	@Override
	public void generateTree() {
		addSkillTotem(basicFishing);
	}
}
