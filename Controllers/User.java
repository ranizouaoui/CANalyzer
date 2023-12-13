package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import helpers.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class User implements Initializable {
	@FXML
	private Button exitbutton;
	@FXML
	private BorderPane borderPane;



	public void cancelButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) exitbutton.getScene().getWindow();
		stage.close();

	}


	@FXML
	private void loadFXML(String fileName) {
		Parent parent;
		try {
			parent = FXMLLoader.load(getClass().getResource("/views/" + fileName + ".fxml"));
			borderPane.setCenter(parent);

		} catch (IOException ex) {
			Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@FXML
	private void clear() {
		borderPane.setCenter(null);
	}

	@FXML
	private void loadPage01View(ActionEvent e) {
		loadFXML("useroption1");
	}

	@FXML
	private void loadPage02View(ActionEvent e) {
		loadFXML("useroption2");
	}

	@FXML
	private void loadPage03View(ActionEvent e) {
		loadFXML("PageTraining");
	}

	@FXML
	private void loadPage05View(ActionEvent e) {
		loadFXML("PageInscription");
	}

	@FXML
	private void loadPage04View(ActionEvent e) {
		loadFXML("Charts");
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadFXML("useroption1");
		
	}

}