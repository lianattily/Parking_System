package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class parkingManager implements Initializable{
	Officer officer = new Officer();
	customer CUSTOMER = new customer();
	SystemAdmin admin = new SystemAdmin();
	@FXML
	private TableView<ParkingSpot> tableView;
	@FXML
	private TableColumn<ParkingSpot, String> requestcol;
	@FXML
	private TableColumn<ParkingSpot, String> paymentCol;
	@FXML
	private TableColumn<ParkingSpot, String> availCol;
	@FXML
	private TableColumn<ParkingSpot, String> requests;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		requestcol.setCellValueFactory(new PropertyValueFactory<>("ID"));
		paymentCol.setCellValueFactory(new PropertyValueFactory<>("stat"));
		availCol.setCellValueFactory(new PropertyValueFactory<>("avail"));
		requests.setCellValueFactory(new PropertyValueFactory<>("request"));
		//tableView.setItems(observableList);
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
		System.out.println("SIZE of officer spots list in parkingManager = "+s.size());
		for(ParkingSpot ps: s) {
			System.out.println("SEARCHING FOR -> "+ps.getID());
			if(!tableView.getItems().contains(ps)) {
				tableView.getItems().add(ps);
			}
		}
	}



@FXML
private JFXTextField NUM;
@FXML
private JFXTextField LICENSE;


@FXML
public void logout(ActionEvent event) throws IOException {
	Parent Scene2root = FXMLLoader.load(getClass().getResource("/EnterAs.fxml"));
	Scene AddInfoScene = new Scene(Scene2root);

	//this gets scene information
	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	//window.getIcons().add(new Image("https://www.freeiconspng.com/uploads/directions--cophall-parking-gatwick-22.png"));

	window.setScene(AddInfoScene);
	window.show();
}

@FXML
private void officerUI(ActionEvent event) throws IOException {
	Parent Scene2root = FXMLLoader.load(getClass().getResource("/officer.fxml"));
	Scene AddInfoScene = new Scene(Scene2root);

	//this gets scene information
	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	//window.getIcons().add(new Image("https://www.freeiconspng.com/uploads/directions--cophall-parking-gatwick-22.png"));

	window.setScene(AddInfoScene);
	window.show();
}
@FXML
private TextField address,rate;
@FXML
private AnchorPane rootPane;


private Boolean availability;


@FXML
public void AddSpot(ActionEvent event) throws IOException {
	//ADD NEW SPOT
	if(address.getText().isEmpty() || rate.getText().isEmpty()) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(null); 
		alert.setContentText("All fields are required"); 
		alert.showAndWait();
		return;
	}
	Boolean found = false;

	String path = "ParkingDatabase.txt"; 
	String line = ""; 
	try {
		BufferedReader br  = new BufferedReader(new FileReader(path));
		while((line = br.readLine())!=null) {
			String [] values = line.split(",");
			System.out.println(values[0]);
			if(values[0].equals(address.getText())) {
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
		Integer r = Integer.parseInt(rate.getText());
		System.out.println("Rate = "+r);
		ParkingSpot spot = officer.AddSpot(address.getText(), r);
		fill();
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null); 
		alert.setContentText("Parking spot added successfully"); 
		alert.showAndWait();
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/officer.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(AddInfoScene);
		window.show();

	}

}

@FXML
public void AddSpotOfficer(ActionEvent event) throws IOException {
	Parent Scene2root = FXMLLoader.load(getClass().getResource("/addSpot.fxml"));
	Scene AddInfoScene = new Scene(Scene2root);

	//this gets scene information
	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	//window.getIcons().add(new Image("https://www.freeiconspng.com/uploads/directions--cophall-parking-gatwick-22.png"));

	window.setScene(AddInfoScene);
	window.show();

}
@FXML
public void RETURN(ActionEvent event) throws IOException {
	Parent Scene2root = FXMLLoader.load(getClass().getResource("/EnterAs.fxml"));
	Scene AddInfoScene = new Scene(Scene2root);

	//this gets scene information
	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	//window.getIcons().add(new Image("https://cdn.dribbble.com/users/2449441/screenshots/6113182/parkit_app_icon.png"));

	window.setScene(AddInfoScene);
	window.show();
}
public void removeLine(String Line) throws IOException{
	BufferedReader br = new BufferedReader(new FileReader("ParkingDatabase.txt"));
	File temp = new File("temp.txt");
	BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
	String removeID = Line;
	String currentLine;
	while((currentLine = br.readLine()) != null){
		String trimmedLine = currentLine.trim();
		if(trimmedLine.equals(removeID)){
			currentLine = "";
		}
		bw.write(currentLine + System.getProperty("line.separator"));

	}
	bw.close();
	br.close();
}

@FXML
public void RemoveSpot(ActionEvent event) throws Exception {
	System.out.println("BEFORE");
	List<ParkingSpot> t = officer.getSpots();
	for(ParkingSpot e: t) {
		System.out.println(e.getID());
	}
	ParkingSpot s = tableView.getSelectionModel().getSelectedItem();
	officer.RemoveSpot(s);
	System.out.println("AFTER");
	t = officer.getSpots();
	for(ParkingSpot e: t) {
		System.out.println(e.getID());
	}
	try {
		fill();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}

@FXML
public void GRANT(ActionEvent event) {
	//change 
	try {
		// input the (modified) file content to the StringBuffer "input"
		BufferedReader file = new BufferedReader(new FileReader("notes.txt"));
		StringBuffer inputBuffer = new StringBuffer();
		String line;

		while ((line = file.readLine()) != null) {
			//line = ... // replace the line here
			inputBuffer.append(line);
			inputBuffer.append('\n');
		}
		file.close();

		// write the new string with the replaced line OVER the same file
		FileOutputStream fileOut = new FileOutputStream("notes.txt");
		fileOut.write(inputBuffer.toString().getBytes());
		fileOut.close();

	} catch (Exception e) {
		System.out.println("Problem reading file.");
	}
}
@FXML
public void CANCEL(ActionEvent event) {
	//remove booking from customer's list

}

}
