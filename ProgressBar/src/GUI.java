
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class GUI extends Application {
	private Button increase;
	private Button decrease;
	private ProgressBar progressBar;
	private ProgressIndicator progressIndicator;

	private void preparePrimaryStage(Stage primaryStage) {
		// Set the title.
		primaryStage.setTitle("P2");
		// Set the width.
		primaryStage.setWidth(150);
		// set the height
		primaryStage.setHeight(400);

		VBox mainLayout = new VBox(40);
		mainLayout.setPadding(new Insets(10, 10, 10, 10));

		// Create progress text
		Text progressText = new Text("Progress: ");

		VBox progressBox = new VBox(50);
		progressBox.setAlignment(Pos.CENTER);

		// Create a progress bar and rotate it to be vertical
		progressBar = new ProgressBar(0);
		progressBar.setPrefWidth(70);
		progressBar.getTransforms().addAll(new Translate(-progressBar.getWidth() + 25, progressBar.getWidth() + 50),
				new Rotate(-90, 0, 0));
		progressIndicator = new ProgressIndicator(0);
		// Link bar and indicator value
		progressIndicator.progressProperty().bind(progressBar.progressProperty());
		progressBox.getChildren().addAll(progressBar, progressIndicator);

		VBox buttonsBox = new VBox(15);
		buttonsBox.setAlignment(Pos.CENTER);

		increase = new Button("Increase");
		increase.setOnAction(e -> {
			String sValue = (String) String.format("%.2f", progressBar.getProgress() + 0.05);
			Double newValue = Double.parseDouble(sValue);
			if (newValue >= 0) {
				progressBar.setProgress(newValue);
			}
		});

		decrease = new Button("Decrease");
		decrease.setOnAction(e -> {
			String sValue = (String) String.format("%.2f", progressBar.getProgress() - 0.05);
			Double newValue = Double.parseDouble(sValue);
			if (newValue >= 0) {
				progressBar.setProgress(newValue);
			}
		});

		buttonsBox.getChildren().addAll(increase, decrease);

		mainLayout.getChildren().addAll(progressText, progressBox, buttonsBox);

		// Create a scene.
		Scene s = new Scene(mainLayout);
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
