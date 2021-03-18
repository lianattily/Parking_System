package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import EECS3311.project.Officer;
import EECS3311.project.SystemAdmin;
import EECS3311.project.customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	
	public void init(Stage primaryStage) {
		this.stage = primaryStage;

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
		
		else if(getEMAIL().contains("@") && CUSTOMER.LogIn(getEMAIL(), getPASSWORD())) {
			System.out.println("CUSTOMER LOGIN SUCCESSFUL");
			return true;
		}
		return false;
		
	}
		
	@FXML
	public void customerUI(ActionEvent event) throws IOException {
		if(LogIn()) System.out.println("LOGIN SUCCESSFUL");
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/Customer.fxml"));
		Scene AddInfoScene = new Scene(Scene2root, 700, 500);

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
		Scene AddInfoScene = new Scene(Scene2root, 700, 400);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
	}

	@FXML
	public void BACKBUTTON(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
		Scene AddInfoScene = new Scene(Scene2root, 700, 400);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
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
}
