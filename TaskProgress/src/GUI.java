
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {
	private Button startTask;
	private Button cancelTask;
	private ProgressBar progressBar;
	private Task<Integer> task;

	private Task<Integer> createWorker() {
		return new Task<Integer>() {
			protected Integer call() {
				int i;
				for (i = 0; i < 20000000; i++) {
					if (isCancelled()) {
						break;
					}
					updateProgress(i + 1, 20000000);
				}
				return i;
			}
		};
	}

	private void preparePrimaryStage(Stage primaryStage) {
		// Set the title.
		primaryStage.setTitle("Task Progress");
		// Set the width.
		primaryStage.setWidth(400);
		// set the height
		primaryStage.setHeight(220);

		VBox mainLayout = new VBox(15);
		mainLayout.setPadding(new Insets(20, 20, 20, 20));

		// Create progress text
		Text progressText = new Text("Progress: ");

		// Create a progress bar
		progressBar = new ProgressBar(0);
		progressBar.setPrefWidth(400);

		startTask = new Button("Start Task");
		startTask.setPrefWidth(100);
		startTask.setOnAction(e -> {
			startTask.setDisable(true);
			cancelTask.setDisable(false);
			progressBar.setProgress(0);
			task = createWorker();
			progressBar.progressProperty().bind(task.progressProperty());
			new Thread(task).start();
		});

		cancelTask = new Button("Cancel Task");
		cancelTask.setPrefWidth(100);
		cancelTask.setOnAction(e -> {
			startTask.setDisable(false);
			cancelTask.setDisable(true);
			task.cancel();
			progressBar.progressProperty().unbind();
			progressBar.setProgress(0);
		});

		mainLayout.getChildren().addAll(progressText, progressBar, startTask, cancelTask);

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
