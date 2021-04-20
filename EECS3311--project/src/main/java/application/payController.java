package application;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import EECS3311.project.CreditCard;
import EECS3311.project.Debit;
import EECS3311.project.ParkingSpot;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.control.SkinBase;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;


public class payController implements Initializable {

	@FXML
	private ToggleGroup tg;
	
	@FXML
	private ComboBox<String> bookings;
	
	@FXML
	private Label amount;
	customer CUSTOMER = new customer();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		List<ParkingSpot> spots = CUSTOMER.ViewBookings();
		for(ParkingSpot s: spots) {
			bookings.getItems().add(s.getID());
		}
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
	public void doPay(ActionEvent event) throws Exception {
		if(tg.getSelectedToggle()!=null) {
		boolean bad = false;
		char[] chars = cardholder.getText().toCharArray();
	      for(char c : chars){
	         if(Character.isDigit(c)){
	            bad = true;
	         }
	      }
	    System.out.println("PAYING FOR -> "+bookings.getSelectionModel().getSelectedItem());
		customer.setMethod(getMethod());
		if(!bad && CUSTOMER.Pay(bookings.getSelectionModel().getSelectedItem()))  {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null); 
			alert.setContentText("Payment was successful"); 
			alert.showAndWait();
			System.out.println(bookings.getSelectionModel().getSelectedItem());
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null); 
			alert.setContentText("Could not complete payment. Please check your entries"); 
			alert.showAndWait();
		}
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null); 
			alert.setContentText("Could not save payment because no method was selected"); 
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
		window.getIcons().add(new Image("https://cdn.dribbble.com/users/2449441/screenshots/6113182/parkit_app_icon.png"));

		window.show();
	}
	
	@FXML
	public void savePaymentMethod(ActionEvent event) throws IOException {
		if(tg.getSelectedToggle()==null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null); 
			alert.setContentText("Could not save payment because no method was selected"); 
			alert.showAndWait();
		}else {
		customer.setMethod(getMethod());
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/Customer.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		
		window.show();
		}
		
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
	
	
	@FXML
	public void setAmount(ActionEvent event) throws ParseException {
		ParkingSpot spot;
		String time1;
		String time2;
		List<ParkingSpot> list = CUSTOMER.ViewBookings();
		
		for(ParkingSpot s: list) {
			if(s.getID().equals(bookings.getSelectionModel().getSelectedItem())){
				System.out.println("calculating spot -> "+s.getID());
				spot = s;
				time1= spot.StartHour+":"+spot.StartMin;
				time2= spot.EndHour+":"+spot.EndMinute;
				SimpleDateFormat format = new SimpleDateFormat("HH:mm");
				Date date1 = format.parse(time1);
				Date date2 = format.parse(time2);
				long difference = date2.getTime() - date1.getTime();
				System.out.println("time difference  = "+difference);
				difference*=spot.rate.getRate();
				amount.setText("$"+difference);
				return;
			}
		}

		
	}
}
