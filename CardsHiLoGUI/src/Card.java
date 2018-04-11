
public class Card {
	private String name;
	private int value;
	private String imageURL;

	public Card(String name, int value, String imageURL) {
		this.name = name;
		this.value = value;
		// The card image file location in project
		this.imageURL = imageURL;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public String getImageURL() {
		return imageURL;
	}

	public boolean isEqual(Card card) {
		return value == card.value;
	}

	public boolean isLower(Card card) {
		return value < card.value;
	}

	public boolean isHigher(Card card) {
		return value > card.value;
	}

	public String toString() {
		return "Card: " + name + ", Value: " + value;
	}
}
