import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class GUI extends Application {
	private Label lblSentence, lblErrors, lblShowErrors;
	private Button btnStart, btnStop;
	private TextField txtfSentence, txtfTyping;
	private String[] sentences = new String[5];
	private int errors;
	private int sentenceIndex;

	public void init() {
		// Initialize the sample sentences.
		sentences[0] = "Mary had a little lamb.";
		sentences[1] = "Whose fleece was white and snow";
		sentences[2] = "And everywhere that mary went";
		sentences[3] = "The lamb was sure to go";
		sentences[4] = "That's all folks";
		errors = 0;
		sentenceIndex = 0;
	}

	private void preparePrimaryStage(Stage primaryStage) {
		// Set the stage
		primaryStage.setTitle("Typing Tutor");
		// set the width and height
		primaryStage.setWidth(500);
		primaryStage.setHeight(250);

		HBox mainLayout = new HBox(10);
		mainLayout.setPadding(new Insets(20));

		VBox column1 = new VBox(10);
		column1.setMinWidth(300);

		lblSentence = new Label("Type in the lower text field:");
		txtfSentence = new TextField();
		txtfSentence.setEditable(false);
		txtfTyping = new TextField();
		lblErrors = new Label("Errors:");
		lblShowErrors = new Label();

		// On stop typing compare two sentences and check for errors
		ChangeListener<String> typingListener = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.trim().equals(sentences[sentenceIndex])) {
					if (++sentenceIndex >= sentences.length) {
						txtfTyping.textProperty().removeListener(this);
						return;
					}
					txtfSentence.setText(sentences[sentenceIndex]);
					Platform.runLater(() -> {
						txtfTyping.clear();
					});
				} else {
					txtfTyping.setStyle("-fx-text-fill: white;");
					errors = 0;
					// Count errors in sentence
					for (int i = 0; i < newValue.length() && i < sentences[sentenceIndex].length(); i++) {
						if (newValue.charAt(i) != sentences[sentenceIndex].charAt(i)) {
							errors++;
						}
					}
					if(errors > 0) {
						txtfTyping.setStyle("-fx-text-fill: red;");
					}
					lblShowErrors.setText(errors + "");
				}
			}
		};

		txtfTyping.textProperty().addListener(typingListener);

		HBox buttonsRow = new HBox(15);
		buttonsRow.setAlignment(Pos.CENTER_RIGHT);
		btnStart = new Button("Start");
		btnStop = new Button("Stop");
		// Size the buttons.
		btnStart.setMinWidth(70);
		btnStop.setMinWidth(70);

		// Handle events on the components
		btnStart.setOnAction(ae -> {
			errors = 0;
			sentenceIndex = 0;
			// Update the errors label
			lblShowErrors.setText("0");

			// clear the lower text field
			txtfTyping.clear();
			// show the first sentence.
			txtfSentence.setText(sentences[sentenceIndex]);
		});

		// Stop waiting monitoring typing on stop
		btnStop.setOnAction(ae -> {
			txtfTyping.textProperty().removeListener(typingListener);
		});

		// Add buttons to HBox
		buttonsRow.getChildren().addAll(btnStart, btnStop);

		// Add components to the layers
		column1.getChildren().addAll(lblSentence, txtfSentence, txtfTyping, buttonsRow);

		VBox column2 = new VBox(10);
		HBox errorsRow = new HBox(100);
		errorsRow.getChildren().addAll(lblErrors, lblShowErrors);

		column2.getChildren().add(errorsRow);

		mainLayout.getChildren().addAll(column1, column2);

		// create a scene.
		Scene s = new Scene(mainLayout);

		// Apply a style for a application
		s.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

		// Set the scene.
		primaryStage.setScene(s);
		// Show the stage.
		primaryStage.show();
	}

	public void start(Stage primaryStage) {
		preparePrimaryStage(primaryStage);
	}

	public static void main(String[] args) {
		// Launch the application
		launch();
	}
}
