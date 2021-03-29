package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
		BufferedReader reader  = new BufferedReader(new FileReader("Parkingdatabase.csv"));
		CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(0).build();
		List<String[]> line;
		line = csvReader.readAll();
			System.out.println(line);
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
	@FXML
	private TextField address;
	@FXML
	private AnchorPane rootPane;
	
	
	private Boolean availability;
	
	private int requests;
	
	@FXML
	public void AddSpot(ActionEvent event) throws IOException {
		//ADD NEW SPOT
		Boolean found = false;
		
		String path = "ParkingDatabase.txt"; 
		String line = ""; 
		if(address.getText().isBlank() || address.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null); 
			alert.setContentText("Please enter a real postal code"); 
			alert.showAndWait();
		
		try {
			BufferedReader br  = new BufferedReader(new FileReader(path));
			while((line = br.readLine())!=null) {
				String [] values = line.split(",");
				System.out.println(values[0]);
				if(values[0].equals(address.getText())) {
					found = true;
					Alert alerrt = new Alert(Alert.AlertType.ERROR);
					alerrt.setHeaderText(null); 
					alerrt.setContentText("Could not add Parking spot because it already exists"); 
					alerrt.showAndWait();
				}
			}
		}
		catch (Exception e) {
			System.out.println("Parking spot adding Checking exception");
		}
		if(!found) {
			ParkingSpot spot = officer.AddSpot(address.getText());
			Alert alertt = new Alert(Alert.AlertType.INFORMATION);
			fill();
			alertt.setHeaderText(null); 
			alertt.setContentText("Parking spot added successfully"); 
			alertt.showAndWait();
			Parent Scene2root = FXMLLoader.load(getClass().getResource("/officer.fxml"));
			Scene AddInfoScene = new Scene(Scene2root);

			//this gets scene information
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setScene(AddInfoScene);
			window.show();
			
		}
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
	public void RETURN(ActionEvent event) throws IOException {
		Parent Scene2root = FXMLLoader.load(getClass().getResource("/EnterAs.fxml"));
		Scene AddInfoScene = new Scene(Scene2root);

		//this gets scene information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
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
	public void RemoveSpot(ActionEvent event) {
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
}
