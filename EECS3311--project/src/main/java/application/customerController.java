package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import EECS3311.project.Officer;
import EECS3311.project.ParkingSpot;
import EECS3311.project.customer;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class customerController implements Initializable{
	customer CUSTOMER = new customer();
	@FXML
	private TableView<ParkingSpot> bookingView;
	
	@FXML
	private TableColumn<ParkingSpot, String> requestcol, paymentCol, availCol;
	
	@FXML
	private ComboBox<String> from, until;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		requestcol.setCellValueFactory(new PropertyValueFactory<>("ID"));
		try {
			fill();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void fill() throws IOException {
		Officer officer = new Officer();
		bookingView.getItems().clear();
		List<ParkingSpot> cs = officer.getSpots();
		for(ParkingSpot ps: cs) {
			if(!bookingView.getItems().contains(ps))
			bookingView.getItems().add(ps);
		}
		
		}
	@FXML
	private JFXTextField NUM;
	@FXML
	private JFXTextField LICENSE;
	
	@FXML 
	private Button BOOK;
	
	@FXML
	public void BookSpot() throws IOException {
		if(NUM.getText().isEmpty() || LICENSE.getText().isEmpty()) { 
			Alert alert = new Alert(Alert.AlertType.ERROR); 
			alert.setHeaderText(null); 
			alert.setContentText("All fields are required"); 
			alert.showAndWait(); return; } 
			//need to make spot unavailable and add it to customer's list
			ParkingSpot p = new ParkingSpot(NUM.getText());//TODO: need to somehow add start and end times
			CUSTOMER.bookSpot(p);
	}
	
	
	@FXML
	public void BookSpotUI(ActionEvent event) throws IOException {
//		if(bookingView.getSelectionModel().getSelectedItem()==null) {
//			Alert alert = new Alert(Alert.AlertType.ERROR);
//			alert.setHeaderText(null); 
//			alert.setContentText("Please select an available spot to book"); 
//			alert.showAndWait();
//		}
//		else {
		Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/bookspot.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Advanced Options");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		//}
	}
	
	
	@FXML
	public void pay(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/pay.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.getIcons().add(new Image("https://cdn.dribbble.com/users/2449441/screenshots/6113182/parkit_app_icon.png"));

		window.show();
	}
	
	@FXML
	public void logout(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/EnterAs.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.getIcons().add(new Image("https://cdn.dribbble.com/users/2449441/screenshots/6113182/parkit_app_icon.png"));

		window.show();
	}
	@FXML
	public void RETURN(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/EnterAs.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.getIcons().add(new Image("https://cdn.dribbble.com/users/2449441/screenshots/6113182/parkit_app_icon.png"));

		window.show();
	}
	@FXML
	public void cancelBooking(ActionEvent event) throws Exception {
		if(CUSTOMER.CancelBookings(bookingView.getSelectionModel().getSelectedItem().getID())) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null); 
			alert.setContentText("Cancelling was successful"); 
			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null); 
			alert.setContentText("Could not cancel booking"); 
			alert.showAndWait();
		}
	}
	
	
	@FXML
	public void customerUI(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/Customer.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.getIcons().add(new Image("https://cdn.dribbble.com/users/2449441/screenshots/6113182/parkit_app_icon.png"));

		window.show();
		
	
	}
	
	@FXML
	public void viewBooking(ActionEvent event) {
		Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/bookingView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Advanced Options");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
}
