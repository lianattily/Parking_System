package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import com.jfoenix.controls.JFXTextField;

import EECS3311.project.Officer;
import EECS3311.project.ParkingSpot;
import EECS3311.project.customer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class bookingController implements Initializable{
	@FXML
	private ComboBox<String> options;
	@FXML
	private JFXTextField Fhour, Fmin, Thour, Tmin;
	@FXML
	private DatePicker date;
	String[] availableSpots;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Officer o = new Officer();
		List<ParkingSpot> list = o.getSpots();
	
		for(ParkingSpot s: list) {
			options.getItems().add(s.ID);
				
		}
	}

	private int getRate() {
		Officer o = new Officer();
		List<ParkingSpot> list = o.getSpots();
		for(ParkingSpot s: list) {
			if(s.getID().equals(options.getSelectionModel().getSelectedItem()))
				return s.rate.getRate();
		}
		return 0;
	}
	
	@FXML
	private JFXTextField NUM;
	@FXML
	private JFXTextField LICENSE;

	@FXML 
	private Button BOOK;
	customer CUSTOMER = new customer();
	@FXML
	public void BookSpot() throws IOException {
		if(options.getSelectionModel().getSelectedItem()==null || LICENSE.getText().isEmpty() || date.getValue()==null) { 
			Alert alert = new Alert(Alert.AlertType.ERROR); 
			alert.setHeaderText(null); 
			alert.setContentText("All fields are required"); 
			alert.showAndWait(); return; 
		}
		System.out.println("Booking date = "+date.getValue());
		System.out.println(Integer.parseInt(Fhour.getText())+" " +Integer.parseInt(Fmin.getText())+"   " +Integer.parseInt(Thour.getText())+"   " +Integer.parseInt(Tmin.getText()));
		ParkingSpot p = new ParkingSpot(options.getSelectionModel().getSelectedItem(), LICENSE.getText(),"UNPAID",Integer.parseInt(Fhour.getText()), Integer.parseInt(Fmin.getText()),Integer.parseInt(Thour.getText()), Integer.parseInt(Tmin.getText()),date.getValue(), getRate());
		String uniqueID = UUID.randomUUID().toString().substring(0, 5);
		
		if(CUSTOMER.bookSpot(uniqueID,options.getSelectionModel().getSelectedItem(),p)) {
			//TODO: change to "REQUESTED" 
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION); 
			alert.setHeaderText("BOOKING ID "+uniqueID); 
			alert.setContentText(options.getSelectionModel().getSelectedItem()+" Booked successfully until: "+Thour.getText()+":"+Tmin.getText()); 
			alert.showAndWait();
			Stage stage = (Stage) close.getScene().getWindow();
			stage.close();
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR); 
			alert.setHeaderText(null); 
			alert.setContentText("Could not book spot (either you have reached the maximum booking capacity or this spot does not exist"); 
			alert.showAndWait(); return;
		}
	}


	@FXML
	public void BookSpotUI(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/bookspot.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
	}

	@FXML Button close;
	@FXML
	public void customerUI(ActionEvent event) throws IOException {
		Stage stage = (Stage) close.getScene().getWindow();
		stage.close();

	}
}
