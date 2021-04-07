package application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import EECS3311.project.CreditCard;
import EECS3311.project.Debit;
import EECS3311.project.Officer;
import EECS3311.project.ParkingSpot;
import EECS3311.project.PaymentMethod;
import EECS3311.project.PaymentStatus;
import EECS3311.project.Paypal;
import EECS3311.project.SystemAdmin;
import EECS3311.project.customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable {
	private Stage stage;
	@FXML
	private JFXTimePicker start = new JFXTimePicker();
	@FXML
	private JFXTimePicker end = new JFXTimePicker();
	@FXML
	private TextField FIRSTNAME;
	@FXML
	private TextField LASTNAME;
	@FXML
	private TextField EMAIL;
	@FXML
	private TextField LogINEMAIL;
	@FXML 
	private PasswordField PASSWORD;
	@FXML 
	private PasswordField PASSWORD2;
	@FXML 
	private PasswordField logINPASSWORD;


	customer CUSTOMER = new customer();
	SystemAdmin admin = new SystemAdmin();
	Officer officer = new Officer();
	
	
	public void init(Stage primaryStage) {
		this.stage = primaryStage;

	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	private boolean checkData(String email, String password) {//add parameter String path
		String path = "CustomerDatabase.txt"; 
		String line = ""; 
		System.out.println("checking for   "+email + "    "+password);
		//ORDER: Fname+","+Lname+","+email+","+Password
		try {
			BufferedReader br  = new BufferedReader(new FileReader(path));
			while((line = br.readLine())!=null) {
				String [] values = line.split(",");
				System.out.println("72 checking printing "+values[2]+"    "+values[3]);
				if(values[2].equals(email) && values[3].equals(password)) {
					return true;
				}
			}
		}
		catch (Exception e) {
			System.out.println("Checking exception");
		}
		return false;
		}


	/** %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	 *	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%CUSTOMER UI METHODS%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	 * 
	    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/
	
	@FXML
	public void BACKBUTTON(ActionEvent event) throws IOException {
		
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
	}
	
	@FXML
	public void CreateAccount(ActionEvent event) {

		if(PASSWORD.getText().equals(PASSWORD2.getText())==false) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null); 
			alert.setContentText("Passwords do not match"); 
			alert.showAndWait();
		}
		if(EMAIL.getText().contains("@")==false) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null); 
			alert.setContentText("Please enter a valid email address"); 
			alert.showAndWait();
		}
		if(PASSWORD.getText().equals(PASSWORD2.getText()) && EMAIL.getText().contains("@")) {
			CUSTOMER.CreatAccount(getFIRST(), getLAST(), getEMAIL(), getPASSWORD());
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null); 
			alert.setContentText("You have successfully created an account. Please use your email and password to login"); 
			alert.showAndWait();
		}
		

	}
	
	public boolean LogIn() {
		System.out.println(LogINEMAIL.getText());
		System.out.println("\n"+logINPASSWORD.getText());
		
		if(LogINEMAIL.getText().contains("@") && checkData(LogINEMAIL.getText(), logINPASSWORD.getText())) {
			System.out.println("CUSTOMER LOGIN SUCCESSFUL");
			return true;
		}
		return false;
		
	}
	
	Boolean log = false;
	@FXML
	public void customerUI(ActionEvent event) throws IOException {
		if(LogINEMAIL!=null)if(LogIn()) log = true;
		if(log) {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/Customer.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);
		
		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		//window.getIcons().add(new Image("https://cdn.dribbble.com/users/2449441/screenshots/6113182/parkit_app_icon.png"));

		window.setScene(AddInfoScene);
		window.show();
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null); 
			alert.setContentText("Could not login"); 
			alert.showAndWait();
		}
	
	}
	@FXML
	private Button createAccount;
	
	@FXML
	public void createAccountBtn(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/createAccount.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
	}

	@FXML
	public void userLogin(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
	}
	
	public String getFIRST() {
		return FIRSTNAME.getText();
	}

	public String getLAST() {
		return LASTNAME.getText();
	}
	public String getEMAIL() {
		return EMAIL.getText();
	}
	public String getPASSWORD() {
		return PASSWORD.getText();
	}
	
	
	
	/** %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	 *	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%OFFICER UI METHODS%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	 * 
	    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	 * @throws IOException */
	@FXML
	private TextField offID;
	@FXML 
	private PasswordField offPassword;
	
	@FXML
	public void OfficerLogin(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/officerLogin.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
	}
	
	@FXML
	private void officerUI(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/officer.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
	}
	
	/** %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	 *	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%ADMIN UI METHODS%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	 * 
	    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	 * @throws IOException */
	
	@FXML
	public void AdminLogin(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/adminLogin.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
	}
	
//	@FXML
//	public void AddOfficer(ActionEvent event) throws IOException {
//		admin.AddOfficer();
//		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//		alert.setHeaderText(null); 
//		alert.setContentText("You have successfully added a new officer"); 
//		alert.showAndWait();
//	}
//	
	@FXML
	public void adminUI(ActionEvent event) throws IOException {
		if(adminLOGIN()) {
		System.out.println("SYSTEM ADMIN LOGIN SUCCESSFUL");
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/admin.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Failed to Login as System Administrator"); 
			alert.setContentText("Master ID or password are incorrect."); 
			alert.showAndWait();
		}
	}
	
	public boolean adminLOGIN() {
		if(logINPASSWORD.getText().equals(admin.getPASS()) && LogINEMAIL.getText().equals(admin.getID()))
			return true;
		else 
		return false;
	}
	
	@FXML
	public void RETURN(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/EnterAs.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
	}
}
