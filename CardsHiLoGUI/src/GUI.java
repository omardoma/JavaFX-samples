
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {
	private MenuBar menuBar;
	private Button dealFirstCard;
	private Button dealSecondCard;
	private ImageView firstCard;
	private ImageView secondCard;
	private RadioButton higher;
	private RadioButton lower;
	private Text firstCardStatus;
	private Text secondCardStatus;
	private ProgressBar progressBar;
	private ProgressIndicator progressIndicator;
	private DeckOfCards cardsDeck;
	private Card currentCard;
	private int consecutiveRightGuesses;
	private static final int NO_FOR_WIN = 5;

	public GUI() {
		// Initialize a new deck of cards
		cardsDeck = new DeckOfCards();
		consecutiveRightGuesses = 0;
	}

	private void showDialog(AlertType alertType, String title, String msg) {
		// Show an alert with a message
		Alert alert = new Alert(alertType, msg);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.showAndWait();
	}

	private void initMenubar() {
		// Create File menu
		Menu fileMenu = new Menu("File");
		// Create New Game Item in File menu
		MenuItem newGame = new MenuItem("New Game");
		// Start a new game on new game press
		newGame.setOnAction(e -> {
			newGame();
		});
		// Create Shuffle Item in File menu
		MenuItem shuffle = new MenuItem("Shuffle");
		// Shuffle card deck on shuffle press
		shuffle.setOnAction(e -> {
			cardsDeck.shuffle();
		});
		// Create Exit Item in File menu
		MenuItem exit = new MenuItem("Exit");
		// Exit on press
		exit.setOnAction(e -> {
			Platform.exit();
		});
		// add items to File menu
		fileMenu.getItems().addAll(newGame, shuffle, exit);

		// Create Help menu
		Menu helpMenu = new Menu("Help");
		// Create about item in Help menu
		MenuItem about = new MenuItem("About");
		// Show alert with name and id on click
		about.setOnAction(e -> {
			showDialog(AlertType.INFORMATION, "About", "Ashokreddy Edara - 2944699");
		});
		// Add item to Help menu
		helpMenu.getItems().add(about);

		// Add the two menus to a menu bar
		menuBar = new MenuBar(fileMenu, helpMenu);
	}

	private void newGame() {
		// Restart new game by resetting everything
		cardsDeck = new DeckOfCards();
		currentCard = cardsDeck.dealTopCard();
		firstCard.setImage(new Image(currentCard.getImageURL()));
		secondCard.setImage(null);
		higher.setSelected(true);
		dealFirstCard.setDisable(true);
		dealSecondCard.setDisable(false);
		consecutiveRightGuesses = 0;
		progressBar.setProgress(0);
	}

	private void preparePrimaryStage(Stage primaryStage) {
		// Set the title.
		primaryStage.setTitle("Hi-Lo Card Game");
		// Set the width.
		primaryStage.setWidth(600);
		// set the height
		primaryStage.setHeight(400);

		// Create menu bar and menus
		initMenubar();

		// Create a BorderPane layout
		BorderPane row1 = new BorderPane();
		row1.setPadding(new Insets(0, 25, 0, 0));

		// Create firstCard text
		Text firstCardText = new Text("First Card Dealt: ");
		// Create secondCard text
		Text secondCardText = new Text("Second Card Dealt: ");

		row1.setLeft(firstCardText);
		row1.setRight(secondCardText);

		// Create a BorderPane layout
		BorderPane row2 = new BorderPane();
		row2.setPadding(new Insets(0, 25, 0, 0));

		// Create the two image views for the cards
		currentCard = cardsDeck.dealTopCard();
		// Set image view to the imageURL of the card
		firstCard = new ImageView(currentCard.getImageURL());
		// Set image size
		firstCard.setPreserveRatio(true);
		firstCard.setFitWidth(165);
		secondCard = new ImageView();
		secondCard.setPreserveRatio(true);
		secondCard.setFitWidth(165);

		VBox controls = new VBox(10);
		controls.setPadding(new Insets(10, 20, 30, 20));

		Text nextCard = new Text("Next card will be:");

		// Create the options radio buttons
		final ToggleGroup group = new ToggleGroup();
		higher = new RadioButton("Higher");
		higher.setToggleGroup(group);
		higher.setSelected(true);
		lower = new RadioButton("Lower");
		lower.setToggleGroup(group);

		// Create dealFirstCard Button
		dealFirstCard = new Button("<- Deal First Card");
		dealFirstCard.setPrefWidth(160);
		dealFirstCard.setDisable(true);
		dealFirstCard.setOnAction(e -> {
			Card card = cardsDeck.dealTopCard();
			if (card != null) {
				firstCard.setImage(new Image(card.getImageURL()));
				if (higher.isSelected()) {
					if (card.isHigher(currentCard)) {
						firstCardStatus.setText("Higher. You win!");
						consecutiveRightGuesses++;
						if (consecutiveRightGuesses == NO_FOR_WIN) {
							showDialog(AlertType.INFORMATION, "Congratulations", "You Won!");
							dealFirstCard.setDisable(true);
							dealSecondCard.setDisable(true);
						}
					} else if (card.isLower(currentCard)) {
						firstCardStatus.setText("Lower. You lose!");
						consecutiveRightGuesses = 0;
					} else {
						firstCardStatus.setText("Equal. You lose!");
						consecutiveRightGuesses = 0;
					}
				} else {
					if (card.isLower(currentCard)) {
						firstCardStatus.setText("Lower. You win!");
						consecutiveRightGuesses++;
						if (consecutiveRightGuesses == NO_FOR_WIN) {
							showDialog(AlertType.INFORMATION, "Congratulations", "You Won!");
							dealFirstCard.setDisable(true);
							dealSecondCard.setDisable(true);
						}
					} else if (card.isHigher(currentCard)) {
						firstCardStatus.setText("Higher. You lose!");
						consecutiveRightGuesses = 0;
					} else {
						firstCardStatus.setText("Equal. You lose!");
						consecutiveRightGuesses = 0;
					}
				}
				secondCardStatus.setText("");
				progressBar.setProgress((double)consecutiveRightGuesses / NO_FOR_WIN);
				currentCard = card;
			} else {
				showDialog(AlertType.WARNING, "Empty Deck", "No cards left in the deck.");
			}
		});
		// Create dealSecondCard Button
		dealSecondCard = new Button("Deal Second Card ->");
		dealSecondCard.setPrefWidth(160);
		dealSecondCard.setOnAction(e -> {
			Card card = cardsDeck.dealTopCard();
			if (card != null) {
				secondCard.setImage(new Image(card.getImageURL()));
				if (dealFirstCard.isDisabled()) {
					dealFirstCard.setDisable(false);
				}
				if (higher.isSelected()) {
					if (card.isHigher(currentCard)) {
						secondCardStatus.setText("Higher. You win!");
						consecutiveRightGuesses++;
						if (consecutiveRightGuesses == NO_FOR_WIN) {
							showDialog(AlertType.INFORMATION, "Congratulations", "You Won!");
							dealFirstCard.setDisable(true);
							dealSecondCard.setDisable(true);
						}
					} else if (card.isLower(currentCard)) {
						secondCardStatus.setText("Lower. You lose!");
						consecutiveRightGuesses = 0;
					} else {
						secondCardStatus.setText("Equal. You lose!");
						consecutiveRightGuesses = 0;
					}
				} else {
					if (card.isLower(currentCard)) {
						secondCardStatus.setText("Lower. You win!");
						consecutiveRightGuesses++;
						if (consecutiveRightGuesses == NO_FOR_WIN) {
							showDialog(AlertType.INFORMATION, "Congratulations", "You Won!");
							dealFirstCard.setDisable(true);
							dealSecondCard.setDisable(true);
						}
					} else if (card.isHigher(currentCard)) {
						secondCardStatus.setText("Higher. You lose!");
						consecutiveRightGuesses = 0;
					} else {
						secondCardStatus.setText("Equal. You lose!");
						consecutiveRightGuesses = 0;
					}
				}
				firstCardStatus.setText("");
				progressBar.setProgress((double)consecutiveRightGuesses / NO_FOR_WIN);
				currentCard = card;
			} else {
				showDialog(AlertType.WARNING, "Empty Deck", "No cards left in the deck.");
			}
		});

		FlowPane progressPanel = new FlowPane();
		progressPanel.setPadding(new Insets(10));
		progressPanel.setHgap(10);

		progressBar = new ProgressBar(0);
		progressIndicator = new ProgressIndicator(0);
		progressIndicator.progressProperty().bind(progressBar.progressProperty());
		progressIndicator.setPrefSize(30, 30);
		progressPanel.getChildren().addAll(progressBar, progressIndicator);

		controls.getChildren().addAll(nextCard, higher, lower, dealFirstCard, dealSecondCard, progressPanel);

		row2.setLeft(firstCard);
		row2.setCenter(controls);
		row2.setRight(secondCard);

		// Create a BorderPane layout
		BorderPane row3 = new BorderPane();
		row3.setPadding(new Insets(0, 25, 0, 0));

		// Create firstCardStatus text
		firstCardStatus = new Text();
		// Create secondCardStatus text
		secondCardStatus = new Text();

		row3.setLeft(firstCardStatus);
		row3.setRight(secondCardStatus);

		// Create a VBox layout
		VBox vBox = new VBox(15);
		vBox.setPadding(new Insets(10, 15, 30, 15));

		vBox.getChildren().addAll(row1, row2, row3);

		// Create a scene.
		Scene s = new Scene(new VBox(menuBar, vBox));
		s.getStylesheets().add("styles.css");
		// Set the scene.
		primaryStage.setScene(s);
		// Show application window
		primaryStage.show();
	}

	public void start(Stage primaryStage) {
		preparePrimaryStage(primaryStage);
	}

	public static void main(String[] args) {
		// Launch the application.
		launch();
	}
}
