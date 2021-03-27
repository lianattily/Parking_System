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

import EECS3311.project.Officer;
import EECS3311.project.ParkingSpot;
import EECS3311.project.PaymentStatus;
import EECS3311.project.SystemAdmin;
import EECS3311.project.customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class parkingManager implements Initializable{
	Officer officer = new Officer();
	customer CUSTOMER = new customer();
	@FXML
	private TableView<ParkingSpot> tableView;
	@FXML
	private TableColumn<ParkingSpot, Integer> requestcol;
	@FXML
	private TableColumn<ParkingSpot, PaymentStatus> paymentCol;
	@FXML
	private TableColumn<ParkingSpot, Boolean> availCol;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		requestcol.setCellValueFactory(new PropertyValueFactory<>("ID"));
		//paymentCol.setCellValueFactory(new PropertyValueFactory<>("isPaid"));
		//availCol.setCellValueFactory(new PropertyValueFactory<>("isFilled"));
		tableView.setItems(observableList);
	}
	ObservableList<ParkingSpot> observableList = FXCollections.observableArrayList(new ParkingSpot("L9A5G2"));
	
	@FXML
	private JFXTextField NUM;
	@FXML
	private JFXTextField LICENSE;
	
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
			ParkingSpot p = new ParkingSpot(NUM.getText());//TODO: need to somehow add start and end times
			CUSTOMER.bookSpot(p);
	}
	
	
	@FXML
	public void BookSpotUI(ActionEvent event) throws IOException {
		//if(CUSTOMER.getStatus()) {
		//PopupWindow window = PopupWindow.create(stage, "javafx", Modality.NONE); window.setContent(new Test2());
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/bookspot.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
	//	}
	}
	
	
	@FXML
	public void pay(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/pay.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();
	}
	
	@FXML
	public void logout(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/EnterAs.fxml"));
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
	
}
