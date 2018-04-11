
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUI extends Application {
	private Text homeValueText;
	private Text insuranceOptionsText;
	private Text totalText;
	private TextField homeValue;
	private CheckBox homeEmergencyCover;
	private CheckBox legalExpensesCover;
	private CheckBox personalInjuryCover;
	private Button cancel;
	private Button ok;
	private Button insuranceQuote;

	private void openDialog() {
		// Create a new stage for the dialog
		Stage dialogStage = new Stage();
		// set the width ,height and other parameters.
		dialogStage.setWidth(450);
		dialogStage.setHeight(300);
		dialogStage.setTitle("Enter Details");
		// Make sure the main window is not clickable until the dialog is closed
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		// The dialog is not resizable
		dialogStage.setResizable(false);
		// the dialog should have focus.
		dialogStage.setAlwaysOnTop(true);
		// Create a VBox layout
		VBox mainLayout = new VBox(10);
		mainLayout.setPadding(new Insets(15));

		Text homeValueText = new Text("Home Value:");
		homeValue = new TextField();

		Text options = new Text("Options:");

		VBox optionsRow = new VBox(10);
		homeEmergencyCover = new CheckBox("Home Emergency Cover");
		legalExpensesCover = new CheckBox("Legal Expenses Cover");
		personalInjuryCover = new CheckBox("Personal Injury Cover");
		optionsRow.getChildren().addAll(homeEmergencyCover, legalExpensesCover, personalInjuryCover);

		// Create a horizontal box layout (HBox)
		HBox buttonsRow = new HBox(10);
		// Add padding to center it
		buttonsRow.setPadding(new Insets(10));
		// Set right alignment
		buttonsRow.setAlignment(Pos.CENTER_RIGHT);
		// Create ok Button
		ok = new Button("Ok");
		ok.setPrefWidth(60);
		// Add action on clicking ok Button
		ok.setOnAction(e -> {
			if (!homeValue.getText().trim().equals("")) {
				this.homeValueText.setText(homeValue.getText());
				double cost = 0.002 * Double.parseDouble(homeValue.getText());
				String selectedOptions = "";
				if (homeEmergencyCover.isSelected()) {
					selectedOptions += "-Home emergency cover\n";
					cost += 0.15 * 0.002 * Double.parseDouble(homeValue.getText());
				}
				if (legalExpensesCover.isSelected()) {
					selectedOptions += "-Legal expenses cover\n";
					cost += 0.10 * 0.002 * Double.parseDouble(homeValue.getText());
				}
				if (personalInjuryCover.isSelected()) {
					selectedOptions += "-Personal injury cover\n";
					cost += 0.10 * 0.002 * Double.parseDouble(homeValue.getText());
				}
				insuranceOptionsText.setText(selectedOptions);
				totalText.setText(cost + "");
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
		buttonsRow.getChildren().addAll(cancel, ok);
		buttonsRow.setPadding(new Insets(15, 50, 0, 0));

		// Put the text field in an hbox to not fill the whole width
		HBox tmp = new HBox();
		tmp.getChildren().add(homeValue);

		mainLayout.getChildren().addAll(homeValueText, tmp, options, optionsRow, buttonsRow);
		// create a scene .
		Scene s = new Scene(mainLayout);
		// Set the scene.
		dialogStage.setScene(s);
		// Show the stage
		dialogStage.show();
	}

	private void preparePrimaryStage(Stage primaryStage) {
		// Set the title
		primaryStage.setTitle("FXInsurance");
		// Set the width.
		primaryStage.setWidth(500);
		// set the height
		primaryStage.setHeight(300);

		VBox mainLayout = new VBox(20);
		mainLayout.setPadding(new Insets(30, 30, 30, 30));
		
		Text header = new Text("Insurance details: ");
		header.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

		HBox row1 = new HBox(120);
		Text value = new Text("Home Value: ");
		homeValueText = new Text();
		row1.getChildren().addAll(value, homeValueText);

		HBox row2 = new HBox(35);
		Text options = new Text("Insurance options selected: ");
		insuranceOptionsText = new Text();
		row2.getChildren().addAll(options, insuranceOptionsText);

		HBox row3 = new HBox(130);
		Text total = new Text("Total Cost: ");
		totalText = new Text();
		row3.getChildren().addAll(total, totalText);

		HBox buttonsRow = new HBox(5);
		buttonsRow.setAlignment(Pos.CENTER_RIGHT);
		insuranceQuote = new Button("Insurance Quote");
		insuranceQuote.setOnAction(e -> {
			openDialog();
		});
		// Add buttons to HBox
		buttonsRow.getChildren().add(insuranceQuote);

		// Add rows to main layout
		mainLayout.getChildren().addAll(header, row1, row2, row3, buttonsRow);

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
