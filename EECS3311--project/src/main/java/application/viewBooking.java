package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import EECS3311.project.ParkingSpot;
import EECS3311.project.customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class viewBooking implements Initializable{
	customer CUSTOMER = new customer();
	@FXML
	private TableView<ParkingSpot> bookingView;

	@FXML
	private TableColumn<ParkingSpot, String> bookingCol, paymentCol, expCol;

	@FXML
	private ComboBox<String> from, until;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bookingCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
		paymentCol.setCellValueFactory(new PropertyValueFactory<>("stat"));
		expCol.setCellValueFactory(new PropertyValueFactory<>("Exp"));
		try {
			fill();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void fill() throws IOException {

		bookingView.getItems().clear();
		List<ParkingSpot> cs = CUSTOMER.ViewBookings();
		for(ParkingSpot ps: cs) {
			if(!bookingView.getItems().contains(ps))
				bookingView.getItems().add(ps);
		}

	}
	@FXML
	public void cancelBooking(ActionEvent event) throws Exception {
		if(CUSTOMER.CancelBookings(bookingView.getSelectionModel().getSelectedItem().getID())) {
			fill();
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
}
