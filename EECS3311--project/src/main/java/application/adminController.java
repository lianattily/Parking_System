package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import EECS3311.project.Officer;
import EECS3311.project.ParkingSpot;
import EECS3311.project.SystemAdmin;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class adminController  implements Initializable{
	Officer officer = new Officer();
	SystemAdmin admin = new SystemAdmin();
	@FXML private JFXTextField ID, password;
	@FXML
	private TableView<ParkingSpot> tableView;
	@FXML
	private TableColumn<ParkingSpot, String> requestcol;
	@FXML
	private TableColumn<ParkingSpot, String> paymentCol;
	@FXML
	private TableColumn<ParkingSpot, String> availCol;
	
	@FXML 
	private TableView<Officer> officersView;
	@FXML
	private TableColumn<Officer, String> officerID;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		officerID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		officersView.setItems(officerList);
		requestcol.setCellValueFactory(new PropertyValueFactory<>("ID"));
		paymentCol.setCellValueFactory(new PropertyValueFactory<>("stat"));
		availCol.setCellValueFactory(new PropertyValueFactory<>("avail"));
		tableView.setItems(observableList);
		try {
			fill();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void fill() throws IOException {
		tableView.getItems().clear();
		List<ParkingSpot> s = officer.getSpots();
		for(ParkingSpot ps: s) {
			if(!tableView.getItems().contains(ps))
			tableView.getItems().add(ps);
		}
		
		//FILL OFFICERS TABLE VIEW
		officersView.getItems().clear();
		List<Officer> o = admin.getList();
		for(Officer of: o) {
			if(!officersView.getItems().contains(of))
				officersView.getItems().add(of);
		}
		
	}
	
	
	ObservableList<ParkingSpot> observableList = FXCollections.observableArrayList(new ParkingSpot("L9A5G2"));
	ObservableList<Officer> officerList= FXCollections.observableArrayList(new Officer("L9A5G2","hi"));
	@FXML
	public void UpdatePayment(ActionEvent event) {
		
	}
	
	@FXML
	public void AddOfficer(ActionEvent event) throws IOException {
		if(admin.AddOfficer(ID.getText(),password.getText())) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null); 
			alert.setContentText("Officer added successfully"); 
			alert.showAndWait();
			fill();
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Could not add officer"); 
			alert.setContentText("Officer ID already exists in the system"); 
			alert.showAndWait();
		}
	}
	
	@FXML
	public void RemoveOfficer(ActionEvent event) throws IOException {

		if(officersView.getSelectionModel().getSelectedItem()!=null) {
			System.out.println("HERE");
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null); 
			alert.setContentText("Officer removed successfully"); 
			alert.showAndWait();
			admin.RemoveOfficer(officersView.getSelectionModel().getSelectedItem().getID());
			fill();
		}
		else {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setHeaderText("No officer selected"); 
			alert.setContentText("Please select an officer from the list"); 
			alert.showAndWait();
		}
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
