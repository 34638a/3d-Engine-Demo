package engine.rpg.books;

public abstract class Book {
	
	private String title;
	private String content;
	
	public Book(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
}
