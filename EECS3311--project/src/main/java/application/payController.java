package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import EECS3311.project.CreditCard;
import EECS3311.project.Debit;
import EECS3311.project.PaymentMethod;
import EECS3311.project.Paypal;
import EECS3311.project.customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.SkinBase;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;


public class payController implements Initializable {
	@FXML
	private ToggleGroup tg;
	customer CUSTOMER = new customer();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	@FXML
	private TextField cardholder;
	@FXML
	private TextField cardnumber;
	@FXML
	private DatePicker expDate = new DatePicker();
	@FXML
	private TextField CVV;
	
	
	
	@FXML
	public void doPay(ActionEvent event) {
		boolean bad = false;
		char[] chars = cardholder.getText().toCharArray();
	      for(char c : chars){
	         if(Character.isDigit(c)){
	            bad = true;
	         }
	      }
	    
		customer.setMethod(getMethod());
		if(CUSTOMER.Pay() && !bad) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null); 
			alert.setContentText("Payment was successful"); 
			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null); 
			alert.setContentText("Could not complete payment. Please check your entries"); 
			alert.showAndWait();
		}
	}
	@FXML
	public void AddSpotOfficer(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/addSpot.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
	}
	
	@FXML
	public void savePaymentMethod(ActionEvent event) {
		customer.setMethod(getMethod());
		
	}
	
	public PaymentMethod getMethod() {
		PaymentMethod method ;
		RadioButton selectedRadioButton = (RadioButton) tg.getSelectedToggle();
		String tgValue = selectedRadioButton.getText();
		switch(tgValue) {
		case "Paypal": method = new Paypal(); break;
		case "Debit":  method = new Debit(); break;
		default:   method = new CreditCard();
		}
		System.out.println(tgValue);
		return method;
	}
}
