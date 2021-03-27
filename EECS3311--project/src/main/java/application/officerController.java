package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import EECS3311.project.Officer;
import EECS3311.project.ParkingSpot;
import EECS3311.project.SystemAdmin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class officerController implements Initializable {
	SystemAdmin admin = new SystemAdmin();
	Officer officer = new Officer();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	@FXML
	public void AddOfficer(ActionEvent event) {
		admin.AddOfficer();
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null); 
		alert.setContentText("You have successfully added a new officer"); 
		alert.showAndWait();
	}
	
	@FXML
	private TextField address;
	@FXML
	private AnchorPane rootPane;
	
	
	private Boolean availability;
	
	private int requests;
	
	public void AddSpot() throws IOException {
		//ADD NEW SPOT
		Boolean found = false;
		FileWriter fw = new FileWriter("ParkingDatabase.txt", true); 
		BufferedWriter bw = new BufferedWriter(fw); 
		PrintWriter pw = new PrintWriter(bw); 
		pw.println(address+","+availability+","+requests); 
		pw.flush(); 
		pw.close();
		
		String path = "ParkingDatabase.txt"; 
		String line = ""; 
		try {
			BufferedReader br  = new BufferedReader(new FileReader(path));
			while((line = br.readLine())!=null) {
				String [] values = line.split(",");
				if(values[0].equals(address)) {
					found = true;
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null); 
					alert.setContentText("Could not add Parking spot because it already exists"); 
					alert.showAndWait();
				}
			}
		}
		catch (Exception e) {
			System.out.println("Parking spot adding Checking exception");
		}
		if(!found) {
			ParkingSpot spot = officer.AddSpot(address.getText());
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null); 
			alert.setContentText("Parking spot added successfully"); 
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
		AddSpot();
	}
}
