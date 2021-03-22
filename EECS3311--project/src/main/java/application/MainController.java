package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.event.ActionEvent;
import EECS3311.project.Officer;
import EECS3311.project.ParkingSpot;
import EECS3311.project.SystemAdmin;
import EECS3311.project.customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import parking.assistant.database.DatabaseHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MainController implements Initializable {
	private Stage stage;
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
	DatabaseHandler databasehandler ;
	
	
	public void init(Stage primaryStage) {
		this.stage = primaryStage;

	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		databasehandler = new DatabaseHandler();
		checkData();
	}
	
	
	private void checkData() {
		String qu = "SELECT spacenum FROM PARKING"; 
		ResultSet rs = databasehandler.execQuery(qu); 
		try{ 
			while(rs.next()){ 
				String numb = rs.getString("spacenum"); 
				System.out.println(numb);
				} 
		}catch(Exception e){
					
				}
		}


	/** %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	 *	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%CUSTOMER UI METHODS%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	 * 
	    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/
	@FXML
	private JFXTextField NUM;
	@FXML
	private JFXTextField LICENSE;
	@FXML
	private JFXTimePicker start;
	@FXML
	private JFXTimePicker end;
	@FXML 
	private Button BOOK;
	
	@FXML
	public void BookSpot() {
		if(NUM.getText().isEmpty() || LICENSE.getText().isEmpty()) { 
			Alert alert = new Alert(Alert.AlertType.ERROR); 
			alert.setHeaderText(null); 
			alert.setContentText("All fields are required"); 
			alert.showAndWait(); return; } 
			//need to make spot unavailable and add it to customer's list
			ParkingSpot p = new ParkingSpot();//TODO: need to somehow add start and end times
			CUSTOMER.bookSpot(p);
	}
	
	
	@FXML
	public void BookSpotUI(ActionEvent event) throws IOException {
		//if(CUSTOMER.getStatus()) {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/bookspot.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
	//	}
	}
	
	public void CreateAccount() {

		/********************/
		if(PASSWORD.getText()==PASSWORD2.getText() && EMAIL.getText().contains("@")) {
			CUSTOMER.CreatAccount(getFIRST(), getLAST(), getEMAIL(), getPASSWORD());
		}
		
	}
	
	public boolean LogIn() {
		System.out.println(EMAIL.getText());
		System.out.println("\n"+PASSWORD.getText());
		
		if(EMAIL.getText()=="MASTER" && PASSWORD.getText()=="A365@MASTERLOGIN!") {
			System.out.println("SYSTEM ADMIN LOGIN SUCCESSFULE");
		}
		
		//else if(getEMAIL().contains("@") && CUSTOMER.LogIn(getEMAIL(), getPASSWORD())) {
			System.out.println("CUSTOMER LOGIN SUCCESSFUL");
			return true;
	//	}
		//return false;
		
	}
		
	@FXML
	public void customerUI(ActionEvent event) throws IOException {
		//if(LogIn()) System.out.println("LOGIN SUCCESSFUL");
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/Customer.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
		
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
	    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/
	@FXML	//THIS IS FOR OFFICERS ONLY!!!
	public void AddSpot() {
		String qu = "INSERT INTO PARKING VALUES ("+
					"'" + NUM.getText() + "'," + 
					"'" + LICENSE.getText() + "'," + 
					"'" + "130" + "'," + 
					"'" + "830" + "'," + 
					"" + "true" + "" + 
				")";
		
		if(databasehandler.execaction(qu)){
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null); 
			alert.setContentText("Parking spot added successfully"); 
			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null); 
			alert.setContentText("Could not add Parking spot"); 
			alert.showAndWait();
		}
	}
	
	@FXML
	public void OfficerLogin(ActionEvent event) {
		
	}
	
	@FXML
	private void cancel() {
		//Stage stage = (Stage) rootPane.getScene().getWindow(); 
		//stage.close();
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
}
