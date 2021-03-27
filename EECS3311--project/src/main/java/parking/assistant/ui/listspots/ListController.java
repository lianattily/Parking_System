package parking.assistant.ui.listspots;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import EECS3311.project.ParkingSpot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import parking.assistant.database.DatabaseHandler;

public class ListController implements Initializable {

	ObservableList<ParkingSpot> list = FXCollections.observableArrayList(); 
	
	@FXML
	private AnchorPane rootPane;
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

		initCol();
		
		loadData();
		
	}

	private void loadData() {
		DatabaseHandler handler = new DatabaseHandler();
		String qu = "SELECT * FROM PARKING"; 
		ResultSet rs = handler.execQuery(qu); 
		try{ 
			while(rs.next()){ 
				String spacenum = rs.getString("spacenum"); 
				String license = rs.getString("platenum"); 
				String numb = rs.getString("spacenum"); 
				int start = rs.getInt("start"); 
				int end = rs.getInt("end"); 
				Boolean avail = rs.getBoolean("isAvail");
				//list.add(new ParkingSpot(spacenum, license, start, end, avail));
				} 
		}catch(Exception e){
					
				}
		tableView.getItems().setAll(list);
		}
		
	

	private void initCol() {
		requestcol.setCellValueFactory(new PropertyValueFactory<>("spacenum"));
		paymentCol.setCellValueFactory(new PropertyValueFactory<>("spacenum"));
		availCol.setCellValueFactory(new PropertyValueFactory<>("isAvail"));
	}
	

}
