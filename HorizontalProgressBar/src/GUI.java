
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {
	private Button increase;
	private Button decrease;
	private ProgressBar progressBar;
	private ProgressIndicator progressIndicator;

	private void preparePrimaryStage(Stage primaryStage) {
		// Set the title.
		primaryStage.setTitle("Progress Bar 1");
		// Set the width.
		primaryStage.setWidth(400);
		// set the height
		primaryStage.setHeight(180);

		VBox mainLayout = new VBox(10);
		mainLayout.setPadding(new Insets(10, 10, 10, 10));

		// Create progress text
		Text progressText = new Text("Progress: ");

		HBox hBox = new HBox(10);

		// Create a progress bar
		progressBar = new ProgressBar(0);
		progressBar.setPrefWidth(260);
		progressIndicator = new ProgressIndicator(0);
		// Link bar and indicator value
		progressIndicator.progressProperty().bind(progressBar.progressProperty());

		hBox.getChildren().addAll(progressText, progressBar, progressIndicator);

		VBox buttonsBox = new VBox(10);
		buttonsBox.setAlignment(Pos.CENTER_RIGHT);

		increase = new Button("Increase");
		increase.setPrefWidth(75);
		increase.setOnAction(e -> {
			String sValue = (String) String.format("%.2f", progressBar.getProgress() + 0.05);
			Double newValue = Double.parseDouble(sValue);
			if (newValue <= 1) {
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

		mainLayout.getChildren().addAll(hBox, buttonsBox);

		// Create a scene.
		Scene s = new Scene(mainLayout);
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
