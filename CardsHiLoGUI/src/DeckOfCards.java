
import java.util.ArrayList;
import java.util.Collections;

public class DeckOfCards {
	private ArrayList<Card> cards;
	private static final String IMG_FORMAT = ".png";
	public static final int DECK_SIZE = 52;

	public DeckOfCards() {
		cards = new ArrayList<>();
		initCards();
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	private void initCards() {
		// Fill symbol cards (Ace, Jack, Queen, King) and number cards for each type
		// (Clubs, Diamonds,
		// Hearts, Spades)
		String[] symbols = { "A", "J", "Q", "K" };
		String[] types = { "C", "D", "H", "S" };
		int[] symbolsValues = { 1, 11, 12, 13 };

		for (String type : types) {
			// Fill symbol cards for this type
			for (int i = 0; i < symbols.length; i++) {
				cards.add(new Card(symbols[i] + type, symbolsValues[i], "res/imgs/" + symbols[i] + type + IMG_FORMAT));
			}
			// Fill number cards for this type
			for (int i = 2; i <= 10; i++) {
				cards.add(new Card(i + type, i, "res/imgs/" + i + type + IMG_FORMAT));
			}
		}
		shuffle();
	}

	public boolean isEmpty() {
		return cards.isEmpty();
	}

	public void shuffle() {
		// Shuffle cards list
		Collections.shuffle(cards);
	}

	public Card dealTopCard() {
		// If there is still cards in the deck remove the top one and return it
		if (!isEmpty()) {
			return cards.remove(0);
		}
		return null;
	}

	public String toString() {
		// Print deck of cards
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Card card : cards) {
			sb.append(card.toString()).append(", ");
		}
		return sb.append("]").toString();
	}
}
