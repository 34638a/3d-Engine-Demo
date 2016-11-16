package engine.rpg.items;

public class Inventory {

	Item[] items;
	
	public Inventory(int storageSpace) {
		items = new Item[storageSpace];
	}
}
