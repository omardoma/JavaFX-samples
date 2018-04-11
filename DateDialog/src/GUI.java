import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUI extends Application {
	private Text startText;
	private Text endText;
	private Text totalText;
	private DatePicker startDate;
	private DatePicker endDate;
	private TextField costPerDay;
	private Button cancel;
	private Button ok;
	private Button newBooking;

	private void openDialog() {
		// Create a new stage for the dialog
		Stage dialogStage = new Stage();
		// set the width ,height and other parameters.
		dialogStage.setWidth(450);
		dialogStage.setHeight(300);
		dialogStage.setTitle("Select Dates");
		// Make sure the main window is not clickable until the dialog is closed
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		// The dialog is not resizable
		dialogStage.setResizable(false);
		// the dialog should have focus.
		dialogStage.setAlwaysOnTop(true);
		// Create a VBox layout
		VBox mainLayout = new VBox(10);
		mainLayout.setPadding(new Insets(15));

		Text start = new Text("Start Date:");
		startDate = new DatePicker();

		Text end = new Text("End Date:");
		endDate = new DatePicker();

		Text cost = new Text("Cost/Day:");
		costPerDay = new TextField();
		costPerDay.setPrefWidth(200);

		// Create a horizontal box layout (HBox)
		HBox hBox = new HBox(10);
		// Add padding to center it
		hBox.setPadding(new Insets(10));
		// Set right alignment
		hBox.setAlignment(Pos.CENTER_RIGHT);
		// Create ok Button
		ok = new Button("Ok");
		ok.setPrefWidth(60);
		// Add action on clicking ok Button
		ok.setOnAction(e -> {
			if ((startDate.getValue() != null && endDate.getValue() != null
					&& (endDate.getValue().isAfter(startDate.getValue())
							|| endDate.getValue().isEqual(startDate.getValue())))
					&& !costPerDay.getText().equals("") && Integer.parseInt(costPerDay.getText()) >= 0) {
				startText.setText(startDate.getValue().toString());
				endText.setText(endDate.getValue().toString());
				totalText.setText(costPerDay.getText());
				dialogStage.close();
			}
		});
		// Create cancel Button
		cancel = new Button("Cancel");
		// Add action on clicking cancel Button
		cancel.setOnAction(e -> {
			// Close the dialog
			dialogStage.close();
		});
		// Add buttons to HBox
		hBox.getChildren().addAll(cancel, ok);
		hBox.setPadding(new Insets(15, 50, 0, 0));

		// Put the text field in an hbox to not fill the whole width
		HBox tmp = new HBox();
		tmp.getChildren().add(costPerDay);

		mainLayout.getChildren().addAll(start, startDate, end, endDate, cost, tmp, hBox);
		// create a scene .
		Scene s = new Scene(mainLayout);
		s.getStylesheets().addAll(getClass().getResource("dialogstyle.css").toExternalForm());
		// Set the scene.
		dialogStage.setScene(s);
		// Show the stage
		dialogStage.show();
	}

	private void preparePrimaryStage(Stage primaryStage) {
		// Set the title
		primaryStage.setTitle("DateDialog");
		// Set the width.
		primaryStage.setWidth(500);
		// set the height
		primaryStage.setHeight(350);

		VBox mainLayout = new VBox(40);
		mainLayout.setPadding(new Insets(30, 30, 30, 30));

		HBox row1 = new HBox(30);
		Text start = new Text("Start Date: ");
		startText = new Text();
		row1.getChildren().addAll(start, startText);

		HBox row2 = new HBox(35);
		Text end = new Text("End Date: ");
		endText = new Text();
		row2.getChildren().addAll(end, endText);

		HBox row3 = new HBox(30);
		Text total = new Text("Total Cost: ");
		totalText = new Text();
		row3.getChildren().addAll(total, totalText);

		HBox buttonsRow = new HBox(5);
		// Set the HBox to have right alignment for the buttons to be on the right
		buttonsRow.setAlignment(Pos.CENTER);
		buttonsRow.setPadding(new Insets(0, 0, 0, 55));
		newBooking = new Button("New Booking");
		newBooking.setOnAction(e -> {
			openDialog();
		});
		// Add buttons to HBox
		buttonsRow.getChildren().add(newBooking);

		// Add rows to main layout
		mainLayout.getChildren().addAll(row1, row2, row3, buttonsRow);

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
