package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javafx.scene.control.Toggle;
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
	private Toggle paypal, debit, creditcard;

	@FXML
	private ComboBox<String> bookings;

	@FXML
	private Label amount;
	customer CUSTOMER = new customer();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		List<ParkingSpot> spots = CUSTOMER.ViewBookings();
		for(ParkingSpot s: spots) {
			String spot =s.getID()+"  Expires: "+ s.EndHour+":"+s.EndMinute;
			//bookings.getItems().add(s.getID());
			bookings.getItems().add(spot);
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
			List<ParkingSpot> list = CUSTOMER.ViewBookings();
			for(ParkingSpot s: list) {
				String time2= s.EndHour+":"+s.EndMinute;
				if(bookings.getSelectionModel().getSelectedItem().contains(s.getID()) && bookings.getSelectionModel().getSelectedItem().contains(time2)) {
					
					if(!bad && CUSTOMER.Pay(s))  {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText(null); 
						alert.setContentText("Payment was successful"); 
						alert.showAndWait();
						System.out.println(bookings.getSelectionModel().getSelectedItem());
						return;
					}
					else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setHeaderText(null); 
						alert.setContentText("Could not complete payment. Please check your entries"); 
						alert.showAndWait();
						return;
					}
				}
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
			if(savePaymentInformation()) {	
				customer.setMethod(getMethod());
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText(null); 
				alert.setContentText("Payment saved!"); 
				alert.showAndWait();
//				Parent Scene2root = FXMLLoader.load(getClass().getResource("/Customer.fxml"));
//
//				Scene AddInfoScene = new Scene(Scene2root);
//
//				//this gets scene information
//				Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
//				window.setScene(AddInfoScene);
//				window.show();
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null); 
				alert.setContentText("Could not save payment because some fields were missing or incorrect"); 
				alert.showAndWait();
			}
		}



	}

	private boolean savePaymentInformation() throws IOException {
		//check if any field is empty
		System.out.println(cardholder.getText()+" / "+ cardnumber.getText() + " / "+ tg.getSelectedToggle().getToggleGroup().toString() + " / "+  expDate.getValue());
		if(cardholder.getText()==null || cardnumber.getText()==null || tg.getSelectedToggle()==null || expDate.getValue()==null || CVV.getText()==null) {
			System.out.println("Returning on first IF");
			return false;
		}
		if(tg.getSelectedToggle()!=null) {
			char[] chars = cardholder.getText().toCharArray();
			for(char c : chars){
				if(Character.isDigit(c)){
					System.out.println("Returning on isDigit(c) == true");
					return false;
				}
			}
			chars = cardnumber.getText().toCharArray();
			for(char c : chars){
				if(!Character.isDigit(c)){
					System.out.println("Returning on isDigit(c) == false");
					return false;
				}
			}
			chars = CVV.getText().toCharArray();
			for(char c : chars){
				if(!Character.isDigit(c)){
					System.out.println("Returning on CVV isDigit(c) == false");
					return false;
				}
			}
		}

		FileWriter fw = new FileWriter("PaymentInfo.txt", false); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		 RadioButton chk = (RadioButton)tg.getSelectedToggle(); 
		System.out.println(chk.getText());
		
		pw.println(cardholder.getText()+","+cardnumber.getText()+","+chk.getText()+","+expDate.getValue().toString()+","+CVV.getText()); 
		pw.flush(); 
		pw.close();
		bw.close();
		fw.close();

		return true;
	}
	@FXML
	public void loadSavedPayment(ActionEvent event) throws ParseException {
		String text;
		String [] parts;
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("PaymentInfo.txt"));
			while((text = br.readLine()) != null) {
				parts = text.split(",");
				// now `parts` array will contain your data
				//LOAD ATTRIBUTES
				if(parts.length<1) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setHeaderText(null); 
					alert.setContentText("No method saved"); 
					alert.showAndWait();
					return;}
				else {
					cardholder.setText(parts[0]);
					cardnumber.setText(parts[1]);
					System.out.println("PARTS[2]"+parts[2]);
					switch(parts[2]){
						case "Paypal": tg.selectToggle(paypal); break;
						case "Debit": tg.selectToggle(debit); break;
						default: tg.selectToggle(creditcard);
					}
					Date date =new SimpleDateFormat("yyyy-MM-dd").parse(parts[3]);  
					expDate.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

					CVV.setText(parts[4]);

				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			String temp = bookings.getSelectionModel().getSelectedItem();
			spot = s;
			time1= spot.StartHour+":"+spot.StartMin;
			time2= spot.EndHour+":"+spot.EndMinute;
			if(temp.contains(s.getID()) && temp.contains(time2)){
				System.out.println("calculating spot -> "+s.getID());

				SimpleDateFormat format = new SimpleDateFormat("HH:mm");
				Date date1 = format.parse(time1);
				Date date2 = format.parse(time2);
				System.out.println("time1 "+date1.getTime()+"    time2 "+date2.getTime());
				long difference = date2.getTime() - date1.getTime();
				double toPay = ((difference *1.0/ (1000*60*60)) % 24);
				System.out.println("time difference  = "+toPay);
				toPay*=spot.rate.getRate();
				amount.setText("$"+Math.abs(toPay));
				return;
			}
		}


	}
}
